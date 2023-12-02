package game;

public class Structure {
    public final String name;
    public final int INITIAL_LEFT;
    private int left;
    private int amount = 0;

    public Structure(String name, int INITIAL_LEFT) {
        this.name = name;
        this.INITIAL_LEFT = INITIAL_LEFT;
        left = this.INITIAL_LEFT;
        amount = 0;
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
