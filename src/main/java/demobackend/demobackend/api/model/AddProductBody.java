package demobackend.demobackend.api.model;

import demobackend.demobackend.model.Inventory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductBody {
    private String name;
    private String shortDescription;
    private String longDescription;
    private Double price;
    private Integer quantity;
}
