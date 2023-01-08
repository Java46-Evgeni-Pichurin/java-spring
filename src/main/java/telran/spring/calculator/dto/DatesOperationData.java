package telran.spring.calculator.dto;

import jakarta.validation.constraints.*;

public class DatesOperationData extends OperationData {
    @Pattern(regexp = "yyyy-MM-dd")
    @NotNull
    public String dateFrom;
    @Pattern(regexp = "yyyy-MM-dd")
    @NotNull
    public String dateTo;
}
