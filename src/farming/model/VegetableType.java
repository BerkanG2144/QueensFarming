package farming.model;

public enum VegetableType {

    CARROT(1),
    SALAD(2),
    TOMATO(3),
    MUSHROOM(4);

    private final int growTime;

    VegetableType(int growTime) {
        this.growTime = growTime;
    }

    public int getGrowTime() {
        return growTime;
    }

    public String getVegetableChar(VegetableType type) {
        return switch (type) {
            case CARROT -> "C";
            case SALAD -> "S";
            case TOMATO -> "T";
            case MUSHROOM -> "M";
            default -> " ";
        };
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
