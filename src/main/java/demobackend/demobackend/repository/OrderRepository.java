package demobackend.demobackend.repository;

import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.WebOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<WebOrder,Integer> {
    public List<WebOrder> findByUser(LocalUser user);
}
