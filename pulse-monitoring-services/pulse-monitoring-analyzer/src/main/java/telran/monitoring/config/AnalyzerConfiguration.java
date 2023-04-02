package telran.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AnalyzerService;

import java.util.function.Consumer;

@Configuration
public class AnalyzerConfiguration {
    AnalyzerService analyzerService;

    public AnalyzerConfiguration(AnalyzerService analyzerService) {
        this.analyzerService = analyzerService;
    }

    @Bean
    Consumer<PulseProbe> pulseProbeConsumer() {
        return analyzerService::pulseProbeAnalyzing;
    }
}
