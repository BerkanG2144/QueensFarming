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

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
