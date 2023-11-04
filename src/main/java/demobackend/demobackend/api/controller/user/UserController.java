package demobackend.demobackend.api.controller.user;

import demobackend.demobackend.model.Address;
import demobackend.demobackend.model.Authorize;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.model.Role;
import demobackend.demobackend.repository.AddressRepository;
import demobackend.demobackend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping("/{id}/address")
    public ResponseEntity<?> getAddress(@AuthenticationPrincipal LocalUser user, @PathVariable Integer id){
        if(checkAuthority(user,id)){
            Optional<LocalUser> localUser = userService.getUserById(id);
            if(localUser.isPresent()){
                List<Address> addresses = addressRepository.findByUser(localUser.get());
                return ResponseEntity.ok().body(addresses);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @Transactional
    @PostMapping("/{userId}/address")
    public ResponseEntity<Address> putAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Integer userId,
            @RequestBody Address address){
        if(!checkAuthority(user,userId)){
            return ResponseEntity.badRequest().build();
        }
        address.setId(null);
        LocalUser refUser = new LocalUser();
        refUser.setId(userId);
        address.setUser(refUser);
        addressRepository.save(address);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/address/{addressId}")
    public ResponseEntity<Address> patchAddress(
            @AuthenticationPrincipal LocalUser user, @PathVariable Integer userId,
            @PathVariable Integer addressId, @RequestBody Address address){
        if(!checkAuthority(user,userId)){
            return ResponseEntity.badRequest().build();
        }
        if (address.getId().equals(addressId)) {
            Optional<Address> opOriginalAddress = addressRepository.findById(addressId);
            if (opOriginalAddress.isPresent()) {
                LocalUser originalUser = opOriginalAddress.get().getUser();
                if (originalUser.getId().equals(userId)) {
                    address.setUser(originalUser);
                    return ResponseEntity.ok(addressRepository.save(address));
                }
            }
        }
        return ResponseEntity.badRequest().build();

    }
    private boolean checkAuthority(LocalUser user, Integer id){
        List<Role> authorizes = user.getAuthorization();
        for(Role role: authorizes){
            if(role.getAuthorize().equals(Authorize.ADMIN)){
                return true;
            }
        }

        if(user.getId().equals(id)){
            return true;
        }
        return false;
    }
}
