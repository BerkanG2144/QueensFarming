package farming.model;

public abstract class Tile {
    protected Position position;
    protected int capacity;
    protected int amount;
    protected int countdown;
    protected VegetableType planted;

    public Tile(Position position, int capacity) {
        this.position = position;
        this.capacity = capacity;
        this.amount = 0;
        this.countdown = -1; //Kachel inaktiv bzw schläft gerade
        this.planted = null;
    }

    public abstract boolean canPlant(VegetableType type);

    public abstract String getAbbreviation();

    public boolean isEmpty() {
        return amount == 0;
    }

    public boolean isFull() {
        return amount >= capacity;
    }

    public VegetableType getPlantedType() {
        return planted;
    }

    public int getAmount() {
        return amount;
    }

    public int getCountdown() {
        return countdown;
    }

    public Position getPosition() {
        return position;
    }

    public int getCapacity() {
        return capacity;
    }


    public void setPosition(Position pos) {
        this.position = pos;
    }


    public void plant(VegetableType type) {
        this.planted = type;
        this.amount = 1;
        this.countdown = type.getGrowTime();
    }

    public void grow() {
        if (countdown > 0) {
            countdown--;
            if (countdown == 0) {
                amount = Math.min(capacity, amount * 2);
                if (amount < capacity) {
                    //wenn noch platz ist neuer Wachstumszyklus
                    countdown = planted.getGrowTime();
                } else {
                    //Kachel voll cap > amount dann countdown deaktivieren
                    countdown = -1;
                }
            }
        }
    }

    public void harvest(int quantity) {
        if (quantity > amount) {
            throw new IllegalArgumentException("Error, harvesting more than possible");
        }
        amount -= quantity;
        if (amount == 0) {
            countdown = -1;
            planted = null;
        }
    }

}
