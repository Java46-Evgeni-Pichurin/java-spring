package telran.spring.calculator.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@Type(ArithmeticOperationData.class),
	@Type(DateDaysOperationData.class), @Type(DatesOperationData.class)})
public class OperationData {
	public static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
public String operationName;
public String additionalData;
}
