package com.azure.spring.integration.todolist.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;

/**
 * @author Xiaolu Dai, 2021/3/16.
 */
@Container(containerName = "TodoItems")
public class TodoItem {

    private String id;
    private String description;

    @PartitionKey
    private String owner;
    private boolean finished;

    public TodoItem(String id, String description, String owner) {
        this.description = description;
        this.id = id;
        this.owner = owner;
        this.finished = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "TodoItem{" + "description='" + description + '\'' + '}';
    }
}
