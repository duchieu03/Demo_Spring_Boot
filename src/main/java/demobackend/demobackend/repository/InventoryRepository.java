package demobackend.demobackend.repository;

import demobackend.demobackend.model.Inventory;
import demobackend.demobackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    public Inventory findByProduct(Product product);
}
