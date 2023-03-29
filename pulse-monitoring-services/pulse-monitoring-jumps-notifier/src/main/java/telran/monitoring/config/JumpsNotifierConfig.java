package telran.monitoring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import telran.monitoring.JumpsNotifierAppl;
import telran.monitoring.model.NotificationData;
import telran.monitoring.model.PulseJump;
import telran.monitoring.service.NotificationDataProvider;

import java.util.function.Consumer;

@Service
public class JumpsNotifierConfig {

    static Logger LOG = LoggerFactory.getLogger(JumpsNotifierAppl.class);

    JavaMailSender mailSender;

    NotificationDataProvider dataProvider;

    public JumpsNotifierConfig(JavaMailSender mailSender, NotificationDataProvider dataProvider) {
        this.mailSender = mailSender;
        this.dataProvider = dataProvider;
    }

    @Value("${app.mail.subject: Pulse Jump Notification}")
    String subject;

    @Bean
    Consumer<PulseJump> jumpsConsumer() {
        return this::jumpProcessing;
    }

    void jumpProcessing(PulseJump jump) {
        LOG.trace("received jump {}", jump);
        sendMail(jump);
    }

    private void sendMail(PulseJump jump) {
        NotificationData data = dataProvider.getData(jump.patientId);
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(data.doctorEmail);
        smm.setSubject(subject + " " + data.patientName);
        String text = getMailText(jump, data);
        smm.setText(text);
        mailSender.send(smm);
        LOG.trace("sent text mail {}", text);
    }

    private String getMailText(PulseJump jump, NotificationData data) {
        return String.format("""
                        Dear Dr. %s
                        Patient %s has pulse jump
                        previous value: %d; current value: %d
                        """,
                data.doctorName, data.patientName, jump.previousValue, jump.currentValue);
    }
}
