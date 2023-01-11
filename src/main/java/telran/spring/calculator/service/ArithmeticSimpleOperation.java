package telran.spring.calculator.service;

import java.util.*;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.*;

@Service()
public class ArithmeticSimpleOperation implements Operation {
    @Value("${app.message.wrong.operation.arithmetic: Wrong operation }")
    String wrongOperationMessage;
    private static Map<String, BiFunction<Double, Double, String>> operations;

    static {
        operations = new HashMap<>();
        operations.put("*", (o1, o2) -> o1 * o2 + "");
        operations.put("-", (o1, o2) -> o1 - o2 + "");
        operations.put("+", (o1, o2) -> o1 + o2 + "");
        operations.put("/", (o1, o2) -> o1 / o2 + "");
    }

    @Override
    public String execute(OperationData data) {
        try {
            ArithmeticOperationData arithmeticData = (ArithmeticOperationData) data;
            var function = operations.getOrDefault(data.additionalData,
                    (o1, o2) -> wrongOperationMessage);
            String result = function.apply(arithmeticData.operand1, arithmeticData.operand2);
            LOG.debug("Message: {}", result);
            return result;
        } catch (Exception e) {
            LOG.error("Exception {}", e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String getServiceName() {
        return "arithmetic-operation";
    }
}
