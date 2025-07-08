package farming.commands;

import farming.game.Game;
import farming.game.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandParser() {
        commands.put("show", new ShowCommand()); // Basiskommandogruppe
        // Weitere Befehle folgen hier, z. B.:
        // commands.put("sell", new SellCommand());
        // commands.put("plant", new PlantCommand());
    }

    public boolean handle(String input, Player player, Game game) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0) return false;

        String keyword = parts[0].toLowerCase();

        if (keyword.equals("show")) {
            if (parts.length < 2) {
                System.out.println("Error, missing show target");
                return false;
            }

            return switch (parts[1]) {
                case "barn" -> new ShowCommand().execute(parts, player, game);
                case "board" -> new ShowBoardCommand().execute(parts, player, game);
                case "market" -> {
                    System.out.println("Error, show market not implemented");
                    yield false;
                }
                default -> {
                    System.out.println("Error, invalid show target");
                    yield false;
                }
            };
        }

        Command command = commands.get(keyword);
        if (command == null) {
            System.out.println("Error, unknown command");
            return false;
        }

        return command.execute(parts, player, game);
    }
}
