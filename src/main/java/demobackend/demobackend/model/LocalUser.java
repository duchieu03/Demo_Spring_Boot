package demobackend.demobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name="local_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class LocalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    /** The username of the user. */
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    /** The encrypted password of the user. */
    @JsonIgnore
    @Column(name = "password", nullable = false, length = 1000)
    private String password;
    /** The email of the user. */
    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;
    /** The first name of the user. */
    @Column(name = "first_name", nullable = false)
    private String firstName;
    /** The last name of the user. */
    @Column(name = "last_name", nullable = false)
    private String lastName;
    /** The addresses associated with the user. */
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER ,mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private List<VerificationToken> verificationTokens = new ArrayList<>();
    /** Has the users email been verified? */
    @Column(name = "email_verified", nullable = false)
    private Boolean isEmailVerified = false;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name= "user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Role> authorization = new ArrayList<>();
}
