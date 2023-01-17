package telran.spring.calculator.dto;

import jakarta.validation.constraints.Pattern;

public class DatesOperationData extends OperationData {
	@Pattern(regexp = OperationData.DATE_PATTERN)
	public String dateFrom;
	@Pattern(regexp = OperationData.DATE_PATTERN)
	public String dateTo;
}
