package farming.commands;

import farming.game.Game;
import farming.game.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandParser {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandParser() {
        // register all commands here
        commands.put("show barn", new ShowCommand());
        commands.put("show board", new ShowBoardCommand());
        commands.put("show market", new ShowMarketCommand());
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
            String key2 = keyword + " " + parts[1].toLowerCase();
            Command command = commands.get(key2);
            if (command == null) {
                System.out.println("Error, invalid show target");
                return false;
            }
            return command.execute(parts, player, game);
        }

        Command command = commands.get(keyword);
        if (command == null) {
            System.out.println("Error, unknown command");
            return false;
        }

        return command.execute(parts, player, game);
    }
}
