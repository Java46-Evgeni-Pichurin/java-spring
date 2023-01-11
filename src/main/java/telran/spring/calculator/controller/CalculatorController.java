package telran.spring.calculator.controller;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.Operation;


@RestController
@RequestMapping("calculator")
public class CalculatorController {
    static Logger LOG = LoggerFactory.getLogger(Operation.class);
    @Value("${app.message.wrong.cast: Wrong casting }")
    String wrongTypeMessage;
    Map<String, Operation> operationServices;
    List<Operation> operationsList;

    public CalculatorController(List<Operation> operationsList) {
        this.operationsList = operationsList;
    }

    @PostMapping
    String getOperationResult(@RequestBody @Valid OperationData data) {
        LOG.debug("received request for operation: {}", data.operationName);
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
        LOG.info("application context is created with types {}", operationServices.keySet());
    }

    @PreDestroy
    void shutdown() {
        System.out.println("Server closed by graceful shutdown");
        LOG.info("shutdown performed");
    }

}
