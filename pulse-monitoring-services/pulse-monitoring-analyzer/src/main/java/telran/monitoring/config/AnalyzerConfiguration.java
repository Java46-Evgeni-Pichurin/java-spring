package telran.monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telran.monitoring.model.PulseJump;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AnalyzerService;

import java.util.function.Consumer;

@Configuration
public class AnalyzerConfiguration {
    @Value("${app.jumps.binding.name:jumps-out-0}")
    private String bindingName;
    private final AnalyzerService analyzerService;
    private final StreamBridge streamBridge;

    public AnalyzerConfiguration(AnalyzerService analyzerService, StreamBridge streamBridge) {
        this.analyzerService = analyzerService;
        this.streamBridge = streamBridge;
    }

    @Bean
    Consumer<PulseProbe> pulseProbeConsumer() {
        return this::pulseProbeAnalyzing;
    }

    public void pulseProbeAnalyzing(PulseProbe pulseProbe) {
        PulseJump pulseJump = analyzerService.processPulseProbe(pulseProbe);
        if(pulseJump != null) {
            streamBridge.send(bindingName, pulseJump);
        }
    }
}
