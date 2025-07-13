package farming.view;

import farming.game.Player;
import farming.model.BarnTile;
import farming.model.Board;
import farming.model.Market;

import java.util.List;

public interface GameView {
    void showBarn(BarnTile barn, int gold);
//    void showMarket(Market market);
//    void showBoard(Board board);
//    void showStartOfTurn(Player player, int grownVegetables, boolean barnSpoiled);
//    void showEndGame(List<Player> winners);
}
