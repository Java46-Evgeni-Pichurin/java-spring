package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class JumpsNotifierAppl {
    public static void main(String[] args) {
        SpringApplication.run(JumpsNotifierAppl.class, args);
    }
}