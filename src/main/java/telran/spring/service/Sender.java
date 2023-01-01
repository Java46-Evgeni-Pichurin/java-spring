package telran.spring.service;


import java.util.function.Predicate;

public interface Sender {
    void send(String text, String addressee);

    String info();

    Predicate<String> validation();
}
