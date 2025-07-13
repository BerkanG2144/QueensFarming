package farming.commands;

import farming.game.Player;
import farming.view.GameView;

public class ShowBarnCommand implements Command {
    private final Player player;
    private final GameView view;

    public ShowBarnCommand(Player player, GameView view) {
        this.player = player;
        this.view = view;
    }

    @Override
    public void execute() {
        view.showBarn(player.getBarn(), player.getGold());
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
