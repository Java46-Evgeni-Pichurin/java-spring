package telran.monitoring.model;

import lombok.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class PulseJump {

    public long patientId;
    public int previousValue;
    public int currentValue;
    public long timestamp;

    public PulseJump(long patientId, int previousValue, int currentValue) {
        this.patientId = patientId;
        this.previousValue = previousValue;
        this.currentValue = currentValue;
    }

    @Override
    public String toString() {
        return "PulseJump [patientId=" + patientId + ", previousValue=" + previousValue + ", currentValue=" + currentValue
                + ", timestamp=" + timestamp + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentValue, patientId, previousValue);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PulseJump other = (PulseJump) obj;
        return currentValue == other.currentValue && patientId == other.patientId && previousValue == other.previousValue;
    }
}
