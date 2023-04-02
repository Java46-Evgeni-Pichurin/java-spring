package telran.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgPopulator;

import java.util.function.Consumer;

@Configuration
public class AvgPopulatorConfiguration {
    AvgPopulator avgPopulator;

    public AvgPopulatorConfiguration(AvgPopulator avgPopulator) {
        this.avgPopulator = avgPopulator;
    }

    @Bean
    Consumer<PulseProbe> avgPulseConsumer() {
        return avgPopulator::getAvgPulseConsumer;
    }

}
