package farming.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Tile> tiles;

    public Board() {
        this.tiles = new HashMap<>();
    }

    public void addTile(Tile tile) {
        tiles.put(tile.getPosition(), tile);
    }

    public Tile getTileAt(Position pos) {
        return tiles.get(pos);
    }

    public boolean hasTileAt(Position pos) {
        return tiles.containsKey(pos);
    }

    public Collection<Tile> getAllTiles() {
        return tiles.values();
    }

    public void removeTileAt(Position pos) {
        tiles.remove(pos);
    }

}

