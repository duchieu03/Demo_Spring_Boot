package demobackend.demobackend.api.controller.auth;

import demobackend.demobackend.api.model.LoginBody;
import demobackend.demobackend.api.model.LoginResponse;
import demobackend.demobackend.api.model.RegistrationBody;
import demobackend.demobackend.api.model.ResetPasswordBody;
import demobackend.demobackend.exception.EmailDoesNotExistException;
import demobackend.demobackend.exception.EmailFailException;
import demobackend.demobackend.exception.UserAlreadyExistException;
import demobackend.demobackend.exception.UserNotVerifiedException;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailException {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().body("Register successfully");
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = null;
        try{
            jwt = userService.loginUser(loginBody);
        }catch (UserNotVerifiedException e){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setStatus("User is not verified");
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
        }catch (Exception exception){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setStatus("Fail to send email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(loginResponse);
        }

         if (jwt == null){
             LoginResponse loginResponse = new LoginResponse();
             loginResponse.setStatus("Wrong username or password");
             return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
         }
         LoginResponse loginResponse = new LoginResponse();
         loginResponse.setJwt(jwt);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam String token) {
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) {
        return user;
    }

    @GetMapping("/admin")
    public String testAuthorize(){
        return "hello admin";
    }

    @GetMapping("/user")
    public String testAuthorizeUser(){
        return "hello user";
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String email) throws EmailDoesNotExistException, EmailFailException {
           userService.resetPassword(email);
           return ResponseEntity.ok().body("Has sent token");
    }

    @PostMapping("/change")
    public ResponseEntity<?> changePassword(@RequestBody ResetPasswordBody resetPasswordBody){

        return ResponseEntity.ok().body(userService.changeNewPassword(resetPasswordBody));
    }
}
