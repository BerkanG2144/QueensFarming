package farming.commands;

import farming.game.Game;
import farming.game.Player;
import farming.model.BarnTile;
import farming.model.Position;
import farming.model.Tile;
import farming.model.VegetableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowBoardCommand implements Command {

    @Override
    public boolean execute(String[] args, Player player, Game game) {
        if (args.length == 2 && args[1].equals("board")) {
            showBoard(player);
            return false;
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
                Tile   tile = tiles.get(pos);
                String[] lines = formatTile(tile); // 3×5-Zeichen

                if (tile == null) {
                    // leere Zelle: immer genau 6 Leerzeichen
                    row1.append("      ");
                    row2.append("      ");
                    row3.append("      ");
                } else {
                    // linke Pipe + Inhalt
                    row1.append("|").append(lines[0]);
                    row2.append("|").append(lines[1]);
                    row3.append("|").append(lines[2]);

                    // wenn rechts KEINE weitere Tile ist, jetzt mit Pipe schließen
                    Position rightNeighbour = new Position(x + 1, y);
                    if (!tiles.containsKey(rightNeighbour)) {
                        row1.append("|");
                        row2.append("|");
                        row3.append("|");
                    }
                }
            }

            System.out.println(row1);
            System.out.println(row2);
            System.out.println(row3);
        }

    }

    private String[] formatTile(Tile tile) {
        if (tile == null) {
            return new String[]{"     ", "     ", "     "};
        }

        String abbr = tile.getAbbreviation();
        String cd = tile.getCountdown() >= 0 ? String.valueOf(tile.getCountdown()) : "*";

        if (tile instanceof BarnTile) {
            return formatBarnTile(abbr, cd);
        } else {
            String veg = tile.getPlantedType() != null ? getVegetableChar(tile.getPlantedType()) : " ";
            String cap = tile.getAmount() + "/" + tile.getCapacity();
            return formatRegularTile(abbr, cd, veg, cap);
        }
    }

    private String[] formatBarnTile(String abbr, String countdown) {
        String line2 = centerString(abbr + " " + countdown, 5);
        return new String[]{
                "     ",
                line2,
                "     "
        };
    }

    private String[] formatRegularTile(String abbr, String countdown, String veg, String cap) {
        return new String[]{
                centerString(abbr + " " + countdown, 5),
                centerString(veg, 5),
                centerString(cap, 5)
        };
    }

    private String centerString(String s, int width) {
        if (s.length() >= width) {
            return s.substring(0, width);
        }

        int padding = (width - s.length()) / 2;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            result.append(" ");
        }
        result.append(s);
        while (result.length() < width) {
            result.append(" ");
        }
        return result.toString();
    }

    private String getVegetableChar(VegetableType type) {
        return switch (type) {
            case CARROT -> "C";
            case SALAD -> "S";
            case TOMATO -> "T";
            case MUSHROOM -> "M";
            default -> " ";
        };
    }
}