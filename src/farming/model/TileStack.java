package farming.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TileStack {
    private final List<Tile> stack;

    public TileStack(int playerCount, long seed) {
        this.stack = new ArrayList<>();
        for (int i = 0; i < 2 * playerCount; i++) stack.add(new Garden(null));
        for (int i = 0; i < 3 * playerCount; i++) stack.add(new Field(null));
        for (int i = 0; i < 2 * playerCount; i++) stack.add(new LargeField(null));
        for (int i = 0; i < 2 * playerCount; i++) stack.add(new Forest(null));
        for (int i = 0; i < 1 * playerCount; i++) stack.add(new LargeForest(null));

        Collections.shuffle(stack, new Random(seed));
    }

    public Tile draw() {
        return stack.remove(0);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}