package id.ac.ui.cs.advprog.auth.model.builder;

import id.ac.ui.cs.advprog.auth.model.enums.UserType;
import id.ac.ui.cs.advprog.auth.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {
    private User currentUser;
    public UserBuilder(){
        this.reset();
    }

    public UserBuilder reset(){
        currentUser = new User();
        return this;
    }

    public UserBuilder addId(String id) {
        currentUser.setId(id);
        return this;
    }

    public UserBuilder addFullName(String name){
        currentUser.setFullName(name);
        return this;
    }

    public UserBuilder addEmail(String email){
        currentUser.setEmail(email);
        return this;
    }

    public UserBuilder addPhoneNumber(String phoneNumber){
        currentUser.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserBuilder addPassword(String password){
        currentUser.setPassword(password);
        return this;
    }

    public UserBuilder addType(String type){
        if (UserType.contains(type)){
            currentUser.setType(type);
            return this;
        }else{
            throw new IllegalArgumentException();
        }
    }

    public UserBuilder setCurrent(User user){
        currentUser = user;
        return this;
    }

    public User build(){
        return currentUser;
    }
}