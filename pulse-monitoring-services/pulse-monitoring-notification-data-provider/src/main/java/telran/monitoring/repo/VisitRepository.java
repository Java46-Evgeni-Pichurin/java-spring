package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.monitoring.entities.VisitEntity;
import telran.monitoring.model.NotificationData;


public interface VisitRepository extends JpaRepository<VisitEntity, Long> {
    NotificationData findAllByPatientIdOrderByLocalDateDesc(Long patientId);
}
