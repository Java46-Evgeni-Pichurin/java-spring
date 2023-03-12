package telran.monitoring;

import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import telran.imitator.PulseProbeImitatorImpl;
import telran.imitator.PulseProbesImitator;

@SpringBootApplication
public class PulseProbesImitatorApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext cx = SpringApplication.run(PulseProbesImitatorApplication.class, args);
		var imitator = cx.getBean(PulseProbeImitatorImpl.class);
		for (int i = 0; i < 10; i++) {
			imitator.nextProbe();
		}

	}

}
