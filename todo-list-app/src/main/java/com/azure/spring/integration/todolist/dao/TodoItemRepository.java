package com.azure.spring.integration.todolist.dao;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.integration.todolist.entity.TodoItem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Xiaolu Dai, 2021/3/16.
 */
@Repository
public interface TodoItemRepository extends CosmosRepository<TodoItem, String> {

    Iterable<TodoItem> findAllByOwner(String owner);

    void deleteByIdAndOwner(String id, String owner);

    Optional<TodoItem> findByIdAndOwner(String id, String owner);

}
