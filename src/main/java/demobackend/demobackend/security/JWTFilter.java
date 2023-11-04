package demobackend.demobackend.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import demobackend.demobackend.model.LocalUser;
import demobackend.demobackend.repository.LocalUserRepository;
import demobackend.demobackend.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    JWTService jwtService;
    @Autowired
    LocalUserRepository repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            String jwt = header.substring(7);
            try{
                String username = jwtService.getUsername(jwt);
                Optional<LocalUser> opUser= repository.findByUsername(username);
                if(opUser.isPresent()){
                    LocalUser user = opUser.get();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorization());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }catch (JWTDecodeException ex){
            }

        }
        filterChain.doFilter(request,response);
    }
}
