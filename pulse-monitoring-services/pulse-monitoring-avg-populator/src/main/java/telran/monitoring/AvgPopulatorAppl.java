package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class AvgPopulatorAppl {
    public static void main(String[] args) {
        SpringApplication.run(AvgPopulatorAppl.class, args);
    }
}