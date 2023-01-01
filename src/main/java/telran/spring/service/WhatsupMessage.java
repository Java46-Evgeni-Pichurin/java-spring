package telran.spring.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service("whatsup")
public class WhatsupMessage implements Sender {

    @Override
    public void send(String text, String addressee) {
        System.out.printf("Whats message: \"%s\" has been sent to %s\n", text, addressee);
    }

    @Override
    public String info() {
        return "phone number";
    }

    @Override
    public Predicate<String> validation() {
        return s -> s.matches("^05\\d-?\\d{7}$");
    }
}