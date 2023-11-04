package demobackend.demobackend.service;

import demobackend.demobackend.api.model.LoginBody;
import demobackend.demobackend.api.model.LoginResponse;
import demobackend.demobackend.api.model.RegistrationBody;
import demobackend.demobackend.api.model.ResetPasswordBody;
import demobackend.demobackend.exception.EmailDoesNotExistException;
import demobackend.demobackend.exception.EmailFailException;
import demobackend.demobackend.exception.UserAlreadyExistException;
import demobackend.demobackend.exception.UserNotVerifiedException;
import demobackend.demobackend.model.LocalUser;

import java.util.Optional;

public interface UserService {
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailException;
    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailException;

    boolean verifyUser(String token);

    void resetPassword(String email) throws EmailFailException, EmailDoesNotExistException;
    LocalUser changeNewPassword(ResetPasswordBody resetPasswordBody);
    public Optional<LocalUser> getUserById(Integer id);
}
