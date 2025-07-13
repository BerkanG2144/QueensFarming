package farming.commands;

import farming.game.Game;
import farming.game.Player;
import farming.view.GameView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class CommandParser {
    private final Player player;
    private final GameView view;
    private final Game game;
    private final Map<String, Function<String[], Optional<Command>>> registry;

    public CommandParser(Player player, GameView view, Game game) {
        this.player = player;
        this.view = view;
        this.game = game;
        this.registry = new HashMap<>();

        // register commands
        registry.put("show", parts -> {
            if (parts.length == 2 && parts[1].equalsIgnoreCase("barn")) {
                return Optional.of(new ShowBarnCommand(player, view));
            }
            System.err.println("Error, unknown show command");
            return Optional.empty();
        });
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

        Function<String[], Optional<Command>> factory = registry.get(keyword);
        if (factory != null) {
            return factory.apply(parts);
        }

        System.err.println("Error, unknown command");
        return Optional.empty();
    }

}
