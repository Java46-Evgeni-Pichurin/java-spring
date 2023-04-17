package telran.monitoring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import telran.monitoring.AvgPopulatorAppl;
import telran.monitoring.entities.AvgPulseDoc;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.AvgPulseRepository;

import java.util.function.Consumer;

@Configuration
public class AvgPopulatorConfiguration {

    static Logger LOG = LoggerFactory.getLogger(AvgPopulatorAppl.class);
    private final AvgPulseRepository avgPulseRepository;

    public AvgPopulatorConfiguration(AvgPulseRepository avgPulseRepository) {
        this.avgPulseRepository = avgPulseRepository;
    }

    @Bean
    Consumer<PulseProbe> avgPulseConsumer() {
        return this::getAvgPulseConsumer;
    }

    public void getAvgPulseConsumer(PulseProbe pulseProbe) {
        LOG.trace("received pulseprobe of patient {}", pulseProbe.patientId);
        AvgPulseDoc pulseDoc = AvgPulseDoc.of(pulseProbe);
        avgPulseRepository.save(pulseDoc);
    }
}
