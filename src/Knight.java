public class Knight extends Piece {

    public Knight(Colour colour, Position currentPosition) {
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
        super.validateMove(newPosition, BOARD);

        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return rowDifference == 1 && columnDifference == 2 || rowDifference == 2 && columnDifference == 1;
    }

    @Override
    public String toString() {
        return "Knight{" + getColour() + "}";
    }
}
