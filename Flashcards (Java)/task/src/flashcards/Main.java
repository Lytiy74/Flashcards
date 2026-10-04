package flashcards;

public class Main {
    public static void main(String[] args) {
        CliArgs cliArgs = CliArgs.parse(args);

        try (InputOutputConsole console = new InputOutputConsole()) {
            FlashcardGame flashcardGame = new FlashcardGame();
            Menu menu = new Menu(flashcardGame, console, cliArgs);
            menu.runMenu();
        }
    }
}
