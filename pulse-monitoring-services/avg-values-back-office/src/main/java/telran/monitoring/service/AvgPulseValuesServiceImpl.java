package telran.monitoring.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import telran.monitoring.repo.BackOfficeRepository;

@Service
public class AvgPulseValuesServiceImpl implements AvgPulseValuesService {
    BackOfficeRepository backOfficeRepository;

    public AvgPulseValuesServiceImpl(BackOfficeRepository backOfficeRepository) {
        this.backOfficeRepository = backOfficeRepository;
    }

    @Override
    public int getAvgPulseValue(long patientId) {
        return backOfficeRepository.getAvgValue(patientId);
    }

    @Override
    public int getAvgPulseValueDates(long patientId, LocalDateTime from, LocalDateTime to) {
        return backOfficeRepository.getAvgValue(patientId, from, to);
    }
}
