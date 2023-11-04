package demobackend.demobackend.api.controller.order;

import demobackend.demobackend.api.model.ProductInCart;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.Product;
import demobackend.demobackend.model.WebOrder;
import demobackend.demobackend.model.WebOrderQuantity;
import demobackend.demobackend.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public ResponseEntity<?> findByUser(@AuthenticationPrincipal LocalUser user){
        System.out.println(user);
        return ResponseEntity.ok().body(orderService.findByUser(user));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addOrder(@AuthenticationPrincipal LocalUser localUser, @RequestBody List<ProductInCart> products){
        if(checkAuthorize(localUser)){
            orderService.addOrder(products,localUser);
            return ResponseEntity.ok().body("Add successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean checkAuthorize(LocalUser localUser){
        return localUser!=null;
    }
}

