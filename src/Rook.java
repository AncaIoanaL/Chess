public class Rook extends Piece {

    public Rook(Colour colour, Position currentPosition) {
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

        return newPosition.getRow() == getCurrentPosition().getRow() || newPosition.getColumn() == getCurrentPosition().getColumn();
    }

    @Override
    public String toString() {
        return "Rook{" + getColour() + "}";
    }
}
