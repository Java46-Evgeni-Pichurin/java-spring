package telran.monitoring.service;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.monitoring.entities.LastProbe;
import telran.monitoring.model.*;
import telran.monitoring.repo.AnalyzerRepository;

@Service
public class AnalyzerServiceImpl implements AnalyzerService {
    public static Logger LOG = LoggerFactory.getLogger(AnalyzerService.class);
    private final AnalyzerRepository analyzerRepository;
    private final StreamBridge streamBridge;
    @Value("${app.jumps.threshold:0.3}")
    private double jumpThreshold;
    @Value("${app.jumps.binding.name:jumps-out-0}")
    private String bindingName;

    public AnalyzerServiceImpl(AnalyzerRepository analyzerRepository, StreamBridge streamBridge) {
        this.analyzerRepository = analyzerRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    @Transactional
    public PulseJump processPulseProbe(PulseProbe probe) {
        PulseJump res = null;
        LastProbe lastProbe =
                analyzerRepository.findById(probe.patientId).orElse(null);
        if (lastProbe == null) {
            LOG.debug("for patient {} no saved values", probe.patientId);
            lastProbe = new LastProbe(probe.patientId, probe.value);
        } else if (isJump(lastProbe.getValue(), probe.value)) {
            res = new PulseJump(probe.patientId, lastProbe.getValue(), probe.value);
            LOG.debug("jump: patientId is {} previous value is {}, current value is {}", probe.patientId,
                    lastProbe.getValue(), probe.value);
        }
        LOG.trace("lastProbe value for patient {} last value {}", lastProbe.getPatientId(), lastProbe.getValue());
        lastProbe.setValue(probe.value);
        analyzerRepository.save(lastProbe);
        return res;
    }

    private boolean isJump(int lastValue, int currentValue) {
        return Math.abs(currentValue - lastValue) >= lastValue * jumpThreshold;
    }

    @Override
    public void pulseProbeAnalyzing(PulseProbe pulseProbe) {
        PulseJump pulseJump = this.processPulseProbe(pulseProbe);
        if(pulseJump != null) {
            streamBridge.send(bindingName, pulseJump);
        }
    }
}
