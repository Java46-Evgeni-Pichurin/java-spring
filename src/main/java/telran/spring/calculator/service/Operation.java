package telran.spring.calculator.service;

import telran.spring.calculator.dto.OperationData;

public interface Operation {
String execute(OperationData data);
String getOperationName();
}
