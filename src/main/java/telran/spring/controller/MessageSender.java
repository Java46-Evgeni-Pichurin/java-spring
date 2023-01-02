package telran.spring.controller;

import java.util.*;

import org.springframework.stereotype.Component;

import telran.spring.service.Sender;
import telran.spring.view.ConsoleInputOutput;
import telran.spring.view.InputOutput;
import telran.spring.view.Item;
import telran.spring.view.menu.Menu;

@Component
public class MessageSender extends Menu {
    static Map<String, Sender> senders;

    public MessageSender(Map<String, Sender> senders) {
        super("Main menu", getItems());
        MessageSender.senders = senders;
    }

    public void menu() {
        Menu menu = new Menu("Main menu", getItems());
        menu.perform(new ConsoleInputOutput());
    }

    private static ArrayList<Item> getItems() {
        return new ArrayList<>(List.of(
                Item.of("Sending message", MessageSender::send),
                Item.exit("Exit")));
    }

    private static void send(InputOutput io) {
        List<String> list = senders.keySet().stream().toList();
        String option = null;
        while (option == null) {
            option = io.readOption("Choose sender ", String.valueOf(list), list);
        }
        Sender sender = senders.get(option);
        String addressee = io.readPredicate(String.format("Enter addressee for %s - %s", option, sender.info()),
                "Wrong addressee",
                sender.validation());
        sender.send(io.readString("Enter message"), addressee);
    }
}
