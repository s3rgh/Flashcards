package flashcards;

import java.util.*;

import static flashcards.menu.Menu.showMenu;

public class Game {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, String> flashCards = new LinkedHashMap<>();
    private static final Map<String, Integer> errors = new LinkedHashMap<>();

    public static void run(String[] args) {
        showMenu(scanner, flashCards, errors, args);
        scanner.close();
    }
}
