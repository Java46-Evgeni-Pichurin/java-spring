package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AvgValuesReducerAppl {
    public static void main(String[] args) {
        SpringApplication.run(AvgValuesReducerAppl.class, args);
    }
}