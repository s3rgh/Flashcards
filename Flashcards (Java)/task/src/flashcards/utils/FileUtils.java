package flashcards.utils;

import flashcards.logger.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtils {

    public static Logger logger = Logger.getLogger();

    public static void download(String filePath, Map<String, String> flashCards, Map<String, Integer> errors) {
        List<String> list;
        try {
            Path path = Path.of(filePath);
            if (!Files.exists(path)) {
                logger.println("File not found.");
            } else {
                list = Files.readAllLines(path);
                for (String s : list) {
                    flashCards.put(s.split(",")[0], s.split(",")[1]);
                    errors.put(s.split(",")[0], Integer.parseInt(s.split(",")[2]));
                }
                logger.printf(String.format("%d cards have been loaded.\n", list.size()));
            }
        } catch (IOException ex) {
            logger.println("File not found.");
            throw new RuntimeException("File not found.", ex);
        }
    }

    public static void upload(String filePath, Map<String, String> flashCards, Map<String, Integer> errors) {
        try {
            List<String> list = new ArrayList<>(flashCards.size());
            Path path = Path.of(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            for (Map.Entry<String, String> entry : flashCards.entrySet()) {
                list.add(entry.getKey() + "," + entry.getValue() + "," + errors.get(entry.getKey()));
            }

            Files.write(Files.createFile(path), list);
            logger.printf(String.format("%d cards have been saved.\n", list.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
