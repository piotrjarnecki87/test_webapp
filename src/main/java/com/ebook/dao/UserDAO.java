package com.ebook.dao;

import com.ebook.model.User;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public interface UserDAO  extends GenericDao{
    void addUser(User user) throws SQLException;
    void updateUser(User user);
    void deleteUser(String username);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    boolean isValidLogin(String username, String password);

}

