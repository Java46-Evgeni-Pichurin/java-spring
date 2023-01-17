package telran.spring.calculator.controller;


import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;
import telran.spring.calculator.dto.OperationData;
import telran.spring.calculator.service.Operation;

@RestController
@RequestMapping("calculator")
public class CalculatorController {
    static Logger LOG = LoggerFactory.getLogger(CalculatorController.class);
    List<Operation> operations;
    Map<String, Operation> operationServices;
    @Value("${app.message.wrong.operation.name}")
    String wrongOperationMessage;

    public CalculatorController(List<Operation> operations) {
        this.operations = operations;
    }

    @PostMapping
    String getOperationResult(@RequestBody @Valid OperationData data) {
        LOG.debug("operation request with type: {}, additional data: {},"
                        + " class of Operation data is {}",
                data.operationName, data.additionalData, data.getClass().getSimpleName());
        Operation operationService = operationServices.get(data.operationName);
        if (operationService == null) {
            LOG.error("operation {} is not implemented", data.operationName);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, wrongOperationMessage);
        }
        return operationService.execute(data);
    }

    @GetMapping
    Set<String> getAllOperationNames() {
        LOG.debug("request for getting operation names");
        return operationServices.keySet();
    }

    @PostConstruct
    void createMapOperationsServices() {
        operationServices = operations.stream()
                .collect(Collectors.toMap(Operation::getOperationName, service -> service));
        LOG.info("the operation names are {}", operationServices.keySet());
    }


}
