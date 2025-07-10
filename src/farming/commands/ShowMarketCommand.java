package farming.commands;

import farming.game.Game;
import farming.game.Player;

public class ShowMarketCommand implements Command {

    @Override
    public boolean execute(String[] args, Player player, Game game) {
        if (args.length == 2 && args[1].equals("market")) {
            game.showMarket();
        } else {
            System.out.println("Error, invalid show command");
        }
        return false; // zählt nicht als Aktion
    }
}
