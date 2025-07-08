package farming.commands;

import farming.game.Game;
import farming.game.Player;
import farming.model.BarnTile;
import farming.model.Position;
import farming.model.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowBoardCommand implements Command {

    @Override
    public boolean execute(String[] args, Player player, Game game) {
        if (args.length == 2 && args[1].equals("board")) {
            showBoard(player);
            return false; // zählt nicht als Aktion
        }

        System.out.println("Error, invalid show command");
        return false;
    }

    private void showBoard(Player player) {
        List<Tile> tileList = new ArrayList<>(player.getBoard().getAllTiles());
        Map<Position, Tile> tiles = new HashMap<>();
        for (Tile tile : tileList) {
            tiles.put(tile.getPosition(), tile);
        }

        int xmin = tiles.keySet().stream().mapToInt(Position::getX).min().orElse(0);
        int xmax = tiles.keySet().stream().mapToInt(Position::getX).max().orElse(0);
        int ymin = tiles.keySet().stream().mapToInt(Position::getY).min().orElse(0);
        int ymax = tiles.keySet().stream().mapToInt(Position::getY).max().orElse(0);

        for (int y = ymax; y >= ymin; y--) {
            StringBuilder row1 = new StringBuilder();
            StringBuilder row2 = new StringBuilder();
            StringBuilder row3 = new StringBuilder();

            for (int x = xmin; x <= xmax; x++) {
                Position pos = new Position(x, y);
                Tile tile = tiles.get(pos);
                String[] lines = formatTile(tile);
                row1.append(lines[0]);
                row2.append(lines[1]);
                row3.append(lines[2]);
            }

            System.out.println(row1);
            System.out.println(row2);
            System.out.println(row3);
        }
    }

    private String[] formatTile(Tile tile) {
        String[] result = new String[3];

        if (tile == null) {
            result[0] = "      ";
            result[1] = "      ";
            result[2] = "      ";
        } else if (tile instanceof BarnTile) {
            String abbr = padRight(tile.getAbbreviation(), 2);
            String cd = tile.getCountdown() >= 0 ? String.valueOf(tile.getCountdown()) : "*";
            result[0] = "      ";
            result[1] = String.format("| %s %s |", abbr.trim(), cd);  // Trim to remove extra space
            result[2] = "      ";
        } else {
            String abbr = padRight(tile.getAbbreviation(), 2);
            String cd = tile.getCountdown() >= 0 ? String.valueOf(tile.getCountdown()) : " *";
            result[0] = String.format("| %s%s |", abbr.trim(), cd);

            if (tile.getPlantedType() != null) {
                String type = tile.getPlantedType().toString().toUpperCase().substring(0, 1);
                result[1] = String.format("|  %s  |", type);
            } else {
                result[1] = "|     |";
            }

            result[2] = String.format("|%d/%d |", tile.getAmount(), tile.getCapacity());
        }

        return result;
    }

    private String padRight(String s, int length) {
        return String.format("%-" + length + "s", s);
    }
}