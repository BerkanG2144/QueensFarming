package farming.commands;

import farming.game.GameContext;

import java.util.Optional;

public class CommandParser {
    private final GameContext context;

    public CommandParser(GameContext context) {
        this.context = context;
    }

    public boolean handle(String input) {
        Optional<Command> cmd = parse(input);
        if (cmd.isPresent()) {
            cmd.get().execute();
            return true; // zähle erstmal jeden Command als Aktion
        }
        return false;
    }


    public Optional<Command> parse(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0) return Optional.empty();

        String keyword = parts[0].toLowerCase();

        switch (keyword) {
            case "show":
                if (parts.length == 2 && parts[1].equalsIgnoreCase("barn")) {
                    return Optional.of(new ShowBarnCommand(context));
                }
                // Weitere show-Befehle hier ergänzen
                System.err.println("Error, unknown show command");
                break;

            default:
                System.err.println("Error, unknown command");
        }

        return Optional.empty();
    }

}
