package telran.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    public long id;
    public String name;

    @Override
    public String toString() {
        return "PatientDto [id=" + id + ", name=" + name + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PatientDto other = (PatientDto) obj;
        return id == other.id && Objects.equals(name, other.name);
    }
}
