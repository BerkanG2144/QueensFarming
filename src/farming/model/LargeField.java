package farming.model;

public class LargeField extends Tile{

    public LargeField(Position position) {
        super(position, 8);
    }

    @Override
    public boolean canPlant(VegetableType type) {
        return type == VegetableType.CARROT
                || type == VegetableType.SALAD
                || type == VegetableType.TOMATO;
    }

    @Override
    public String getAbbreviation() {
        return "LFi";
    }
}
