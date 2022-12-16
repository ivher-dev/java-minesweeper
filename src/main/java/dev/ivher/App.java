public class App{
    public static void main(String[] args) {
        Board board = new Board(10, 10);
        showWelcomeMessage();
        board.play();
    }

    private static void showWelcomeMessage() {
        System.out.println("#################################\n" +
                            "#### Welcome to Minesweeper! ####\n" +
                            "#################################\n");
    }
}