package demobackend.demobackend.service;

import demobackend.demobackend.api.model.AddProductBody;
import demobackend.demobackend.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProduct();
    public List<Product> paginationProduct(Integer page, Integer size,String name,Integer startPrice, Integer endPrice);

    Product addProduct(AddProductBody product);
}
