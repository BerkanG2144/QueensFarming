package farming.commands;

import farming.game.Game;
import farming.game.Player;
import farming.model.BarnTile;
import farming.model.VegetableType;

import java.util.*;

public class ShowCommand implements Command{
    @Override
    public boolean execute(String[] args, Player player, Game game) {
        if (args.length != 2 || !args[1].equals("barn")) {
            System.out.println("Error, invalid show command");
            return false;
        }

        BarnTile barn = player.getBarn();

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


        System.out.printf("Gold: %6d\n", player.getGold());

        return false; // "show" zählt nicht als Aktion
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
