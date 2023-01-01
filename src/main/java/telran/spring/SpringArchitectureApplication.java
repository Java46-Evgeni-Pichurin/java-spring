package telran.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import telran.spring.controller.MessageSender;

@SpringBootApplication
public class SpringArchitectureApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext ct =
				SpringApplication.run(SpringArchitectureApplication.class, args);
		MessageSender messageSender = ct.getBean(MessageSender.class);
		messageSender.menu();
		ct.close();
	}

}
