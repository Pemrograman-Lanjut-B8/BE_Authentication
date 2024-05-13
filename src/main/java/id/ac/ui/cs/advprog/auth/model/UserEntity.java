package id.ac.ui.cs.advprog.auth.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 200)
    private String fullName;

    @Size(max = 15)
    private String phoneNumber;

    private String profilePicture;

    private String bio;

    private String gender;

    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public UserEntity() {

    }

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}