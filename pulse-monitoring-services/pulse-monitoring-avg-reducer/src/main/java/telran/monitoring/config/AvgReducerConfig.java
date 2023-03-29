package telran.monitoring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.monitoring.AvgValuesReducerAppl;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.AvgReducerService;

import java.util.function.Consumer;

@Service
public class AvgReducerConfig {
    AvgReducerService service;
    StreamBridge streamBridge;

    public AvgReducerConfig(AvgReducerService service, StreamBridge streamBridge) {
        this.service = service;
        this.streamBridge = streamBridge;
    }

    @Value("${app.avg.binding.name}")
    String bindingName;
    static Logger LOG = LoggerFactory.getLogger(AvgValuesReducerAppl.class);

    @Bean
    Consumer<PulseProbe> pulseProbeConsumer() {
        return this::processPulseProbe;
    }

    void processPulseProbe(PulseProbe probe) {
        Integer avgValue = service.reduce(probe);
        if (avgValue != null) {
            LOG.debug("for patient {} avg value is {}", probe.patientId, avgValue);
            streamBridge.send(bindingName, new PulseProbe(probe.patientId, System.currentTimeMillis(), 0, avgValue));
        } else {
            LOG.trace("for patient {} no avg value yet", probe.patientId);
        }
    }
}