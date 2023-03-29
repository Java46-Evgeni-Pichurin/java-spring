package telran.monitoring.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.monitoring.entities.jpa.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
