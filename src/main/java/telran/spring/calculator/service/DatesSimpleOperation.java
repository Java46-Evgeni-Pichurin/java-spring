package telran.spring.calculator.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.DateDaysOperationData;
import telran.spring.calculator.dto.OperationData;

@Service("dates-simple")
public class DatesSimpleOperation implements Operation {
    @Value("${app.message.wrong.operation.date.format: Wrong date format }")
    String wrongDateFormatMessage;
    @Value("${app.message.wrong.additional.data: Wrong additional data }")
    String wrongAdditionalDataMessage;

    @Override
    public String execute(OperationData data) {
        try {
            DateDaysOperationData dateData = (DateDaysOperationData) data;
            LocalDate date = LocalDate.parse(dateData.date);
            int days = dateData.days;
            if (data.additionalData != null) {
                if (data.additionalData.equalsIgnoreCase("before")) {
                    days = -days;
                }
                else {
                    return wrongAdditionalDataMessage;
                }

            }
            return date.plusDays(days).toString();
        } catch (DateTimeParseException e) {
            return wrongDateFormatMessage;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
