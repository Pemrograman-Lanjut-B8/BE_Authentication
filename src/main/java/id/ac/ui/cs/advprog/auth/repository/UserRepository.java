package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.builder.UserBuilder;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Repository
public class UserRepository {
    private final List<UserEntity> userData = new ArrayList<>();

    @Setter
    @Autowired
    private UserBuilder userBuilder;

    public UserEntity createUser(UserEntity user){
        userData.add(user);
        return user;
    }
    public Iterator<UserEntity> findAll(){
        return userData.iterator();
    }
    public UserEntity findByUsername(String username){
        for (UserEntity User: userData){
            if (User.getUsername().equals(username)){
                return User;
            }
        }
        return null;
    }

    public UserEntity update(String username, UserEntity updatedUser){
        for (int i=0; i<userData.size(); i++){
            UserEntity user = userData.get(i);
            if(user.getUsername().equals(username)){
                UserEntity newUser = userBuilder.reset()
                        .setCurrent(updatedUser)
                        .addUsername(updatedUser.getUsername())
                        .addPassword(updatedUser.getPassword())
                        .build();
                userData.remove(i);
                userData.add(i,newUser);
                return newUser;
            }
        }
        return null;
    }

}