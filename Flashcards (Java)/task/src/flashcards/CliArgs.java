package flashcards;

import java.util.Optional;

public record CliArgs(Optional<String> importFilename, Optional<String> exportFilename) {

    public static CliArgs parse(String[] args) {
        return new CliArgs(valueOf(args, "-import"), valueOf(args, "-export"));
    }

    private static Optional<String> valueOf(String[] args, String key) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) {
                return Optional.of(args[i + 1]);
            }
        }
        return Optional.empty();
    }
}
