package demobackend.demobackend.api.controller.product;

import demobackend.demobackend.api.model.AddProductBody;
import demobackend.demobackend.model.Authorize;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.Product;
import demobackend.demobackend.model.Role;
import demobackend.demobackend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public ResponseEntity<?> getProduct(){
        return ResponseEntity.ok().body(productService.getAllProduct());
    }

    @GetMapping("/list")
    public ResponseEntity<?> viewProduct
            (@RequestParam(defaultValue = "0",name="page") Integer page,
             @RequestParam(defaultValue = "3",name="size") Integer size,
             @RequestParam(required = false,name="name") String name,
             @RequestParam(required = false,name="start") Integer startPrice,
             @RequestParam(required = false,name="end") Integer endPrice
            ){
        List<Product> list = productService.paginationProduct(page,size,name,startPrice,endPrice);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@AuthenticationPrincipal LocalUser localUser
            , @Valid @RequestBody AddProductBody product ){
        if(!checkAuthorize(localUser)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not allowed");
        Product product1 = productService.addProduct(product);
        return ResponseEntity.ok().body(product1);
    }

    private boolean checkAuthorize(LocalUser user){
        if(user == null) return  false;
        List<Role> authorizes = user.getAuthorization();
        for(Role role: authorizes){
            if(role.getAuthorize().equals(Authorize.ADMIN)){
                return true;
            }
        }
        return false;
    }
}
