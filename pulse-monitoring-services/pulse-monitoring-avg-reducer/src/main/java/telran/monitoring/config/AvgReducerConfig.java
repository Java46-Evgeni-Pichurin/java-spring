package telran.monitoring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

import java.util.function.Consumer;

@Configuration
public class AvgReducerConfig {
    static Logger LOG = LoggerFactory.getLogger(AvgReducerService.class);
    @Value("${app.avg.binding.name}")
    String bindingName;
    private final StreamBridge streamBridge;
    private final AvgReducerService service;

    public AvgReducerConfig(StreamBridge streamBridge, AvgReducerService service) {
        this.streamBridge = streamBridge;
        this.service = service;
    }

    @Bean
    Consumer<PulseProbe> pulseProbeConsumer() {
        return this::processPulseProbe;
    }

    public void processPulseProbe(PulseProbe probe) {
        Integer avgValue = service.reduce(probe);
        if (avgValue != null) {
            LOG.debug("for patient {} avg value is {}", probe.patientId, avgValue);
            streamBridge.send(bindingName, new PulseProbe(probe.patientId, System.currentTimeMillis(), 0, avgValue));
        } else {
            LOG.trace("for patient {} no avg value yet", probe.patientId);
        }
    }
}