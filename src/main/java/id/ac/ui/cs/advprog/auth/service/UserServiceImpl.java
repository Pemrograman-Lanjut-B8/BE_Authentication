package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.UserEntity;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserEntity create(UserEntity user) {
        userRepository.createUser(user);
        return user;
    }

    @Override
    public List<UserEntity> findAll() {
        Iterator<UserEntity> userIterator = userRepository.findAll();
        List<UserEntity> allUser = new ArrayList<>();
        userIterator.forEachRemaining(allUser::add);
        return allUser;
    }

    @Override
    public UserEntity findByUsername(String id) {
        UserEntity user = userRepository.findByUsername(id);
        return user;
    }

    @Override
    public void update(String userId, UserEntity user) {
        userRepository.update(userId, user);
    }

//    @Override
//    public void deleteUserBy(String userId) {
//        userRepository.deleteUserById(userId);
//    }
}