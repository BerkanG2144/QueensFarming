package farming.model;

public class Garden extends Tile {

    public Garden(Position position) {
        super(position, 2);
    }

    @Override
    public boolean canPlant(VegetableType type) {
        return true; //Erlaubt alle arten von Gemüse
    }

    @Override
    public String getAbbreviation() {
        return "G";
    }
}
