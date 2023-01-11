package telran.spring.calculator.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.DatesOperationData;
import telran.spring.calculator.dto.OperationData;

@Service()
public class DatesBetweenOperation implements Operation {
    @Value("${app.message.wrong.operation.date.format: Wrong date format }")
    String wrongDateFormatMessage;
    @Value("${app.message.wrong.additional.data: Wrong additional data }")
    String wrongAdditionalDataMessage;
    @Override
    public String execute(OperationData data) {
        try {
            DatesOperationData datesData = (DatesOperationData) data;
            if (datesData.additionalData != null) {
                LOG.error("Exception {}", wrongAdditionalDataMessage);
                return wrongAdditionalDataMessage;
            }
            LocalDate dateFrom = LocalDate.parse(datesData.dateFrom);
            LocalDate dateTo = LocalDate.parse(datesData.dateTo);
            String result = ChronoUnit.DAYS.between(dateFrom, dateTo) + "";
            LOG.debug("Message: {}", result);
            return result;
        } catch (DateTimeParseException e) {
            LOG.error("Exception {}", wrongDateFormatMessage);
            return wrongDateFormatMessage;
        } catch (Exception e) {
            LOG.error("Exception {}", e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String getServiceName() {
        return "dates-between";
    }
}
