package demobackend.demobackend.api.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationBody {
    @NotNull
    @Size(max=32,min=3)
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(max=32,min=6)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    private String password;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
}
