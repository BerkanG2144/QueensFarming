package farming.model;

import java.util.Collections;
import java.util.List;

public class Market {
    private final int[][] priceTable;
    private int diamondRow; //Mushroom and Carrot
    private int starRow; //Mushroom and Carrot

    public Market() {
        this.priceTable = new int[][] {
                {12, 3, 3, 6},
                {15, 2, 5, 5},
                {16, 2, 6, 4}, // Start position für ⋄ und ★
                {17, 2, 7, 3},
                {20, 1, 9, 2}
        };
        this.diamondRow = 2;
        this.starRow = 2;
    }

    public int getPrice(VegetableType vegetableType) {
        switch (vegetableType) {
            case MUSHROOM -> {
                return priceTable[diamondRow][0];
            }
            case CARROT -> {
                return priceTable[diamondRow][1];
            }
            case TOMATO -> {
                return priceTable[starRow][2];
            }
            case SALAD -> {
                return priceTable[starRow][3];
            }
            default -> throw new IllegalArgumentException("Error, unknown type");
        }
    }

    public void updateMarket(List<VegetableType> sold) {
        int mushrooms = Collections.frequency(sold, VegetableType.MUSHROOM);
        int carrots = Collections.frequency(sold, VegetableType.CARROT);
        int tomatoes = Collections.frequency(sold, VegetableType.TOMATO);
        int salads = Collections.frequency(sold, VegetableType.SALAD);

        int pair = Math.min(mushrooms, carrots);
        mushrooms -= pair;
        carrots -= pair;

        pair = Math.min(tomatoes, salads);
        tomatoes -= pair;
        salads -= pair;

        diamondRow = Math.max(0, Math.min(4, diamondRow - (mushrooms / 2) + (carrots / 2)));
        starRow    = Math.max(0, Math.min(4, starRow - (tomatoes / 2) + (salads / 2)));
    }


    public void showMarket() {
        System.out.printf("mushrooms: %2d\n", priceTable[diamondRow][0]);
        System.out.printf("carrots:   %2d\n", priceTable[diamondRow][1]);
        System.out.printf("tomatoes:  %2d\n", priceTable[starRow][2]);
        System.out.printf("salads:    %2d\n", priceTable[starRow][3]);

    }

}
