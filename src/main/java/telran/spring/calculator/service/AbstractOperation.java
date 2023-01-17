package telran.spring.calculator.service;

import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractOperation implements Operation{
	@Value("${app.message.wrong.dto.fileds}")
	String wrongDtoMessage;
	
}
