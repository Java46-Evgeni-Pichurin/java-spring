package telran.spring.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CalculatorAppl {
    public static void main(String[] args) {
        ConfigurableApplicationContext ct =
                SpringApplication.run(CalculatorAppl.class, args);
    }
}
