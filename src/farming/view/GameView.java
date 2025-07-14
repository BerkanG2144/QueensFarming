package farming.view;

import farming.game.Player;
import farming.model.BarnTile;
import farming.model.Board;
import farming.model.BoardFactory;


public interface GameView {
    void showBarn(BarnTile barn, int gold);
    void showBoard(Player player);
}
