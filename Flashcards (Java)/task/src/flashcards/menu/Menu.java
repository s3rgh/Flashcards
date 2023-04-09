package flashcards.menu;

import flashcards.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static flashcards.utils.CardsUtil.*;

public class Menu {

    public static Logger logger = Logger.getLogger();

    public static void showMenu(Scanner scanner, Map<String, String> flashCards, Map<String, Integer> errors, String[] args) {
        Map<String, String> cliArguments = new HashMap<>();
        if (args != null) {
            if (args.length == 2) {
                cliArguments.put(args[0], args[1]);
            }
            if (args.length == 4) {
                cliArguments.put(args[0], args[1]);
                cliArguments.put(args[2], args[3]);
            }
        }

        if (cliArguments.containsKey("-import")) {
            importCard(cliArguments.get("-import"), flashCards, errors);
        }

        while (true) {
            logger.println("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String s = logger.nextLine();
            if ("add".equals(s)) {
                add(scanner, flashCards, errors);
            } else if ("remove".equals(s)) {
                remove(scanner, flashCards);
            } else if ("import".equals(s)) {
                importCard(scanner, flashCards, errors);
            } else if ("export".equals(s)) {
                export(scanner, flashCards, errors);
            } else if ("ask".equals(s)) {
                ask(scanner, flashCards, errors);
            } else if ("exit".equals(s)) {
                if (cliArguments.containsKey("-export")) {
                    export(cliArguments.get("-export"), flashCards, errors);
                }
                exit();
                break;
            } else if ("log".equals(s)) {
                log();
            } else if ("hardest card".equals(s)) {
                getHardestCard(errors);
            } else if ("reset stats".equals(s)) {
                resetStats(errors);
            }
        }
    }
}
