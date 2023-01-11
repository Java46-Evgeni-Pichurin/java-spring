package telran.spring.calculator.controller;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
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
    List<Operation> operationsList;

    public CalculatorController(List<Operation> operationsList) {
        this.operationsList = operationsList;
    }

    @PostMapping
    String getOperationResult(@RequestBody @Valid OperationData data) {
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
        operationServices = operationsList.stream().collect(Collectors.toMap(Operation::getServiceName, Function.identity()));
    }
}
