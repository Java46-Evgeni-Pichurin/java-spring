package telran.spring.calculator.controller;


import java.util.*;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.Operation;

@RestController
@RequestMapping("calculator")
public class CalculatorController {
    @Value("${app.message.wrong.cast: Wrong casting }")
    String wrongTypeMessage;
    Map<String, Operation> operationServices = new HashMap<>();
    List<Operation> operationsList;

    public CalculatorController(List<Operation> operationsList) {
        this.operationsList = operationsList;
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

    @PostConstruct
    void convertToOperationNamesMap() {
        List<String> operationNames = operationsList.stream().map(operation -> operation.getClass().getSimpleName()
                .replaceAll("Operation", "")
                .replaceAll("(?=[A-Z]+)", "-")
                .replaceFirst("-", "")
                .toLowerCase()).toList();
        for (int i = 0; i < operationNames.size(); i++) {
            operationServices.put(operationNames.get(i), operationsList.get(i));
        }
    }
}
