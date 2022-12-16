public class Box {
    private boolean mine;
    private boolean revealed;
    private int minesAround;

    public Box(boolean mine) {
        this.mine = mine;
    }

    public boolean getMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean getRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }
}
