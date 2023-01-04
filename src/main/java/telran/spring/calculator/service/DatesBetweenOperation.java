package telran.spring.calculator.service;

import org.springframework.stereotype.Service;
import telran.spring.calculator.dto.ArithmeticOperationData;
import telran.spring.calculator.dto.DatesOperationData;
import telran.spring.calculator.dto.OperationData;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service("dates between operation")
public class DatesBetweenOperation implements Operation {

    @Override
    public String execute(OperationData operationData) {
        DatesOperationData data = (DatesOperationData) operationData;
        return String.valueOf(ChronoUnit.DAYS.between(LocalDate.parse(data.dateFrom), LocalDate.parse(data.dateTo)));
    }
}
