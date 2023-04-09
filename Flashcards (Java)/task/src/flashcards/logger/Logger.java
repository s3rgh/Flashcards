package flashcards.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {
    private static Logger logger;
    private static final List<String> log = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private Logger() {
    }

    public static Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }

    public String nextLine() {
        String line = scanner.nextLine();
        log.add(line);
        return line;
    }

    public void println(String str) {
        log.add(str);
        System.out.println(str);
    }

    public void printf(String format, Object... args) {
        String str = String.format(format, args);
        log.add(str);
        System.out.printf(format, args);
    }

    public static void log() {
        logger.println("File name:");
        var filePath = logger.nextLine();
        try {
            Path path = Path.of(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.write(Files.createFile(path), log);
            logger.println("The log has been saved.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
