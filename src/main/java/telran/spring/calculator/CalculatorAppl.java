package telran.spring.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class CalculatorAppl {

    public static void main(String[] args) {
        ConfigurableApplicationContext ct = SpringApplication.run(CalculatorAppl.class, args);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("For shutdown type 'exit'");
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
        }
        ct.close();
        scanner.close();
    }
}
