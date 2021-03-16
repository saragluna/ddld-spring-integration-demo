package com.azure.spring.integration.todolist.controller;

import com.azure.spring.integration.todolist.dao.TodoItemRepository;
import com.azure.spring.integration.todolist.entity.Event;
import com.azure.spring.integration.todolist.entity.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Xiaolu Dai, 2021/3/16.
 */
@RestController
public class TodoListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoListController.class);

    public static final String QUEUE = "events";

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * HTTP GET ALL
     */
    @GetMapping(value = "/api/todolist", produces = { APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getAllTodoItems() {
        System.out.println(" ======= /api/todolist =======");
        try {
            List<TodoItem> result = new ArrayList<>();
            todoItemRepository.findAllByOwner(getUser()).iterator().forEachRemaining(result::add);
            return new ResponseEntity<>(result, OK);
        } catch (Exception e) {
            LOGGER.error("Error happened", e);
            return new ResponseEntity<>("Nothing found", NOT_FOUND);
        }
    }

    /**
     * HTTP POST NEW ONE
     */
    @PostMapping(value = "/api/todolist", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewTodoItem(@RequestBody TodoItem item) {
        try {
            item.setOwner(getUser());
            item.setId(UUID.randomUUID().toString());
            todoItemRepository.save(item);

            jmsTemplate.convertAndSend(QUEUE, new Event(item, "Create"));

            return new ResponseEntity<>("Entity created", CREATED);
        } catch (Exception e) {
            LOGGER.error("Error happened", e);
            return new ResponseEntity<>("Entity creation failed", CONFLICT);
        }
    }

    /**
     * HTTP DELETE
     */
    @DeleteMapping(value = "/api/todolist/{id}")
    public ResponseEntity<String> deleteTodoItem(@PathVariable("id") String id) {
        try {
            todoItemRepository.deleteByIdAndOwner(id, getUser());

            jmsTemplate.convertAndSend(QUEUE, new Event(getUser(), id, "Delete"));

            return new ResponseEntity<>("Entity deleted", OK);
        } catch (Exception e) {
            LOGGER.error("Error happened", e);
            return new ResponseEntity<>("Entity deletion failed", NOT_FOUND);
        }

    }

    /**
     * HTTP GET
     */
    @GetMapping(value = "/api/todolist/{id}", produces = { APPLICATION_JSON_VALUE })
    public ResponseEntity<?> getTodoItem(@PathVariable("id") String id) {
        try {
            jmsTemplate.convertAndSend(new Event(getUser(), id, "Get"));

            return todoItemRepository.findByIdAndOwner(id, getUser())
                                     .map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            LOGGER.error("Error happened", e);
            return new ResponseEntity<>(id + " not found", NOT_FOUND);
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @PutMapping(value = "/api/todolist", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTodoItem(@RequestBody TodoItem item) {
        try {
            item.setOwner(getUser());
            todoItemRepository.deleteByIdAndOwner(item.getId(), getUser());
            todoItemRepository.save(item);

            jmsTemplate.convertAndSend(new Event(getUser(), item.getId(), "Update"));

            return new ResponseEntity<>("Entity updated", OK);
        } catch (Exception e) {
            LOGGER.error("Error happened", e);
            return new ResponseEntity<>("Entity updating failed", NOT_FOUND);
        }
    }

    private String getUser() {
        return ((DefaultOidcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
    }
}
