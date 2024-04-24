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
        if (user.getId() == null) {
            UUID uuid = UUID.randomUUID();
            user.setId(uuid.toString());
        }
        userData.add(user);
        return user;
    }
    public Iterator<User> findAll(){
        return userData.iterator();
    }
    public User findById(String id){
        for (User User: userData){
            if (User.getId().equals(id)){
                return User;
            }
        }
        return null;
    }

    public User update(String id, User updatedUser){
        for (int i=0; i<userData.size(); i++){
            User user = userData.get(i);
            if(user.getId().equals(id)){
                User newUser = userBuilder.reset()
                        .setCurrent(updatedUser)
                        .addId(id)
                        .addPassword(updatedUser.getPassword())
                        .build();
                userData.remove(i);
                userData.add(i,newUser);
                return newUser;
            }
        }
        return null;
    }

    public void deleteUserById(String id){
        userData.removeIf(User -> User.getId().equals(id));
    }

}