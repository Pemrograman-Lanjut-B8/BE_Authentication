package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User create(User user) {
        userRepository.createUser(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        Iterator<User> userIterator = userRepository.findAll();
        List<User> allUser = new ArrayList<>();
        userIterator.forEachRemaining(allUser::add);
        return allUser;
    }

    @Override
    public User findById(String id) {
        User user = userRepository.findById(id);
        return user;
    }

    @Override
    public void update(String userId, User user) {
        userRepository.update(userId, user);
    }

    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteUserById(userId);
    }
}