package demobackend.demobackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "authorize")
    @Enumerated(EnumType.STRING)
    private Authorize authorize;

    @Override
    public String getAuthority() {
        return authorize.name();
    }
}
