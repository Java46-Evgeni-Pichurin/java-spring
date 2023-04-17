package telran.monitoring.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "visits", indexes = {@Index(columnList = "patient_id")})
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    LocalDate date;
    @ManyToOne
    @JoinColumn(name = "doctor_email")
    Doctor doctor;
    @JoinColumn(name = "patient_id")
    @ManyToOne
    Patient patient;

    public Visit(LocalDate date, Doctor doctor, Patient patient) {
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }
}
