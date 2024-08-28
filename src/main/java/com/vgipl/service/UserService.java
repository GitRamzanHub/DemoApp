package com.vgipl.service;

import com.vgipl.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User addUser(User user);

    User findUserById(int id);

    List<User> getUserList();

    void deleteUserById(int id);

    User updateUser(User user);
}
