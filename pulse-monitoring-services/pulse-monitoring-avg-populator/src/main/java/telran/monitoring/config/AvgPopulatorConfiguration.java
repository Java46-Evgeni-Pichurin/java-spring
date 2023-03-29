package telran.monitoring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.monitoring.AvgPopulatorAppl;
import telran.monitoring.entities.mongodb.AvgPulseDoc;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.AvgPopulatorRepository;

import java.util.function.Consumer;

@Service
public class AvgPopulatorConfiguration {
    static Logger LOG = LoggerFactory.getLogger(AvgPopulatorAppl.class);
    AvgPopulatorRepository avgPopulatorRepository;

    public AvgPopulatorConfiguration(AvgPopulatorRepository avgPopulatorRepository) {
        this.avgPopulatorRepository = avgPopulatorRepository;
    }

    @Bean
    Consumer<PulseProbe> avgPulseConsumer() {
        return this::getAvgPulseConsumer;
    }

    void getAvgPulseConsumer(PulseProbe pulseProbe) {
        LOG.trace("received pulseprobe of patient {}", pulseProbe.patientId);
        AvgPulseDoc pulseDoc = AvgPulseDoc.of(pulseProbe);
        avgPopulatorRepository.save(pulseDoc);
    }
}
