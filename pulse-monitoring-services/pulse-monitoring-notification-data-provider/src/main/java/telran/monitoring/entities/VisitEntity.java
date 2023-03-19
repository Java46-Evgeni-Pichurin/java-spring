package telran.monitoring.entities;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "visits")
public class VisitEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long id;
    LocalDate localDate;
    @ManyToOne
    @JoinColumn(name = "docid")
    DoctorEntity doctor;
    @ManyToOne
    @JoinColumn(name = "patid")
    PatientEntity patient;

    public VisitEntity(LocalDate localDate, DoctorEntity doctor, PatientEntity patient) {
        this.localDate = localDate;
        this.doctor = doctor;
        this.patient = patient;
    }

    public VisitEntity() {
    }

    public long getId() {
        return id;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public PatientEntity getPatient() {
        return patient;
    }
}
