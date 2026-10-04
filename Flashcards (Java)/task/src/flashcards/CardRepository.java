package flashcards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    public List<FlashCard> loadFromFile(String filename) throws IOException {
        List<FlashCard> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String term;
            while ((term = reader.readLine()) != null) {
                String definition = reader.readLine();
                String mistakes = reader.readLine();
                if (definition == null || mistakes == null) {
                    throw new IOException("Incomplete card record in " + filename);
                }
                try {
                    cards.add(new FlashCard(term, definition, Integer.parseInt(mistakes.trim())));
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid mistakes count in " + filename, e);
                }
            }
        }
        return cards;
    }

    public void saveToFile(String filename, List<FlashCard> cards) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (FlashCard card : cards) {
                writer.write(card.getTerm());
                writer.newLine();
                writer.write(card.getDefinition());
                writer.newLine();
                writer.write(Integer.toString(card.getMistakes()));
                writer.newLine();
            }
        }
    }

    public void saveLog(String filename, List<String> logMessages) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String message : logMessages) {
                writer.write(message);
                writer.newLine();
            }
        }
    }
}
