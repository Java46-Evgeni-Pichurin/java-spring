package telran.monitoring.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash
public class ProbesList {
    @Id
    long patientId;
    List<Integer> values;

    public ProbesList(long patientId, List<Integer> values) {
        this.patientId = patientId;
        this.values = values;
    }

    public ProbesList() {
    }

    public long getPatientId() {
        return patientId;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }
}
