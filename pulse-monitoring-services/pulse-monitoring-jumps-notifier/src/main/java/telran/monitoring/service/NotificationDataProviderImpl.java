package telran.monitoring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import telran.monitoring.JumpsNotifierAppl;
import telran.monitoring.model.NotificationData;
import telran.monitoring.model.PulseJump;

@Service
public class NotificationDataProviderImpl implements NotificationDataProvider {
    private final RestTemplate restTemplate;
    private final JavaMailSender mailSender;
    static Logger LOG = LoggerFactory.getLogger(JumpsNotifierAppl.class);
    @Value("${app.data.provider.mapping.url:data}")
    String mappingUrl;
    @Value("${app.data.provider.host:localhost}")
    String host;
    @Value("${app.data.provider.port:8080}")
    int port;
    @Value("${app.mail.subject: Pulse Jump Notification}")
    String subject;

    public NotificationDataProviderImpl(@Lazy RestTemplate restTemplate, JavaMailSender mailSender) {
        this.restTemplate = restTemplate;
        this.mailSender = mailSender;
    }

    @Override
    public NotificationData getData(long patientId) {
        ResponseEntity<NotificationData> response =
                restTemplate.exchange(getFullUrl(patientId),
                        HttpMethod.GET, null, NotificationData.class);
        return response.getBody();
    }

    private String getFullUrl(long patientId) {
        return String.format("http://%s:%d/%s/%d", host, port, mappingUrl, patientId);
    }

    @Override
    public void jumpProcessing(PulseJump jump) {
        LOG.trace("received jump {}", jump);
        sendMail(jump);
    }

    private void sendMail(PulseJump jump) {
        NotificationData data = this.getData(jump.patientId);
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