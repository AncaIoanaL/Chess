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
        super.validateMove(newPosition, BOARD);

        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        if((rowDifference == columnDifference) || (newPosition.getRow() == getCurrentPosition().getRow() || newPosition.getColumn() == getCurrentPosition().getColumn())) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "Queen{" + getColour() + "}";
    }
}
