package demobackend.demobackend.repository;

import demobackend.demobackend.api.model.FilterBody;
import demobackend.demobackend.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Transactional
    @Query(
            value="Select * from Product where (name like %:name% or :name is null) " +
                    "and (price >= :startPrice or :startPrice is null )"+
                    "and (price<= :endPrice or :endPrice is null)",
            nativeQuery = true
    )
    public List<Product> filterProduct(@Param("name") String name , @Param("startPrice") Integer startPrice, @Param("endPrice") Integer endPrice, Pageable pageable);
}

