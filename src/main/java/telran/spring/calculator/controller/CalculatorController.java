package telran.spring.calculator.controller;


import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.Operation;

@RestController
@RequestMapping("calculator")
public class CalculatorController {
    @Value("${app.message.wrong.cast: Wrong casting }")
    String wrongTypeMessage;
    Map<String, Operation> operationServices;

    public CalculatorController(Map<String, Operation> operationServices) {
        this.operationServices = operationServices;
    }

    @PostMapping
    String getOperationResult(@RequestBody OperationData data) {
        Operation operationService = operationServices.get(data.operationName);
        return operationService != null ? operationService.execute(data) :
                String.format("%s Should be one of the following %s", wrongTypeMessage, operationServices.keySet());
    }

    @GetMapping
    Set<String> getAllOperationNames() {
        return operationServices.keySet();
    }


}
