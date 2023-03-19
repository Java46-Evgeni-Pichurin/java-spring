package telran.monitoring.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    long id;
    String name;
    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    List<VisitEntity> visits;

    public PatientEntity(long id, String name, List<VisitEntity> visits) {
        this.id = id;
        this.name = name;
        this.visits = visits;
    }

    public PatientEntity() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
