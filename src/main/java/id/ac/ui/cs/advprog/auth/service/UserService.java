package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.User;

import java.util.List;

public interface UserService{
    public User create(User user);
    public List<User> findAll();
    User findById(String id);
    public void update(String userId, User user);
    public void deleteUserById(String userId);
}