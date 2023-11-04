package demobackend.demobackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    /** The token that was sent to the user. */
    @Lob
    @Column(name = "token", nullable = false, unique = true)
    private String token;
    /** The timestamp of when the token was created. */
    @Column(name = "created_timestamp", nullable = false)
    private Timestamp createdTimestamp;
    /** The user this verification token is for. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private LocalUser user;

}
