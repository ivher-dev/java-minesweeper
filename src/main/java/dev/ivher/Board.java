import java.util.HashSet;

public class Board {
    private Box[][] boxes;

    public Board(int size, int mines) {
        this.boxes = new Box[size][size];

        HashSet<int[]> minesLocations = this.generateMines(mines);

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                boolean mine = false;
                for (int[] minesLocation : minesLocations) {
                    if (minesLocation[0] == i && minesLocation[1] == j) {
                        mine = true;
                        break;
                    }
                }
                this.boxes[i][j] = new Box(mine);
            }
        }

        this.setMinesAround(minesLocations);
    }

    private HashSet<int[]> generateMines(int mines) {
        HashSet<int[]> minesLocations = new HashSet<int[]>();
        int minesSet = 0;
        while (minesSet < mines) {
            int x = (int) (Math.random() * this.boxes[0].length);
            int y = (int) (Math.random() * this.boxes.length);
            if (minesLocations.add(new int[] {y, x})) {
                minesSet++;
            }
        }
        return minesLocations;
    }

    private void setMinesAround(HashSet<int[]> minesLocations){
        for (int[] minesLocation : minesLocations) {
            int x = minesLocation[1];
            int y = minesLocation[0];
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x + i >= 0 && x + i < boxes[0].length && y + j >= 0 && y + j < boxes.length) {
                        this.boxes[y + j][x + i].setMinesAround(this.boxes[y + j][x + i].getMinesAround() + 1);
                    }
                }
            }
        }
    }

    private boolean revealBox(int x, int y){
        if (this.boxes[y][x].getMine()) {
            return false;
        } else {
            this.boxes[y][x].setRevealed(true);
            if (this.boxes[y][x].getMinesAround() == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (x + i >= 0 && x + i < boxes[0].length && y + j >= 0 && y + j < boxes.length) {
                            if (!this.boxes[y + j][x + i].getRevealed()) {
                                this.revealBox(x + i, y + j);
                            }
                        }
                    }
                }
            } 
            return true;
        }
    }

    private boolean checkWin(){
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++) {
                if (!this.boxes[i][j].getMine() && !this.boxes[i][j].getRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean play(){
        this.show(false);
        int x, y;
        while (true) {
            while (true){
                try {
                    System.out.print("Enter the coordinate X of the box you want to reveal: ");
                    x = Integer.parseInt(System.console().readLine());
                    if (x < 0 || x >= this.boxes[0].length) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input! Introduce a valid number [0-"+this.boxes[0].length+"]!\n");
                }
            }
            while (true){
                try {
                    System.out.print("Enter the coordinate Y of the box you want to reveal: ");
                    y = Integer.parseInt(System.console().readLine());
                    if (y < 0 || y >= this.boxes.length) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input! Introduce a valid number [0-"+this.boxes.length+"]!\n");
                }
            }
            System.out.println();
            if (!this.revealBox(x, y)) {
                this.show(true);
                System.out.println("You stepped on a mine! Game over!\n");
                return false;
            } else {
                this.show(false);
                if (this.checkWin()) {
                    System.out.println("You won!\n");
                    return true;
                }
            }
        }
    }

    private void show(boolean showHidden){
        // Print X index
        System.out.print("    ");
        for (int i = 0; i < boxes.length; i++) {
            System.out.print(i + " ");
        }
        System.out.print("\n    ");
        for (int i = 0; i < boxes.length; i++) {
            System.out.print("-" + " ");
        }
        System.out.println();
        for (int i = 0; i < boxes.length; i++) {
            // Print Y index
            System.out.print(i + " | ");
            // Print boxes
            for (int j = 0; j < boxes.length; j++) {
                if (this.boxes[i][j].getRevealed() || showHidden) {
                    System.out.print((boxes[i][j].getMine() ? "*" : boxes[i][j].getMinesAround()) + " ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
