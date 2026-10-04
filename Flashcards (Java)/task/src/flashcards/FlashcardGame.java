package flashcards;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Game logic only: no console I/O. {@link Menu} is responsible for prompts and output.
 */
public class FlashcardGame {

    public record AnswerResult(boolean correct, Optional<FlashCard> cardWithGivenDefinition) {
    }

    private final Deck deck = new Deck();
    private final CardRepository cardRepository = new CardRepository();
    private final Random random = new Random();

    public boolean hasTerm(String term) {
        return deck.containsTerm(term);
    }

    public Deck.AddResult addCard(String term, String definition) {
        return deck.add(new FlashCard(term, definition));
    }

    public boolean removeCard(String term) {
        return deck.remove(term);
    }

    public List<FlashCard> getCards() {
        return deck.getCards();
    }

    public Optional<FlashCard> pickRandomCard(List<FlashCard> cards) {
        if (cards.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cards.get(random.nextInt(cards.size())));
    }

    public AnswerResult checkAnswer(FlashCard card, String answer) {
        if (card.getDefinition().equals(answer)) {
            return new AnswerResult(true, Optional.empty());
        }
        card.addMistake();
        return new AnswerResult(false, deck.getCardByDefinition(answer));
    }

    public int importCards(String filename) throws IOException {
        List<FlashCard> cards = cardRepository.loadFromFile(filename);
        deck.addOrReplace(cards);
        return cards.size();
    }

    public int exportCards(String filename) throws IOException {
        List<FlashCard> cards = deck.getCards();
        cardRepository.saveToFile(filename, cards);
        return cards.size();
    }

    public void saveLog(String filename, List<String> logMessages) throws IOException {
        cardRepository.saveLog(filename, logMessages);
    }

    /** Cards with the highest non-zero mistake count; empty if nobody has made a mistake. */
    public List<FlashCard> getHardestCards() {
        int maxMistakes = deck.getCards().stream().mapToInt(FlashCard::getMistakes).max().orElse(0);
        if (maxMistakes == 0) {
            return List.of();
        }
        return deck.getCards().stream().filter(card -> card.getMistakes() == maxMistakes).toList();
    }

    public void resetStats() {
        deck.resetStats();
    }
}
