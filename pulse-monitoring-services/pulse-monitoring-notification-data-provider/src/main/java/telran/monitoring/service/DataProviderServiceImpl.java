package telran.monitoring.service;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.monitoring.model.NotificationData;
import telran.monitoring.repo.*;

@Service
@Transactional(readOnly = true)
public class DataProviderServiceImpl implements DataProviderService{
    EntityManager em;
    VisitRepository visitRepository;
    DoctorRepository doctorRepository;
    PatientRepository patientRepository;

    public DataProviderServiceImpl(EntityManager em, VisitRepository visitRepository,
                                   DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.em = em;
        this.visitRepository = visitRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public NotificationData getNotificationData(long patientId) {
        return visitRepository.findAllByPatientIdOrderByLocalDateDesc(patientId);
    }
}
