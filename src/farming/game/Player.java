package farming.game;

import farming.model.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final String name;
    private final Board board;
    private final BarnTile barn;
    private int gold;
    private int grownSinceLastTurn = 0;
    private boolean barnSpoiled = false;

    public Player(String name) {
        this.name = name;
        this.gold = 0;
        this.board = BoardFactory.createStarterBoard();
        this.barn = (BarnTile) board.getTileAt(new Position(0, 0));
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean barnIsSpoiled() {
        return barnSpoiled;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public void subtractGold(int amount) {
        if (amount > gold) throw new IllegalArgumentException("Not enough gold!");
        gold -= amount;
    }

    public Board getBoard() {
        return board;
    }

    public BarnTile getBarn() {
        return barn;
    }


    public int getAndResetGrownVegetables() {
        int result = grownSinceLastTurn;
        grownSinceLastTurn = 0;
        return result;
    }

    public void updateTiles() {
        for (Tile tile : board.getAllTiles()) {
            int before = tile.getAmount();
            tile.grow();
            int after = tile.getAmount();
            grownSinceLastTurn += (after - before);
        }
    }

    public void updateBarn() {
        BarnTile barn = this.getBarn();

        if (barn.isEmpty()) {
            barn.resetSpoilCountdown();
            return;
        } else {
            int before = barn.getSpoilCountdown();
            barn.spoil();
            barnSpoiled = (before > 0 && barn.getSpoilCountdown() == -1 && barn.isEmpty());
        }


    }

    public void plant(Position position, VegetableType type) {
        Tile tile = board.getTileAt(position);

        if (tile == null) {
            System.out.println("Error, no tile at that position");
            return;
        }

        if (tile.getPlantedType() != null) {
            System.out.println("Error, tile already planted");
            return;
        }

        if (!tile.canPlant(type)) {
            System.out.println("Error, cannot plant " + type.toString().toLowerCase());
            return;
        }

        if (barn.getAmount(type) < 1) {
            System.out.println("Error, not enough ");
            return;
        }

        if (tile.isFull()) {
            System.err.println("Error, tile is full");
            return;
        }

        tile.plant(type);
        barn.remove(type, 1);
    }

    public boolean harvest(Position position, int amount) {
        Tile tile = board.getTileAt(position);

        if (tile == null) {
            System.out.println("Error, no tile at " + position);
            return false;
        }

        VegetableType type = tile.getPlantedType();
        if (type == null || tile.isEmpty()) {
            System.out.println("Error, nothing to harvest");
            return false;
        }

        if (amount <= 0) {
            System.out.println("Error, invalid amount");
            return false;
        }

        if (amount > tile.getAmount()) {
            System.out.println("Error, not enough to harvest");
            return false;
        }

        tile.harvest(amount);
        barn.store(type, amount);
        return true;
    }

    public int sell(List<VegetableType> vegetables, Market market) {
        if (vegetables == null || vegetables.isEmpty()) {
            System.out.println("Error, no vegetables");
            return 0;
        }

        int totalGold = 0;

        //Prüfen ob alles vorhanden und Grupppieren
        Map<VegetableType, Integer> grouped = new EnumMap<>(VegetableType.class);
        for (VegetableType v : vegetables) {
            grouped.put(v, grouped.getOrDefault(v, 0) + 1);
        }

        for (Map.Entry<VegetableType, Integer> entry : grouped.entrySet()) {
            int available = barn.getAmount(entry.getKey());
            if (available < entry.getValue()) {
                System.err.println("Error, not enough " + entry.getKey().toString().toLowerCase());
                return 0;
            }
        }

        for (Map.Entry<VegetableType, Integer> entry : grouped.entrySet()) {
            VegetableType type = entry.getKey();
            int amount = entry.getValue();
            int price = market.getPrice(type);
            totalGold += price * amount;
            barn.remove(type, amount);
        }

        addGold(totalGold);
        market.updateMarket(vegetables);
        return totalGold;

    }

    public boolean buyVegetable(VegetableType type, Market market) {
        if (type == null) {
            System.out.println("Error, invalid vegetable");
            return false;
        }

        int price = market.getPrice(type);

        if (gold < price) {
            System.out.println("Error, not enough Gold");
            return false;
        }

        subtractGold(price);
        barn.store(type, 1);
        return true;
    }

    public boolean buyLand(Position position, Tile tile, Market market) {
        if (board.hasTileAt(position)) {
            System.out.println("Error, tile already exists at" + position);
            return false;
        }

        boolean adjacent = board.getAllTiles().stream().map(Tile::getPosition)
                .anyMatch(p -> p.manhattanDistance(position) == 1);

        if (!adjacent) {
            System.out.println("Error, place not adjacent");
            return false;
        }

        int distance = new Position(0, 0).manhattanDistance(position);
        int price = 10 * (distance - 1);

        if (gold < price) {
            System.out.println("Error, not enough Gold");
            return false;
        }

        tile.setPosition(position);
        board.addTile(tile);
        subtractGold(price);
        return true;
    }

}
