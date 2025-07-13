package farming.game;

import farming.commands.CommandParser;
import farming.model.*;
import farming.view.ConsoleGameView;
import farming.view.GameView;

// Provides context objects for command execution

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Player> players;
    private Market market;
    private TileStack tileStack;
    private int winGold;
    private int currentPlayerIndex;
    private Scanner scanner;
    private boolean gameOver;
    private int actionsThisTurn;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.players = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.gameOver = false;
    }


    public void showMarket() {
        market.showMarket();
    }

    public void initializeGame() {
        System.out.println("How many players?");
        int numPlayers = 0;
        while (true) {
            try {
                numPlayers = Integer.parseInt(scanner.nextLine().trim());
                if (numPlayers > 0) break;
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Error, invalid player count");
        }

        for (int i = 0; i < numPlayers; i++) {
            while (true) {
                System.out.println("Enter the name of player " + (i + 1) + ":");
                String name = scanner.nextLine().trim();
                if (name.matches("[A-Za-z]+")) {
                    Player p = new Player(name);
                    players.add(p);
                    break;
                } else {
                    System.out.println("Error, invalid name");
                }
            }
        }

        int startGold = 0;
        System.out.println("With how much gold should each player start");
        while (true) {
            try {
                startGold = Integer.parseInt(scanner.nextLine().trim());
                if (startGold >= 0) {
                    break;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Error, invalid gold amount");
        }

        System.out.println("With how much gold should a player win?");
        while (true) {
            try {
                winGold = Integer.parseInt(scanner.nextLine().trim());
                if (winGold >= 1) {
                    break;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Error, invalid win amount");
        }

        System.out.println("Please enter the seed used to shuffle the tiles:");
        int seed = 0;
        while (true) {
            try {
                seed = Integer.parseInt(scanner.nextLine().trim());
                break; // jeder int erlaubt
            } catch (NumberFormatException ignored) {
                System.out.println("Error, invalid seed");
            }
        }

        tileStack = new TileStack(players.size(), seed);
        market = new Market();

        for (Player p : players) {
            p.setGold(startGold);

            BarnTile barn = p.getBarn();
            for (VegetableType type : VegetableType.values()) {
                barn.store(type, 1);
            }
        }

        System.out.println();
        System.out.println("It is " + players.get(currentPlayerIndex).getName() + "'s turn!");

    }

    public void run() {
        GameView view = new ConsoleGameView();

        while (!gameOver) {
            Player currentPlayer = players.get(currentPlayerIndex);
            actionsThisTurn = 0;

            CommandParser parser = new CommandParser(currentPlayer, view);

            while (actionsThisTurn < 2) {
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("quit")) {
                    gameOver = true;
                    return;
                }

                if (input.equalsIgnoreCase("end turn")) {
                    break;
                }

                boolean counted = parser.handle(input);
                if (counted) actionsThisTurn++;
            }

            endTurn();
        }
    }

    public void endTurn() {
        currentPlayerIndex++;

        // Neue Runde starten, wenn alle dran waren
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;

            // Gemüse wachsen lassen & Scheunen verwalten
            for (Player player : players) {
                player.updateTiles();
                player.updateBarn();
            }

            // Siegbedingung prüfen
            List<Player> winners = new ArrayList<>();
            for (Player p : players) {
                if (p.getGold() >= winGold) {
                    winners.add(p);
                }
            }

            if (!winners.isEmpty()) {
                // Vermögen aller Spieler ausgeben
                for (int i = 0; i < players.size(); i++) {
                    Player p = players.get(i);
                    System.out.println("Player " + (i + 1) + " (" + p.getName() + "): " + p.getGold());
                }

                // Gewinnernachricht
                if (winners.size() == 1) {
                    System.out.println(winners.get(0).getName() + " has won!");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < winners.size(); i++) {
                        sb.append(winners.get(i).getName());
                        if (i == winners.size() - 2) {
                            sb.append(" and ");
                        } else if (i < winners.size() - 2) {
                            sb.append(", ");
                        }
                    }
                    sb.append(" have won!");
                    System.out.println(sb.toString());
                }

                gameOver = true;
                return;
            }
        }

        //nächster spielzug
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println();
        System.out.println("It is " + currentPlayer.getName() + "'s turn!");

        int grown = currentPlayer.getAndResetGrownVegetables();
        if (grown == 1) {
            System.out.println("1 vegetable has grown since your last turn.");
        } else if (grown > 1) {
            System.out.println(grown + " vegetables have grown since your last turn.");
        }

        if (currentPlayer.barnIsSpoiled()) {
            System.out.println("The vegetables in your barn are spoiled.");
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

}
