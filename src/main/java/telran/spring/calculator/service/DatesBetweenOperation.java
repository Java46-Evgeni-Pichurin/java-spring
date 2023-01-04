package telran.spring.calculator.service;

import org.springframework.stereotype.Service;
import telran.spring.calculator.dto.OperationData;

@Service("dates between operation")
public class DatesBetweenOperation implements Operation {

    @Override
    public String execute(OperationData operationData) {
        return null;
    }
}
