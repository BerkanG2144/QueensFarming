package farming.commands;

import farming.game.Game;
import farming.game.Player;

public interface Command {
    /**
     * @return true if it counts as an action, false otherwise
     */
    boolean execute(String[] args, Player player, Game game);
}
