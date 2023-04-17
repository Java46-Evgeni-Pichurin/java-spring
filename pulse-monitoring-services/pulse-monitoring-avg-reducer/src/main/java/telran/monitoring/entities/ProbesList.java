package telran.monitoring.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

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
