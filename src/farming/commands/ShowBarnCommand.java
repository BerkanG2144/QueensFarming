package farming.commands;

import farming.game.GameContext;
import farming.game.Player;
import farming.view.GameView;

public class ShowBarnCommand implements Command {
    private final GameContext context;

    public ShowBarnCommand(GameContext context) {
        this.context = context;
    }

    @Override
    public void execute() {
        Player player = context.getCurrentPlayer();
        GameView view = context.getView();
        view.showBarn(player.getBarn(), player.getGold());
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
