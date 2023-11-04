package demobackend.demobackend.service;

import demobackend.demobackend.exception.EmailFailException;
import demobackend.demobackend.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${email.from}")
    private String from;
    @Value("${app.frontend.url}")
    private String url;
    private JavaMailSender javaMailSender;
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    private SimpleMailMessage generateMessage(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        return mailMessage;
    }

    public void sendVerifyEmail(VerificationToken verificationToken) throws EmailFailException {
    SimpleMailMessage mailMessage = generateMessage();
    mailMessage.setTo(verificationToken.getUser().getEmail());
    mailMessage.setSubject("Verify your email to active your account");
    mailMessage.setText("Please follow the link below to verify your email to active your account.\n" +
            url + "/auth/verify?token=" + verificationToken.getToken());
           try{
               javaMailSender.send(mailMessage);
           }catch (MailException ex){
               throw new EmailFailException("Can not send mail");
           }


    }

    public void sendResetPasswordEmail(VerificationToken verificationToken) throws EmailFailException {
        SimpleMailMessage mailMessage = generateMessage();
        mailMessage.setTo(verificationToken.getUser().getEmail());
        mailMessage.setSubject("Verify your email to reset password of your account");
        mailMessage.setText("Please follow the link below to verify your email to reset password of your account.\n" +
                url + "/auth/verify?token=" + verificationToken.getToken());
        try{
            javaMailSender.send(mailMessage);
        }
        catch(MailException e){
            throw new EmailFailException("Can not send email");
        }
    }
}
