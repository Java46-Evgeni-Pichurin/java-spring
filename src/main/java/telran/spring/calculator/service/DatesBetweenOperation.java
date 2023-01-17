package telran.spring.calculator.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.DatesOperationData;
import telran.spring.calculator.dto.OperationData;
@Service
public class DatesBetweenOperation extends AbstractOperation {
	static Logger LOG = LoggerFactory.getLogger(DatesBetweenOperation.class);
	@Override
	public String execute(OperationData data) {
		String res;
		try {
			DatesOperationData datesData = (DatesOperationData) data;
			LOG.debug("Number days between {} and {}", datesData.dateFrom, datesData.dateTo);
			LocalDate dateFrom = LocalDate.parse(datesData.dateFrom);
			LocalDate dateTo = LocalDate.parse(datesData.dateTo);
			res = ChronoUnit.DAYS.between(dateFrom, dateTo) + "";
		} catch (ClassCastException e) {
			res = wrongDtoMessage;
			LOG.error("Dates between operation but received data for class {} ",
					data.getClass().getSimpleName());
		}
		return res;
	}

	@Override
	public String getOperationName() {
		return "dates-between";
	}

}
