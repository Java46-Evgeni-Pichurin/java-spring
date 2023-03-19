package telran.monitoring.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "doctors")
public class DoctorEntity {
    @Id
    String email;
    String name;
    @OneToMany(mappedBy = "doctors", cascade = CascadeType.REMOVE)
    List<VisitEntity> visits;

    public DoctorEntity(String email, String name, List<VisitEntity> visits) {
        this.email = email;
        this.name = name;
        this.visits = visits;
    }

    public DoctorEntity() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
