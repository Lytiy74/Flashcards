package flashcards;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Menu {
    private final FlashcardGame game;
    private final InputOutputConsole console;
    private final CliArgs cliArgs;

    public Menu(FlashcardGame game, InputOutputConsole console, CliArgs cliArgs) {
        this.game = game;
        this.console = console;
        this.cliArgs = cliArgs;
    }

    public void runMenu() {
        cliArgs.importFilename().ifPresent(this::importFrom);

        while (true) {
            console.println(Messages.MENU_PROMPT);
            Optional<Option> optionalOption = Option.findByValue(console.nextLine().toLowerCase());
            if (optionalOption.isEmpty()) {
                continue;
            }
            switch (optionalOption.get()) {
                case ADD -> addCard();
                case REMOVE -> removeCard();
                case IMPORT -> importFrom(askFilename());
                case EXPORT -> exportTo(askFilename());
                case ASK -> reviewCards();
                case EXIT -> {
                    console.println(Messages.BYE);
                    cliArgs.exportFilename().ifPresent(this::exportTo);
                    return;
                }
                case LOG -> saveLog();
                case HARDEST_CARD -> showHardestCards();
                case RESET_STATS -> {
                    game.resetStats();
                    console.println(Messages.STATS_RESET);
                }
            }
            console.println();
        }
    }

    private String askFilename() {
        console.println(Messages.FILE_NAME);
        return console.nextLine();
    }

    private void addCard() {
        console.println(Messages.CARD_PROMPT);
        String term = console.nextLine();
        if (game.hasTerm(term)) {
            console.printf(Messages.CARD_EXISTS, term);
            return;
        }
        console.println(Messages.DEFINITION_PROMPT);
        String definition = console.nextLine();
        switch (game.addCard(term, definition)) {
            case ADDED -> console.printf(Messages.CARD_ADDED, term, definition);
            case TERM_EXISTS -> console.printf(Messages.CARD_EXISTS, term);
            case DEFINITION_EXISTS -> console.printf(Messages.DEFINITION_EXISTS, definition);
        }
    }

    private void removeCard() {
        console.println(Messages.REMOVE_PROMPT);
        String term = console.nextLine();
        if (game.removeCard(term)) {
            console.println(Messages.CARD_REMOVED);
        } else {
            console.printf(Messages.CARD_NOT_FOUND, term);
        }
    }

    private void reviewCards() {
        console.println(Messages.ASK_COUNT_PROMPT);
        int count;
        try {
            count = Integer.parseInt(console.nextLine().trim());
        } catch (NumberFormatException _) {
            console.println(Messages.INVALID_NUMBER);
            return;
        }

        List<FlashCard> cards = game.getCards();
        for (int i = 0; i < count; i++) {
            Optional<FlashCard> picked = game.pickRandomCard(cards);
            if (picked.isEmpty()) {
                return;
            }
            FlashCard card = picked.get();
            console.printf(Messages.ASK_DEFINITION, card.getTerm());
            FlashcardGame.AnswerResult result = game.checkAnswer(card, console.nextLine());
            if (result.correct()) {
                console.println(Messages.CORRECT_ANSWER);
            } else {
                result.cardWithGivenDefinition().ifPresentOrElse(
                        other -> console.printf(Messages.WRONG_ANSWER_DEFINITION_EXISTS, card.getDefinition(), other.getTerm()),
                        () -> console.printf(Messages.WRONG_ANSWER, card.getDefinition())
                );
            }
        }
    }

    private void importFrom(String filename) {
        try {
            console.printf(Messages.CARDS_LOADED, game.importCards(filename));
        } catch (FileNotFoundException _) {
            console.println(Messages.FILE_NOT_FOUND);
        } catch (IOException _) {
            console.println(Messages.ERROR_READING_FILE);
        }
    }

    private void exportTo(String filename) {
        try {
            console.printf(Messages.CARDS_SAVED, game.exportCards(filename));
        } catch (FileNotFoundException _) {
            console.println(Messages.ERROR_SAVING_FILE);
        } catch (IOException _) {
            console.println(Messages.ERROR_WRITING_FILE);
        }
    }

    private void saveLog() {
        String filename = askFilename();
        try {
            game.saveLog(filename, console.getLogMessages());
            console.println(Messages.LOG_SAVED);
        } catch (FileNotFoundException _) {
            console.println(Messages.ERROR_SAVING_FILE);
        } catch (IOException _) {
            console.println(Messages.ERROR_WRITING_LOG);
        }
    }

    private void showHardestCards() {
        List<FlashCard> hardest = game.getHardestCards();
        if (hardest.isEmpty()) {
            console.println(Messages.NO_ERRORS);
        } else if (hardest.size() == 1) {
            FlashCard card = hardest.getFirst();
            console.printf(Messages.HARDEST_CARD, card.getTerm(), card.getMistakes());
        } else {
            String terms = hardest.stream()
                    .map(card -> "\"" + card.getTerm() + "\"")
                    .collect(Collectors.joining(", "));
            console.printf(Messages.HARDEST_CARDS, terms, hardest.getFirst().getMistakes());
        }
    }

    public enum Option {
        ADD("add"),
        REMOVE("remove"),
        IMPORT("import"),
        EXPORT("export"),
        ASK("ask"),
        EXIT("exit"),
        LOG("log"),
        HARDEST_CARD("hardest card"),
        RESET_STATS("reset stats");

        private final String value;

        Option(String s) {
            this.value = s;
        }

        public String getValue() {
            return value;
        }

        public static Optional<Option> findByValue(String s) {
            return Arrays.stream(Option.values()).filter(
                    option -> option.getValue().equals(s)
            ).findFirst();
        }
    }
}
