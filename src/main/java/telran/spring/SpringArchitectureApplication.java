package telran.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import telran.spring.controller.MessageMenuFactory;
import telran.spring.controller.MessageSender;
import telran.spring.view.ConsoleInputOutput;

@SpringBootApplication
public class SpringArchitectureApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ct =
				SpringApplication.run(SpringArchitectureApplication.class, args);
		MessageSender messageMenuFactory = ct.getBean(MessageSender.class);
		messageMenuFactory.perform();
		ct.close();
	}
}
