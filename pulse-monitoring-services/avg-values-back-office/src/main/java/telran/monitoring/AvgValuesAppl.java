package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "telran.monitoring.repositories")
public class AvgValuesAppl {
    public static void main(String[] args) {
        SpringApplication.run(AvgValuesAppl.class, args);
    }
}
