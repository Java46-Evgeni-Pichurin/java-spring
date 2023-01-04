package telran.spring.calculator.service;

import telran.spring.calculator.dto.OperationData;

public interface Operation {
    String perform(OperationData operationData);
}
