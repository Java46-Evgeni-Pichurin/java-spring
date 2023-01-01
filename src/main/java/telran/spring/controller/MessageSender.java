package telran.spring.controller;

import java.util.*;

import org.springframework.stereotype.Component;

import telran.spring.service.Sender;
import telran.spring.view.ConsoleInputOutput;
import telran.spring.view.InputOutput;
import telran.spring.view.Item;
import telran.spring.view.menu.Menu;

@Component
public class MessageSender {
    Map<String, Sender> senders;

    public MessageSender(Map<String, Sender> senders) {

        this.senders = senders;
    }

    public void menu() {
//        Scanner scanner = new Scanner(System.in);
//        String line;
//        while (true) {
//            System.out.printf("enter type from %s or exit\n", senders.keySet());
//            line = scanner.nextLine();
//            if (line.equalsIgnoreCase("exit")) {
//                break;
//            }
//            Sender sender = senders.get(line);
//            if (sender == null) {
//                System.out.println(line + " type doesn't exist");
//            } else {
//                System.out.println("Enter text");
//                line = scanner.nextLine();
//                sender.send(line);
//            }
//        }

        Menu menu = new Menu("Main menu", getItems());
        menu.perform(new ConsoleInputOutput());
    }

    private ArrayList<Item> getItems() {
        return new ArrayList<>(List.of(
                Item.of("Sending message", this::send),
                Item.exit("Exit")));
    }

    private void send(InputOutput io) {
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
