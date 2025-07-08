package farming.model;

public class LargeForest extends Tile{

    public LargeForest(Position position) {
        super(position, 8);
    }

    @Override
    public boolean canPlant(VegetableType type) {
        return type == VegetableType.CARROT
                || type == VegetableType.MUSHROOM;
    }

    @Override
    public String getAbbreviation() {
        return "LFo";
    }
}
