package demobackend.demobackend.repository;

import demobackend.demobackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface LocalUserRepository extends JpaRepository<LocalUser,Integer> {
    public Optional<LocalUser> findByUsername(String username);
    public Optional<LocalUser> findByEmail(String email);

}
