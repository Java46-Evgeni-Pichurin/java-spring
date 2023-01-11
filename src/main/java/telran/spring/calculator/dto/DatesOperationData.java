package telran.spring.calculator.dto;

import jakarta.validation.constraints.*;

public class DatesOperationData extends OperationData {
    // MM/DD/YYYY format
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[01])$")
    @NotNull
    public String dateFrom;
    // MM/DD/YYYY format
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[01])$")
    @NotNull
    public String dateTo;
}
