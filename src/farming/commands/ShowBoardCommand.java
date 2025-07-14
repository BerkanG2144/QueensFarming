package farming.commands;

import farming.game.Player;
import farming.view.GameView;

public class ShowBoardCommand implements Command{
    private final Player player;
    private final GameView view;

    public ShowBoardCommand(Player player, GameView view) {
        this.player = player;
        this.view = view;
    }


    @Override
    public void execute() {
        view.showBoard(player.getBoard());
    }

    @Override
    public boolean isValid() {
        return false;
    }
}
