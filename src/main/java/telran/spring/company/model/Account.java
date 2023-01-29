package telran.spring.company.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Email @NotNull
    public String username;
    @Size(min = 6) @NotNull
    public String password;
    @NotNull
    public String role;
}
