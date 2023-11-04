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
import demobackend.demobackend.model.VerificationToken;
import demobackend.demobackend.repository.LocalUserRepository;
import demobackend.demobackend.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private LocalUserRepository repository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private JWTService JWTservice;
    @Autowired
    private EmailService emailService;
    @Override
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFailException {
        if(repository.findByEmail(registrationBody.getEmail()).isPresent()) throw new UserAlreadyExistException("Email is already in used");
                if( repository.findByUsername(registrationBody.getUsername()).isPresent()) throw new UserAlreadyExistException("Username is already in ussed");

        LocalUser user= new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()) );
        user.setFirstName(registrationBody.getFirstname());
        user.setLastName(registrationBody.getLastname());
        VerificationToken verificationToken = createVerifiedToken(user);
        user = repository.save(user);
        emailService.sendVerifyEmail(verificationToken);
        return user;
    }

    @Override
    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailException {
        Optional<LocalUser> user= repository.findByUsername(loginBody.getUsername());
        if(user.isPresent()){
            LocalUser localUser = user.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(),localUser.getPassword())){
                if(localUser.getIsEmailVerified()){
                    return JWTservice.generateToken(localUser);
                }
                else{
                    List<VerificationToken> verificationTokenList = user.get().getVerificationTokens();
                    Timestamp endToken = verificationTokenList.get(0).getCreatedTimestamp();
                    endToken.setTime(endToken.getTime()+(1000*60*30));
                    boolean resend = endToken.after(new Timestamp(System.currentTimeMillis()));
                    if(resend){
                        VerificationToken verificationToken = createVerifiedToken(localUser);
                        emailService.sendVerifyEmail(verificationToken);
                        verificationTokenRepository.save(verificationToken);
                        throw new UserNotVerifiedException("New token is send");
                    }
                    else{
                        throw new UserNotVerifiedException("Not Credential User");
                    }

                }

            }
        }
        return null;
    }


    private VerificationToken createVerifiedToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(JWTservice.generateVerifyToken(user));
        verificationToken.setUser(user);
        verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }

    @Transactional
    public boolean verifyUser(String token) {
        Optional<VerificationToken> opToken = verificationTokenRepository.findByToken(token);
        if (opToken.isPresent()) {
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if (!user.getIsEmailVerified()) {
                user.setIsEmailVerified(true);
                repository.save(user);
                verificationTokenRepository.deleteByUser(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetPassword(String email) throws EmailFailException, EmailDoesNotExistException {
        Optional<LocalUser> opUser = repository.findByEmail(email);
        if(opUser.isPresent()){
            LocalUser localUser = opUser.get();
            String token = JWTservice.generateResetPasswordToken(localUser);
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setUser(localUser);
            verificationToken.setToken(token);
            verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
            verificationTokenRepository.save(verificationToken);
            emailService.sendResetPasswordEmail(verificationToken);
        }else {
            throw new EmailDoesNotExistException("Email does not exist in database");
        }
    }

    @Transactional
    @Override
    public LocalUser changeNewPassword(ResetPasswordBody resetPasswordBody) {
        String token = resetPasswordBody.getToken();
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if(verificationToken.isPresent()){
            VerificationToken token1 = verificationToken.get();
            LocalUser user =token1.getUser();
            user.setPassword(encryptionService.encryptPassword(resetPasswordBody.getPassword()));
            repository.save(user);
            return user;
        }
        return null;
    }

    @Override
    public Optional<LocalUser> getUserById(Integer id) {
        return repository.findById(id);
    }
}
