package farming.test;

import farming.game.Game;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;


public class QueensFarming {
    public static void main(String[] args) {
        Game game = new Game();
        game.initializeGame();
        game.run();
    }
}

