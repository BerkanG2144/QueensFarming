package farming.test;

import farming.game.Game;

public class QueensFarming {
    public static void main(String[] args) {
        Game game = new Game();      // deine zentrale Steuerklasse
        game.initializeGame();       // Eingabe (Spielerzahl, Namen, Seed, etc.)
        game.run();                  // Game-Loop mit Spieleraktionen
    }
}

