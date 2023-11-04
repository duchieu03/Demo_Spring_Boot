package demobackend.demobackend.repository;

import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Integer> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUser(LocalUser user);
}
