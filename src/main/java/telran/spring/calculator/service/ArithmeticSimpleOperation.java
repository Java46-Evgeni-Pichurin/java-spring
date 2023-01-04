package telran.spring.calculator.service;

import org.springframework.stereotype.Service;
import telran.spring.calculator.dto.ArithmeticOperationData;
import telran.spring.calculator.dto.OperationData;

@Service("arithmetic operation")
public class ArithmeticSimpleOperation implements Operation {

    @Override
    public String perform(OperationData operationData) {
        ArithmeticOperationData data = (ArithmeticOperationData) operationData;
        return String.format("The result of arithmetic operation %s is %s",
                data.operand1 + data.operation + data.operand2, getResult(data));
    }

    private double getResult(ArithmeticOperationData data) {
        return Double.parseDouble(String.format("%f%s%f", data.operand1, data.operation, data.operand2));
    }
}
