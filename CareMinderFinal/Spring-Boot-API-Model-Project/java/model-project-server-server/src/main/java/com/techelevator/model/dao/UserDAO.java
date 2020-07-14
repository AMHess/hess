package com.techelevator.model.dao;

import java.util.List;

import com.techelevator.model.model.User;

public interface UserDAO {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
}
