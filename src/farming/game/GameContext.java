package farming.game;

import farming.model.Market;

public class GameContext {
    private final Player currentPlayer;
    private final Market market;

    public GameContext(Player currentPlayer, Market market) {
        this.currentPlayer = currentPlayer;
        this.market = market;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Market getMarket() {
        return market;
    }
}

