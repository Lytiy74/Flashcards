package flashcards;

import java.util.Objects;

public class FlashCard {
    private final String term;
    private final String definition;
    private int mistakes;

    public FlashCard(String term, String definition) {
        this(term, definition, 0);
    }

    public FlashCard(String term, String definition, int mistakes) {
        this.term = term;
        this.definition = definition;
        this.mistakes = mistakes;
    }

    public String getDefinition() {
        return definition;
    }

    public String getTerm() {
        return term;
    }

    public void addMistake() {
        mistakes++;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void resetStats() {
        mistakes = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FlashCard flashCard)) return false;
        return Objects.equals(term, flashCard.term) && Objects.equals(definition, flashCard.definition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(term, definition);
    }

    @Override
    public String toString() {
        return "FlashCard{" +
                "definition='" + definition + '\'' +
                ", term='" + term + '\'' +
                ", mistakes=" + mistakes +
                '}';
    }
}
