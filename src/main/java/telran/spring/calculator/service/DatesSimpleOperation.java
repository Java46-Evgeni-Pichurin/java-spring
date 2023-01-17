package telran.spring.calculator.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import telran.spring.calculator.dto.DateDaysOperationData;
import telran.spring.calculator.dto.OperationData;
@Service
public class DatesSimpleOperation extends AbstractOperation {
	static Logger LOG = LoggerFactory.getLogger(DatesSimpleOperation.class);
	@Override
	public String execute(OperationData data) {
		String res;
		try {
			DateDaysOperationData dateData = (DateDaysOperationData) data;
			LOG.debug("computing date {} days {} from {}", data.additionalData,
					dateData.days, dateData.date);
			LocalDate date = LocalDate.parse(dateData.date);
			int days = dateData.days;
			if(data.additionalData.equalsIgnoreCase("before")) {
				days = -days;
			}
			res = date.plusDays(days).toString();
		} catch (ClassCastException e) {
			res = wrongDtoMessage;
			LOG.error("Date after/before finding but received data for class {} ",
					data.getClass().getSimpleName());
		} 
		
		
		
		return res;
	}

	@Override
	public String getOperationName() {

		return "dates-simple";
	}

}
