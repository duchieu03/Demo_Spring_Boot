package demobackend.demobackend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import demobackend.demobackend.model.LocalUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expired}")
    private int expired;

    public String generateToken(LocalUser user){
        return JWT.create()
                .withClaim("USER_NAME",user.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+expired))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(algorithmKey));
    }

    public String generateVerifyToken(LocalUser user){
        return JWT.create()
                .withClaim("EMAIL_KEY",user.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+expired))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(algorithmKey));
    }

    public String generateResetPasswordToken(LocalUser user){
        return JWT.create()
                .withClaim("REST_EMAIL_KEY",user.getEmail())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*30)))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(algorithmKey));
    }

    public String getUsername(String token)
    {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(algorithmKey)).withIssuer(issuer).build().verify(token);
        return JWT.decode(token).getClaim("USER_NAME").asString();
    }
}
