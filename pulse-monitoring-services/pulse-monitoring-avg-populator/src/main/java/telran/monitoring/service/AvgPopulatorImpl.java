package telran.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import telran.monitoring.AvgPopulatorAppl;
import telran.monitoring.entities.mongodb.AvgPulseDoc;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.AvgPopulatorRepository;

@Service
public class AvgPopulatorImpl implements AvgPopulator{
    static Logger LOG = LoggerFactory.getLogger(AvgPopulatorAppl.class);
    private final AvgPopulatorRepository avgPopulatorRepository;

    public AvgPopulatorImpl(AvgPopulatorRepository avgPopulatorRepository) {
        this.avgPopulatorRepository = avgPopulatorRepository;
    }
    @Override
    public void getAvgPulseConsumer(PulseProbe pulseProbe) {
        LOG.trace("received pulseprobe of patient {}", pulseProbe.patientId);
        AvgPulseDoc pulseDoc = AvgPulseDoc.of(pulseProbe);
        avgPopulatorRepository.save(pulseDoc);
    }
}
