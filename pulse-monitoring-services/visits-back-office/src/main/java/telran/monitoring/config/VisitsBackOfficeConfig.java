package telran.monitoring.config;

import org.springframework.context.annotation.Configuration;
import telran.monitoring.service.DbInitService;

import javax.annotation.PostConstruct;

@Configuration
public class VisitsBackOfficeConfig {

    DbInitService dbInitService;

    public VisitsBackOfficeConfig(DbInitService dbInitService) {
        this.dbInitService = dbInitService;
    }

    @PostConstruct
    void dbInit() {
        dbInitService.dbInit();
    }
}