public class King extends Piece {

    public King(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void attack() {

    }

    @Override
    public void move(Position newPosition, Piece[][] BOARD) {
        super.move(newPosition, BOARD);
    }

    @Override
    public boolean validateMove(Position newPosition, Piece[][] BOARD) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return super.validateMove(newPosition, BOARD)&& (rowDifference == 1 || columnDifference == 1);
    }

    @Override
    public String toString() {
        return "King{" + getColour() + "}";
    }
}
