package telran.spring.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service("email")
public class EmailsSender implements Sender {

    @Override
    public void send(String text, String addressee) {
        System.out.printf("Email message: \"%s\" has been sent to %s\n", text, addressee);
    }

    @Override
    public String info() {
        return "email address";
    }

    @Override
    public Predicate<String> validation() {
        return s -> s.matches("\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*.\\w{2,4}");
    }
}
