package telran.spring.calculator.dto;

import jakarta.validation.constraints.*;

public class DateDaysOperationData extends OperationData {
    // yyyy-MM-dd format
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[01])$")
    @NotNull
    public String date;
    @Min(value = 0)
    @NotNull
    public int days;
}
