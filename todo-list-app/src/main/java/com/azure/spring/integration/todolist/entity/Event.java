package com.azure.spring.integration.todolist.entity;

import java.io.Serializable;

/**
 * @author Xiaolu Dai, 2021/3/16.
 */
public class Event implements Serializable {

    private String type;
    private String owner;
    private String description;
    private String id;

    public Event() {

    }

    public Event(TodoItem item, String type) {
        this.type = type;
        this.owner = item.getOwner();
        this.description = item.getDescription();
        this.id = item.getId();
    }

    public Event(String owner, String id, String type) {
        this.owner = owner;
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" + "type='" + type + '\'' + ", owner='" + owner + '\'' + ", description='" + description + '\'' + ", id='" + id + '\'' + '}';
    }
}
