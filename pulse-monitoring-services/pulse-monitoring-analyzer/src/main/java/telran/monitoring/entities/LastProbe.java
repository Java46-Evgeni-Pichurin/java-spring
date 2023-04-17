package telran.monitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LastProbe {
    @Id
    long patientId;
    int value;

    public void setValue(int value) {
        this.value = value;
    }
}
