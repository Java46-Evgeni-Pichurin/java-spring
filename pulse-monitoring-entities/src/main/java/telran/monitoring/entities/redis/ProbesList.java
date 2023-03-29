package telran.monitoring.entities.redis;

import java.util.*;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProbesList {
	@Id
	long patientId;
	List<Integer> pulseValues = new ArrayList<>();

	public ProbesList(long patientId) {
		this.patientId = patientId;
	}
}
