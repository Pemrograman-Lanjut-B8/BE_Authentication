package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.builder.UserBuilder;
import id.ac.ui.cs.advprog.auth.model.User;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Repository
public class UserRepository {
    private final List<User> userData = new ArrayList<>();

    @Setter
    @Autowired
    private UserBuilder userBuilder;

    public User createUser(User user){
        return null;
    }
    public Iterator<User> findAll(){
        return null;
    }
    public User findById(String id){
        return null;
    }

    public User update(String id, User updatedUser){
        return null;
    }

    public void deleteUserById(String id){

    }

}