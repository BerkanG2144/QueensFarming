package farming.model;

public class Forest extends Tile{

    public Forest(Position position) {
        super(position, 4);
    }

    @Override
    public boolean canPlant(VegetableType type) {
        return type == VegetableType.CARROT
                || type == VegetableType.MUSHROOM;
    }

    @Override
    public String getAbbreviation() {
        return "Fo";
    }
}
