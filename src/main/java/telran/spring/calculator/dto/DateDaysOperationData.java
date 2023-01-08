package telran.spring.calculator.dto;

import jakarta.validation.constraints.*;

public class DateDaysOperationData extends OperationData {
    @Pattern(regexp = "yyyy-MM-dd")
    @NotNull
    public String date;
    @Min(value = 0)
    @NotNull
    public int days;
}
