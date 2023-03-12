package telran.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import telran.imitator.PulseProbeImitatorImpl;
import telran.imitator.PulseProbesImitator;

@SpringBootApplication
public class PulseProbesImitatorApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(PulseProbesImitatorApplication.class, args);
		int x = 0;
		PulseProbesImitator pulseProbesImitator = new PulseProbeImitatorImpl();
		while (x < 100) {
			Thread.sleep(10);
			pulseProbesImitator.nextProbe();
			x++;
		}
	}

}
