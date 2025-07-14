package farming.view;

import farming.game.Player;
import farming.model.BarnTile;
import farming.model.Board;
import farming.model.Tile;
import farming.model.VegetableType;

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
       Board board  = player.getBoard();
       List<Tile> tiles = (List<Tile>) board.getAllTiles();

        int minX = tiles.stream().mapToInt(t -> t.getPosition().getX()).min().orElse(0);
        int maxX = tiles.stream().mapToInt(t -> t.getPosition().getX()).max().orElse(0);
        int minY = tiles.stream().mapToInt(t -> t.getPosition().getY()).min().orElse(0);
        int maxY = tiles.stream().mapToInt(t -> t.getPosition().getY()).max().orElse(0);

        String[][] display = new String[maxY - minY + 1][maxX - minX + 1];
        for (String [] row : display) {
            Arrays.fill(row, " ");
        }

        for (Tile tile : tiles) {
            int x = tile.getPosition().getX() - minX;
            int y = tile.getPosition().getY() - minY;
            String symbol;
            if (tile instanceof BarnTile) {
                symbol = "B ";
            } else {
                VegetableType planted = tile.getPlantedType();
                symbol = planted != null ? planted.getVegetableChar(planted) + tile.getCountdown() : tile.getPlantedType().getVegetableChar(planted) + " ";
            }
            display[y][x] = symbol;
        }

        // Ausgabe der Matrix
        System.out.println("Board");
        for (String[] row : display) {
            System.out.println(String.join(" ", row));
        }
        System.out.println();

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
