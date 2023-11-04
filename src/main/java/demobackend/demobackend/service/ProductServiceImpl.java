package demobackend.demobackend.service;

import demobackend.demobackend.api.model.AddProductBody;
import demobackend.demobackend.model.Inventory;
import demobackend.demobackend.model.Product;
import demobackend.demobackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAllProduct() {
        return repository.findAll();
    }

    @Override
    public List<Product> paginationProduct(Integer page, Integer size,String name, Integer startPrice, Integer endPrice) {
        Pageable pageable = PageRequest.of(page,size);
        List<Product> allProducts = repository.filterProduct(name,startPrice,endPrice,pageable);
        return allProducts;
    }

    @Override
    public Product addProduct(AddProductBody addProductBody) {
        Product product = new Product();
        product.setName(addProductBody.getName());
        product.setPrice(addProductBody.getPrice());
        product.setShortDescription(addProductBody.getShortDescription());
        product.setLongDescription(addProductBody.getLongDescription());
        Inventory inventory= new Inventory();
        inventory.setQuantity(addProductBody.getQuantity());
        inventory.setProduct(product);
        product.setInventory(inventory);
        return repository.save(product);
    }
}
