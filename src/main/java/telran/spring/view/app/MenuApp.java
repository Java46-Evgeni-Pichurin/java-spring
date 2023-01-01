package telran.spring.view.app;

import telran.spring.view.*;
import telran.spring.view.menu.DateOperationsMenu;
import telran.spring.view.menu.Menu;
import telran.spring.view.menu.NumbersOperationsMenu;

public class MenuApp {
    public static void main(String[] args) {
        Menu menu = new Menu("Main menu", getItems());
        menu.perform(new ConsoleInputOutput());

    }

    private static Item[] getItems() {
        return new Item[]{
                NumbersOperationsMenu.getNumbersOperationsMenu(),
                DateOperationsMenu.getDateOperationsMenu(),
                Item.exit("Exit")
        };
    }
}
