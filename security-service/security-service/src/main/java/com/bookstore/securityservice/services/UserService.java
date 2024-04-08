package com.bookstore.securityservice.services;


import com.bookstore.securityservice.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User createUser(User user);

    User getUserById(long id);

    void delete(long id);

    User updateUser(User user, long id);

    User getOneUserByUserName(String userName);
}
