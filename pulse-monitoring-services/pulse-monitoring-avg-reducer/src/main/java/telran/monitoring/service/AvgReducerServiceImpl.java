package telran.monitoring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import telran.monitoring.entities.ProbesList;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.ProbeListRepository;

import java.util.ArrayList;

@Service
public class AvgReducerServiceImpl implements AvgReducerService {
    @Value("${app.reducing.size}")
    Integer listSize;

    @Autowired
    ProbeListRepository probeListRepository;

    @Override
    public Integer reduce(PulseProbe probe) {
        Integer res = null;
        ProbesList probesList = probeListRepository.findById(probe.patientId).orElse(null);
        if (probesList == null) {
            probesList = new ProbesList(probe.patientId, new ArrayList<>(listSize));
        } else if (probesList.getValues().size() == listSize) {
            res = (int) probesList.getValues().stream().mapToInt(Integer::intValue).summaryStatistics().getAverage();
            probesList.setValues(new ArrayList<>(listSize));
        } else {
            probesList.getValues().add(probe.value);
        }
        probeListRepository.save(probesList);
        return res;
    }
}
