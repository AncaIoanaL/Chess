public class Queen extends Piece {

    public Queen(Colour colour, Position currentPosition) {
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

        return super.validateMove(newPosition, BOARD) && ((rowDifference == columnDifference) || (newPosition.getRow() == getCurrentPosition().getRow() || newPosition.getColumn() == getCurrentPosition().getColumn()));

    }

    @Override
    public String toString() {
        return "Queen{" + getColour() + "}";
    }
}
