package telran.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class PulseProbe {
    public long patientId;
    public long timestamp;
    public long sequenceNumber;
    public int value;

    @Override
    public String toString() {
        return "PulseProbe [patientId=" + patientId + ", timestamp=" + timestamp + ", sequenceNumber=" + sequenceNumber
                + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PulseProbe other = (PulseProbe) obj;
        return patientId == other.patientId && value == other.value;
    }
}
