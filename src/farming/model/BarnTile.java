package farming.model;

import java.util.EnumMap;
import java.util.Map;

public class BarnTile extends Tile {
    private Map<VegetableType, Integer> storage;
    private int spoilCountdown;

    public BarnTile(Position position) {
        super(position, Integer.MAX_VALUE);
        this.storage = new EnumMap<>(VegetableType.class);
        this.spoilCountdown = -1;
    }

    /** Store in Barn
     * if empty = (0) add the quantity
     * if no countdown active and there IS something in Barn => start countdown
     */
    public void store(VegetableType type, int quantity) {
        storage.put(type, storage.getOrDefault(type, 0) + quantity);
        if (spoilCountdown == -1 && !storage.isEmpty()) {
            spoilCountdown = 6;
        }
    }

    public int getAmount(VegetableType type) {
        return storage.getOrDefault(type, 0);
    }

    public Map<VegetableType, Integer> getAllVegetables() {
        //return storage; kann von außen geändert werden mit clear deswegen kopie erstellen
        return new EnumMap<>(storage);
    }

    public int getSpoilCountdown() {
        return spoilCountdown;
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    public void resetSpoilCountdown() {
        spoilCountdown = -1;
    }

    public void spoil() {
        if (spoilCountdown > 0) {
            spoilCountdown--;
            if (spoilCountdown == 0) {
                storage.clear();
                spoilCountdown = -1;
            }
        }
    }

    public void remove(VegetableType type, int quantity) {
        int current = storage.getOrDefault(type, 0);
        if (quantity > current) {
            throw new IllegalArgumentException("Not enoug " +  type);
        }
        if (quantity == current) {
            storage.remove(type);
        } else {
            storage.put(type, current - quantity);
        }

    }

    @Override
    public boolean canPlant(VegetableType type) {
        return false;
    }

    @Override
    public String getAbbreviation() {
        return "B";
    }

}
