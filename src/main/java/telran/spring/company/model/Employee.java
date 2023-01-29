package telran.spring.company.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final long MIN_SALARY = 5000;
    private static final long MAX_SALARY = 45000;

    public Integer id;
    @NotNull
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Name must start with a capital letter and contains letters only")
    public String firstName;
    @NotNull
    @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Last name must start with a capital letter and contains letters only")
    public String lastName;
    @NotNull @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date should be in format YYYY-MM-DD")
    public String birthDay;
    @NotNull
    @Min(value = MIN_SALARY, message = "Min value of salary is " + MIN_SALARY)
    @Max(value = MAX_SALARY, message = "Max value of salary is " + MAX_SALARY)
    public Integer salary;
}
