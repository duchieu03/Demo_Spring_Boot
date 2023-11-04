package demobackend.demobackend.repository;

import demobackend.demobackend.model.Address;
import demobackend.demobackend.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    List<Address> findByUser(LocalUser user);
}
