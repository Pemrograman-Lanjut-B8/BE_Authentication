package id.ac.ui.cs.advprog.auth.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class User {
    String id;
    String fullName;
    String email;
    String phoneNumber;
    String password;
    String bio;
    String jenisKelamin;
    String TanggalLahir;
    String type;
}
