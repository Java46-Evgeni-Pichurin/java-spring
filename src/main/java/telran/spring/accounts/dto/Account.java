package telran.spring.accounts.dto;

import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;

public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Email @NotNull
    public String username;
    @Size(min = 6) @NotNull
    public String password;
    @Pattern(regexp = "USER|ADMIN") @NotNull
    public String role;
}
