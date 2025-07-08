package farming.model;

public class Field extends Tile{

    public Field(Position position) {
        super(position, 4);
    }

    @Override
    public boolean canPlant(VegetableType type) {
        return type == VegetableType.CARROT
                || type == VegetableType.SALAD
                || type == VegetableType.TOMATO;
    }

    @Override
    public String getAbbreviation() {
        return "Fi";
    }
}
