package telran.spring.service;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service("TCP")
public class TcpSender implements Sender {

    @Override
    public void send(String text, String addressee) {
        System.out.printf("TCP message: \"%s\" has been sent to %s\n", text, addressee);
    }

    @Override
    public String info() {
        return "ip:port";
    }

    @Override
    public Predicate<String> validation() {
        return s -> s.matches("[1-2][0-4]?\\d?.[0-2][0-5]?[0-5]?.[0-2][0-5]?[0-5]?.[0-2][0-5]?[0-5]?:[1-9]\\d\\d?\\d?\\d?");
    }

}
