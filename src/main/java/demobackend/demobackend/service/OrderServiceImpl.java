package demobackend.demobackend.service;

import demobackend.demobackend.api.model.ProductInCart;
import demobackend.demobackend.exception.OutOfStock;
import demobackend.demobackend.exception.ProductDoesNotExist;
import demobackend.demobackend.model.*;
import demobackend.demobackend.repository.InventoryRepository;
import demobackend.demobackend.repository.OrderRepository;
import demobackend.demobackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Override
    public List<WebOrder> findByUser(LocalUser user) {
        return repository.findByUser(user);
    }

    @Override
    public void addOrder(List<ProductInCart> products, LocalUser user) {
        List<WebOrderQuantity> list = new ArrayList<>();
        WebOrder webOrder = new WebOrder();
        webOrder.setUser(user);
        webOrder.setAddress(user.getAddresses().get(0));
        webOrder.setQuantities(list);
        if(checkQuantity(products)){
        for (ProductInCart productInCart: products){
            WebOrderQuantity webOrderQuantity = new WebOrderQuantity();
            Product product = new Product();
            product.setId(productInCart.getId());
            // change inventory of product
            Inventory inventory = inventoryRepository.findByProduct(product);
            inventory.setQuantity(inventory.getQuantity()-productInCart.getQuantity());
            inventoryRepository.save(inventory);


            webOrderQuantity.setProduct(product);
            webOrderQuantity.setQuantity(productInCart.getQuantity());
            webOrderQuantity.setOrder(webOrder);
            list.add(webOrderQuantity);
        }
        repository.save(webOrder);
        }

    }

    private boolean checkQuantity(List<ProductInCart> productInCarts) {
        for (ProductInCart p : productInCarts) {
            Optional<Product> product = productRepository.findById(p.getId());
            if (product.isPresent()) {
                Product product1 = product.get();
                Integer stock = product1.getInventory().getQuantity();
                if (stock < p.getQuantity()) {
                    throw new OutOfStock(p.getId());
                }
            }else throw new ProductDoesNotExist(p.getId());

        }
        return true;
    }
}
