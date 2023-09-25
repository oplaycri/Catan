package game;

public class Structure {
    public final String name;
    private int left;
    private int amount = 0;

    public Structure(String name, int left) {
        this.name = name;
        this.left = left;
        this.amount = 0;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
