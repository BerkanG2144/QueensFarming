package farming.view;

import farming.game.Player;
import farming.model.*;

import java.util.*;

public class ConsoleGameView implements GameView{
    @Override
    public void showBarn(BarnTile barn, int gold) {

        Map<VegetableType, Integer> stored = new EnumMap<>(VegetableType.class);
        for (VegetableType type : VegetableType.values()) {
            int count = barn.getAmount(type);
            if (count > 0) {
                stored.put(type, count);
            }
        }

        System.out.print("Barn");
        if (!stored.isEmpty()) {
            int spoil = barn.getSpoilCountdown();
            if (spoil > 0) {
                System.out.print(" (spoils in " + spoil + " turn" + (spoil != 1 ? "s" : "") + ")");
            }
        }
        System.out.println();

        if (!stored.isEmpty()) {
            List<Map.Entry<VegetableType, Integer>> list = new ArrayList<>(stored.entrySet());
            list.sort(Comparator
                    .comparing(Map.Entry<VegetableType, Integer>::getValue)
                    .thenComparing(e -> e.getKey().name()));

            int maxLabelLen = list.stream()
                    .map(e -> getPlural(e.getKey()).length())
                    .max(Integer::compareTo).orElse(0);

            int maxValueDigits = list.stream()
                    .map(e -> String.valueOf(e.getValue()).length())
                    .max(Integer::compareTo).orElse(0);

            int colonColumn = maxLabelLen + 1; // +1 für das ":"

            for (Map.Entry<VegetableType, Integer> entry : list) {
                String label = getPlural(entry.getKey());
                int spaces = colonColumn - label.length(); // Abstand bis `:`
                String pad = " ".repeat(spaces);
                String value = String.format("%" + maxValueDigits + "d", entry.getValue());
                System.out.println(label + pad + ":" + " " + value);
            }

            String line = "-".repeat(colonColumn + 1 + maxValueDigits); // ":" + " " + value
            System.out.println(line);

            int sum = list.stream().mapToInt(Map.Entry::getValue).sum();
            String sumPad = " ".repeat(colonColumn - "Sum".length());
            System.out.printf("Sum" + sumPad + ": %" + maxValueDigits + "d\n\n", sum);
        }

        System.out.printf("Gold: %6d\n", gold);


    }

    @Override
    public void showBoard(Player player) {
        List<Tile> tileList = new ArrayList<>(player.getBoard().getAllTiles());
        Map<Position, Tile> tiles = new HashMap<>();
        for (Tile tile : tileList) {
            tiles.put(tile.getPosition(), tile);
        }

        int xmin = tiles.keySet().stream().mapToInt(Position::getX).min().orElse(0);
        int xmax = tiles.keySet().stream().mapToInt(Position::getX).max().orElse(0);
        int ymin = tiles.keySet().stream().mapToInt(Position::getY).min().orElse(0);
        int ymax = tiles.keySet().stream().mapToInt(Position::getY).max().orElse(0);

        List<String> output = new ArrayList<>();
        output.add("Board");

        for (int y = ymax; y >= ymin; y--) {
            StringBuilder row1 = new StringBuilder();
            StringBuilder row2 = new StringBuilder();
            StringBuilder row3 = new StringBuilder();

            for (int x = xmin; x <= xmax; x++) {
                Position pos = new Position(x, y);
                Tile tile = tiles.get(pos);
                String[] lines = formatTile(tile);

                if (tile == null) {
                    row1.append("      ");
                    row2.append("      ");
                    row3.append("      ");
                } else {
                    row1.append("|").append(lines[0]);
                    row2.append("|").append(lines[1]);
                    row3.append("|").append(lines[2]);

                    Position rightNeighbour = new Position(x + 1, y);
                    if (!tiles.containsKey(rightNeighbour)) {
                        row1.append("|");
                        row2.append("|");
                        row3.append("|");
                    }
                }
            }

            output.add(row1.toString());
            output.add(row2.toString());
            output.add(row3.toString());
        }

        output.add(""); // Leere Zeile am Ende
    }

    private String[] formatTile(Tile tile) {
        if (tile == null) {
            return new String[]{"     ", "     ", "     "};
        }

        String abbr = tile.getAbbreviation(); // Annahme: getType() gibt TileType zurück
        String cd = tile.getCountdown() >= 0 ? String.valueOf(tile.getCountdown()) : "*";

        if (tile instanceof BarnTile barnTile) {
            return formatBarnTile(abbr, cd, barnTile);
        } else {
            String veg = tile.getPlantedType() != null ? VegetableType.getVegetableChar(tile.getPlantedType()) : " ";
            String cap = tile.getAmount() + "/" + tile.getCapacity();
            return formatRegularTile(abbr, cd, veg, cap);
        }
    }

    private String[] formatBarnTile(String abbr, String countdown, BarnTile barnTile) {
        String content = abbr + " " + countdown;
        String line2 = centerString(content, 5);
        return new String[]{"     ", line2, "     "};
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

    private String getPlural(VegetableType type) {
        return switch (type) {
            case CARROT -> "carrots";
            case MUSHROOM -> "mushrooms";
            case SALAD -> "salads";
            case TOMATO -> "tomatoes";
        };
    }

}
