package telran.spring.controller;

import org.springframework.stereotype.Component;
import telran.spring.view.ConsoleInputOutput;

@Component
public class MessageSender extends ConsoleInputOutput {
    MessageMenuFactory menuFactory;
    public MessageSender(MessageMenuFactory menuFactory){
        this.menuFactory = menuFactory;
    }
    public void perform() {
        menuFactory.perform(this);
    }
}
