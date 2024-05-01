package id.ac.ui.cs.advprog.auth.model.builder;

import id.ac.ui.cs.advprog.auth.model.enums.UserType;
import id.ac.ui.cs.advprog.auth.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {
    private UserEntity currentUser;
    public UserBuilder(){
        this.reset();
    }

    public UserBuilder reset(){
        currentUser = new UserEntity();
        return this;
    }

    public UserBuilder addUsername(String username) {
        currentUser.setUsername(username);
        return this;
    }

    public UserBuilder addPassword(String password){
        currentUser.setPassword(password);
        return this;
    }

    public UserBuilder setCurrent(UserEntity user){
        currentUser = user;
        return this;
    }

    public UserEntity build(){
        return currentUser;
    }
}