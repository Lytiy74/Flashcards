package flashcards;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputOutputConsole implements AutoCloseable {

    private final Scanner scanner;
    private final List<String> logMessages;

    public InputOutputConsole() {
        this.scanner = new Scanner(System.in);
        this.logMessages = new ArrayList<>();
    }

    public void println(String message) {
        System.out.println(message);
        logMessages.add(message);
    }

    public void println() {
        println("");
    }
    public String nextLine() {
        String input = scanner.nextLine();
        logMessages.add(input);
        return input;
    }


    public void printf(String message, Object... args) {
        String formatted = String.format(message, args);
        System.out.println(formatted);
        logMessages.add(formatted);
    }

    public List<String> getLogMessages() {
        return List.copyOf(logMessages);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
