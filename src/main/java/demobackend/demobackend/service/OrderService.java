package demobackend.demobackend.service;

import demobackend.demobackend.api.model.ProductInCart;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.WebOrder;

import java.util.List;

public interface OrderService {
    public List<WebOrder> findByUser(LocalUser user);
    public void addOrder(List<ProductInCart> products, LocalUser user);
}
