package demobackend.demobackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    /** The name of the product. */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    /** The short description of the product. */
    @Column(name = "short_description", nullable = false)
    private String shortDescription;
    /** The long description of the product. */
    @Column(name = "long_description")
    private String longDescription;
    /** The price of the product. */
    @Column(name = "price", nullable = false)
    private Double price;
    /** The inventory of the product. */
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    private Inventory inventory;
}
