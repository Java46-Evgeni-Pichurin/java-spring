package telran.spring.calculator.controller;

import org.springframework.web.bind.annotation.*;
import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.Operation;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("calculator")
public class CalculatorController {
    Map<String, Operation> operations;

    public CalculatorController(Map<String, Operation> operations) {
        this.operations = operations;
    }

    @PostMapping
    String calculate(@RequestBody OperationData operationData) {
        Operation operation = operations.get(operationData.operationName);
        return operation != null ? operation.execute(operationData) : "Wrong type " + operationData.operationName;
    }

    @GetMapping
    Set<String> getTypes() {
        return operations.keySet();
    }
}