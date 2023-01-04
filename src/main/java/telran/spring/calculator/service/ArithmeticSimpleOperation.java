package telran.spring.calculator.service;

import org.springframework.stereotype.Service;
import telran.spring.calculator.dto.ArithmeticOperationData;
import telran.spring.calculator.dto.OperationData;

@Service("arithmetic operation")
public class ArithmeticSimpleOperation implements Operation {

    @Override
    public String execute(OperationData operationData) {
        ArithmeticOperationData data = (ArithmeticOperationData) operationData;
        return String.format("%s",
                getResult(data, "+", "Sum") +
                getResult(data, "-", "Difference") +
                getResult(data, "*", "Multiplication") +
                getResult(data, "/", "Division"));
    }

    private String getResult(ArithmeticOperationData data, String operation, String operationName) {
        return String.format("%s: %s\n",
                operationName,
                Double.parseDouble(String.format("%f%s%f", data.operand1, operation, data.operand2)));
    }
}
