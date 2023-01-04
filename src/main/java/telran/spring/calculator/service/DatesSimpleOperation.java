package telran.spring.calculator.service;


import org.springframework.stereotype.Service;
import telran.spring.calculator.dto.OperationData;

@Service("dates simple operation")
public class DatesSimpleOperation implements Operation {

    @Override
    public String execute(OperationData operationData) {
        return null;
    }
}
