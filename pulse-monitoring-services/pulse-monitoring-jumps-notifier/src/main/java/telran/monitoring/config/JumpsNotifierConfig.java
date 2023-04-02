package telran.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import telran.monitoring.model.PulseJump;
import telran.monitoring.service.NotificationDataProvider;

import java.util.function.Consumer;

@Configuration
public class JumpsNotifierConfig {

    private final NotificationDataProvider dataProvider;

    public JumpsNotifierConfig(NotificationDataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Bean
    Consumer<PulseJump> jumpsConsumer() {
        return dataProvider::jumpProcessing;
    }

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
