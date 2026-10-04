package flashcards;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Deck {
    private final Map<String, FlashCard> termToCardMap;
    private final Map<String, FlashCard> definitionToCardMap;

    public Deck() {
        this.definitionToCardMap = new HashMap<>();
        this.termToCardMap = new LinkedHashMap<>();
    }

    public AddResult add(FlashCard card) {
        if (containsTerm(card.getTerm())) {
            return AddResult.TERM_EXISTS;
        }
        if (containsDefinition(card.getDefinition())) {
            return AddResult.DEFINITION_EXISTS;
        }
        store(card);
        return AddResult.ADDED;
    }

    public void addOrReplace(FlashCard card) {
        if (containsTerm(card.getTerm())) {
            definitionToCardMap.remove(termToCardMap.get(card.getTerm()).getDefinition());
        }
        if (containsDefinition(card.getDefinition())) {
            termToCardMap.remove(definitionToCardMap.get(card.getDefinition()).getTerm());
        }
        store(card);
    }

    public void addOrReplace(List<FlashCard> cards) {
        for (FlashCard card : cards) {
            addOrReplace(card);
        }
    }

    private void store(FlashCard card) {
        termToCardMap.put(card.getTerm(), card);
        definitionToCardMap.put(card.getDefinition(), card);
    }

    public boolean remove(String term) {
        FlashCard removed = termToCardMap.remove(term);
        if (removed == null) {
            return false;
        }
        definitionToCardMap.remove(removed.getDefinition());
        return true;
    }

    public void resetStats() {
        termToCardMap.values().forEach(FlashCard::resetStats);
    }

    public boolean containsTerm(String term) {
        return termToCardMap.containsKey(term);
    }

    public boolean containsDefinition(String definition) {
        return definitionToCardMap.containsKey(definition);
    }

    public Optional<FlashCard> getCardByDefinition(String definition) {
        return Optional.ofNullable(definitionToCardMap.get(definition));
    }

    public List<FlashCard> getCards() {
        return List.copyOf(termToCardMap.values());
    }

    public enum AddResult {
        ADDED,
        TERM_EXISTS,
        DEFINITION_EXISTS
    }
}
