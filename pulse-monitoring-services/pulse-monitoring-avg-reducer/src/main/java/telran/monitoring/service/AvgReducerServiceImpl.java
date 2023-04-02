package telran.monitoring.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.monitoring.entities.redis.ProbesList;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.AvgReducerRepository;

@Service
public class AvgReducerServiceImpl implements AvgReducerService {
    static Logger LOG = LoggerFactory.getLogger(AvgReducerService.class);
    private final AvgReducerRepository avgReducerRepository;
    private final StreamBridge streamBridge;
    @Value("${app.reducing.size: 3}")
    int reducingSize;
    @Value("${app.avg.binding.name}")
    String bindingName;

    public AvgReducerServiceImpl(AvgReducerRepository avgReducerRepository, StreamBridge streamBridge) {
        this.avgReducerRepository = avgReducerRepository;
        this.streamBridge = streamBridge;
    }

    @Transactional
    public Integer reduce(PulseProbe probe) {
        ProbesList probesList = avgReducerRepository.findById(probe.patientId)
                .orElse(null);
        Integer avgValue = null;
        if (probesList == null) {
            LOG.debug("for patient {} no saved pulse values", probe.patientId);
            probesList = new ProbesList(probe.patientId);
        } else {
            LOG.trace("for patient {} number of saved pulse values is {}",
                    probesList.getPatientId(), probesList.getPulseValues().size());
        }
        List<Integer> pulseValues = probesList.getPulseValues();
        pulseValues.add(probe.value);
        if (pulseValues.size() >= reducingSize) {
            avgValue = pulseValues.stream().collect(Collectors.averagingInt(x -> x)).intValue();
            pulseValues.clear();
        }
        avgReducerRepository.save(probesList);
        return avgValue;
    }

    @PostConstruct
    void inintDebugInfo() {
        LOG.debug("reducing size is {}", reducingSize);
    }

    @Override
    public void processPulseProbe(PulseProbe probe) {
        Integer avgValue = this.reduce(probe);
        if (avgValue != null) {
            LOG.debug("for patient {} avg value is {}", probe.patientId, avgValue);
            streamBridge.send(bindingName, new PulseProbe(probe.patientId, System.currentTimeMillis(), 0, avgValue));
        } else {
            LOG.trace("for patient {} no avg value yet", probe.patientId);
        }
    }
}
