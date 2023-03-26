package avgerage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import avgerage.repo.AvgPulseDocRepository;

import java.time.LocalDateTime;

@Service
public class AvgValuesServiceImpl implements AvgValuesService{
    @Autowired
    AvgPulseDocRepository avgPulseDocRepository;

    @Override
    public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
        return avgPulseDocRepository.getAvgValue(patientId, from, to);
    }

    @Override
    public int getAvgValue(long patientId) {
        return avgPulseDocRepository.getAvgValue(patientId);
    }
}
