package farming.game;

import farming.model.Market;
import farming.view.GameView;

public class GameContext {
    private final Player currentPlayer;
    private final Market market;
    private final GameView view;

    public GameContext(Player currentPlayer, Market market, GameView view) {
        this.currentPlayer = currentPlayer;
        this.market = market;
        this.view = view;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Market getMarket() {
        return market;
    }

    public GameView getView() {
        return view;
    }
}

