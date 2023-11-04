package demobackend.demobackend.api.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String jwt;
    private String status;
}
