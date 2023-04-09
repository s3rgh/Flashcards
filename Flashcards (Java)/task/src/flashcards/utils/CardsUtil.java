package flashcards.utils;

import flashcards.logger.Logger;

import java.util.*;
import java.util.stream.Collectors;

import static flashcards.utils.FileUtils.download;
import static flashcards.utils.FileUtils.upload;

public class CardsUtil {

    public static Logger logger = Logger.getLogger();

    public static void add(Scanner scanner, Map<String, String> flashCards, Map<String, Integer> errors) {
        String term;
        String definition;
        logger.println("The card:");
        term = logger.nextLine();

        if (flashCards.containsKey(term)) {
            logger.printf(String.format("The card \"%s\" already exists.\n", term));
            return;
        }

        logger.println("The definition of the card:");
        definition = logger.nextLine();

        if (flashCards.containsValue(definition)) {
            logger.printf(String.format("The definition \"%s\" already exists.\n", definition));
            return;
        }

        flashCards.put(term, definition);
        errors.put(term, 0);
        logger.printf(String.format("The pair (\"%s\":\"%s\") has been added\n", term, definition));
    }

    public static void remove(Scanner scanner, Map<String, String> cards) {
        logger.println("Which card?");
        var term = logger.nextLine();
        if (!cards.containsKey(term)) {
            logger.printf(String.format("Can't remove \"%s\": there is no such card.\n", term));
        } else {
            cards.remove(term);
            logger.println("The card has been removed.");
        }
    }

    public static void importCard(Scanner scanner, Map<String, String> cards, Map<String, Integer> errors) {
        logger.println("File name:");
        download(logger.nextLine(), cards, errors);
    }

    public static void importCard(String s, Map<String, String> cards, Map<String, Integer> errors) {
        logger.println("File name:");
        download(s, cards, errors);
    }

    public static void export(Scanner scanner, Map<String, String> cards, Map<String, Integer> errors) {
        logger.println("File name:");
        upload(logger.nextLine(), cards, errors);
    }

    public static void export(String s, Map<String, String> cards, Map<String, Integer> errors) {
        logger.println("File name:");
        upload(s, cards, errors);
    }

    public static void ask(Scanner scanner, Map<String, String> cards, Map<String, Integer> errors) {
        logger.println("How many times to ask?");
        var number = Integer.parseInt(logger.nextLine());
        var counter = 0;
        while (counter != number) {
            for (Map.Entry<String, String> entry : cards.entrySet()) {
                logger.printf(String.format("Print the definition of \"%s\":\n", entry.getKey()));
                String definition = logger.nextLine();
                if (cards.containsValue(definition)) {
                    if (!entry.getValue().equals(definition)) {
                        var str = cards
                                .entrySet()
                                .stream()
                                .filter(e -> definition.equals(e.getValue()))
                                .map(Map.Entry::getKey)
                                .collect(Collectors.joining());

                        logger.printf(String.format("Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".\n",
                                entry.getValue(), str));
                        var mark = errors.get(entry.getKey());
                        mark++;
                        errors.put(entry.getKey(), mark);
                    } else {
                        logger.println("Correct!");
                    }
                } else {
                    logger.printf(String.format("Wrong. The right answer is \"%s\".\n", entry.getValue()));
                    var mark = errors.get(entry.getKey());
                    mark++;
                    errors.put(entry.getKey(), mark);
                }
                counter++;
            }
        }
    }

    public static void exit() {
        logger.println("Bye bye!");
    }

    public static void log() {
        Logger.log();
    }

    public static void getHardestCard(Map<String, Integer> errors) {
        if (errors.size() == 0) {
            logger.println("There are no cards with errors.");
            return;
        }
        var maxValue = 0;
        var counter = 0;
        var terms = new ArrayList<>();
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : errors.entrySet()) {
            if (maxEntry == null || entry.getValue()
                    .compareTo(maxEntry.getValue()) >= 0) {
                maxEntry = entry;
            }
        }
        maxValue = maxEntry.getValue();

        for (Map.Entry<String, Integer> entry : errors.entrySet()) {
            if (maxValue == entry.getValue()) {
                counter++;
                terms.add(String.format("\"%s\"", entry.getKey()));
            }
        }

        if (counter > 1 && maxValue > 0) {
            String commaSeparatedList = terms.toString();

            commaSeparatedList = commaSeparatedList
                    .replace("[", "")
                    .replace("]", "");
            logger.printf(String.format("The hardest cards are %s. You have %d errors answering them.", commaSeparatedList, maxValue));
        } else {
            logger.printf(String.format("The hardest card is %s. You have %d errors answering it.\n", terms.get(0), maxValue));
        }
    }

    public static void resetStats(Map<String, Integer> errors) {
        errors.clear();
        logger.println("Card statistics have been reset.");
    }
}
