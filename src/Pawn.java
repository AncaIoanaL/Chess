public class Pawn extends Piece {

    private final Position initialPosition;

    public Pawn(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
        initialPosition = currentPosition;
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean validateMove(Position newPosition, Piece[][] BOARD) {
        if (BOARD[newPosition.getRow()][newPosition.getColumn()] == null) {
            return validateMove(newPosition);
        } else if (BOARD[newPosition.getRow()][newPosition.getColumn()].getColour() != getColour()) {
            return validateAttack(newPosition);
        }

        return false;
    }

    private boolean validateMove(Position newPosition) {
        if (getCurrentPosition().getColumn() != newPosition.getColumn()) {
            return false;
        }

        int moveBy = getCurrentPosition().equals(initialPosition) ? 2 : 1;

        if (Colour.BLACK.equals(getColour())) {
            return newPosition.getRow() > getCurrentPosition().getRow() && newPosition.getRow() <= getCurrentPosition().getRow() + moveBy;
        } else {
            return newPosition.getRow() < getCurrentPosition().getRow() && newPosition.getRow() >= getCurrentPosition().getRow() - moveBy;
        }
    }

    private boolean validateAttack(Position newPosition) {
        int moveBy = Colour.BLACK.equals(getColour()) ? 1 : -1;

        return (newPosition.getRow() == getCurrentPosition().getRow() + moveBy) &&
                (newPosition.getColumn() == getCurrentPosition().getColumn() + 1 || newPosition.getColumn() == getCurrentPosition().getColumn() - 1);
    }

    @Override
    public String toString() {
        return "Pawn{" + getColour() + "}";
    }
}
