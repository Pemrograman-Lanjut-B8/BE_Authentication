package id.ac.ui.cs.advprog.auth.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum UserType {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");
    private final String value;

    private UserType(String value){
        this.value = value;
    }

    public static  boolean contains(String param){
        for (UserType userType : UserType.values()){
            if (userType.name().equals(param)){
                return true;
            }
        }
        return false;
    }

    public static List<String> getAll(){
        List<String> types = new ArrayList<>();
        for (UserType userType : UserType.values()){
            types.add(userType.name());
        }
        return types;
    }

}