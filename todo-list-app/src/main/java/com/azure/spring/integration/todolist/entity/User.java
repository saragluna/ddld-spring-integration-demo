package com.azure.spring.integration.todolist.entity;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

/**
 * @author Xiaolu Dai, 2021/3/14.
 */
@Container(containerName = "Users")
public class User {

    @Id
    private String id;

    private String firstName;

    @PartitionKey
    private String lastName;

    private String email;

    public User() {

    }

    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + '}';
    }
}