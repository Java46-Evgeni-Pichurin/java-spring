package telran.spring.calculator.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@Type(ArithmeticOperationData.class), @Type(DateDaysOperationData.class),
        @Type(DatesOperationData.class)})
public class OperationData {
    public String type;
    public String text;
}
