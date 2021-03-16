package com.azure.spring.integration.todolist.dao;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.integration.todolist.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author Xiaolu Dai, 2021/3/14.
 */
@Repository
public interface UserRepository extends CosmosRepository<User, String> {

    User findByEmail(String email);

}
