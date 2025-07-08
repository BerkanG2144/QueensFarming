package farming.model;

public class BoardFactory {

    public static Board createStarterBoard() {
        Board board = new Board();

        board.addTile(new BarnTile(new Position(0, 0)));
        board.addTile(new Garden(new Position(-1, 0)));
        board.addTile(new Garden(new Position(1, 0)));
        board.addTile(new Field(new Position(0, 1)));

        return board;
    }
}
