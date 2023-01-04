package telran.spring.calculator.service;


import org.springframework.stereotype.Service;
import telran.spring.calculator.dto.DateDaysOperationData;
import telran.spring.calculator.dto.OperationData;

import java.time.LocalDate;

@Service("dates simple operation")
public class DatesSimpleOperation implements Operation {

    @Override
    public String execute(OperationData operationData) {
        DateDaysOperationData data = (DateDaysOperationData) operationData;
        return String.format("%s",
                getResult(data, "after") +
                        getResult(data, "before"));
    }

    private String getResult(DateDaysOperationData data, String plusMinus) {
        return String.format("The date %s %s from %s is %s", plusMinus, data.days, data.date,
                calculate(data, plusMinus));
    }

    private LocalDate calculate(DateDaysOperationData data, String plusMinus) {
        return plusMinus.equals("after") ?
                LocalDate.parse(data.date).plusDays(data.days) :
                LocalDate.parse(data.date).minusDays(data.days);
    }
}
