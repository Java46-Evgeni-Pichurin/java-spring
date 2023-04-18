package telran.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.service.PulseProbesImitator;

import java.util.function.Supplier;

@Service
public class PulseImitatorConfig {
    PulseProbesImitator imitator;

    public PulseImitatorConfig(PulseProbesImitator imitator) {
        this.imitator = imitator;
    }

    @Bean
    Supplier<PulseProbe> pulseProbeSupplier() {
        return imitator::nextProbe;
    }
}
