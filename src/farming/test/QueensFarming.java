package farming.test;

import farming.game.Game;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class QueensFarming {
    public static void main(String[] args) {
        String input = String.join("\n",
                "3",            // Spieleranzahl
                "Mira",         // Spieler 1
                "Milan",        // Spieler 2
                "Vincent",      // Spieler 3
                "42",           // Startgold
                "50",           // Siegbedingung
                "6",            // Seed
                "show barn",    // Mira's Aktion
                "show board",   // Mira's Aktion
                "show market"
        );

        // Simulierte Eingabe vorbereiten
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        System.setIn(in);

        Game game = new Game();
        game.initializeGame();  // liest jetzt aus dem simulierten Input
        game.run();             // Spiel startet automatisch mit Mira's Eingaben
    }
}
