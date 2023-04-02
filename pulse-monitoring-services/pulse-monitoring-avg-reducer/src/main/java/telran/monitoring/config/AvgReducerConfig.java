package telran.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

import java.util.function.Consumer;

@Configuration
public class AvgReducerConfig {
    AvgReducerService service;

    public AvgReducerConfig(AvgReducerService service) {
        this.service = service;
    }

    @Bean
    Consumer<PulseProbe> pulseProbeConsumer() {
        return service::processPulseProbe;
    }
}