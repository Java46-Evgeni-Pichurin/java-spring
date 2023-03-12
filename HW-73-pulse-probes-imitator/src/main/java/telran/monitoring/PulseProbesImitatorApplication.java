package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import telran.imitator.PulseProbeImitatorImpl;
import telran.imitator.PulseProbesImitator;

@SpringBootApplication
public class PulseProbesImitatorApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(PulseProbesImitatorApplication.class, args);
	}

}
