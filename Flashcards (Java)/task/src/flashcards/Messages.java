package flashcards;

public final class Messages {
    public static final String MENU_PROMPT = "Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):";
    public static final String BYE = "Bye bye!";
    public static final String FILE_NAME = "File name:";

    public static final String CARD_PROMPT = "The card:";
    public static final String DEFINITION_PROMPT = "The definition of the card:";
    public static final String CARD_ADDED = "The pair (\"%s\":\"%s\") has been added.";
    public static final String CARD_EXISTS = "The card \"%s\" already exists.";
    public static final String DEFINITION_EXISTS = "The definition \"%s\" already exists.";

    public static final String REMOVE_PROMPT = "Which card?";
    public static final String CARD_REMOVED = "The card has been removed.";
    public static final String CARD_NOT_FOUND = "Can't remove \"%s\": there is no such card.";

    public static final String ASK_COUNT_PROMPT = "How many times to ask?";
    public static final String INVALID_NUMBER = "Invalid number.";
    public static final String ASK_DEFINITION = "Print the definition of \"%s\":";
    public static final String CORRECT_ANSWER = "Correct!";
    public static final String WRONG_ANSWER = "Wrong. The right answer is \"%s\".";
    public static final String WRONG_ANSWER_DEFINITION_EXISTS = "Wrong. The right answer is \"%s\", but your definition is correct for \"%s\".";

    public static final String CARDS_LOADED = "%d cards have been loaded.";
    public static final String FILE_NOT_FOUND = "File not found.";
    public static final String ERROR_READING_FILE = "Error reading file.";
    public static final String CARDS_SAVED = "%d cards have been saved.";
    public static final String ERROR_SAVING_FILE = "Error saving file.";
    public static final String ERROR_WRITING_FILE = "Error writing file.";
    public static final String LOG_SAVED = "The log has been saved.";
    public static final String ERROR_WRITING_LOG = "Error writing log file.";

    public static final String NO_ERRORS = "There are no cards with errors.";
    public static final String HARDEST_CARD = "The hardest card is \"%s\". You have %d errors answering it.";
    public static final String HARDEST_CARDS = "The hardest cards are %s. You have %d errors answering them.";
    public static final String STATS_RESET = "Card statistics have been reset.";

    private Messages() {
    }
}
