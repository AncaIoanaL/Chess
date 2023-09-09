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
        return super.validateMove(newPosition, BOARD) &&
                validateRookInBetweenPositions(newPosition, BOARD) &&
                (newPosition.getRow() == getCurrentPosition().getRow() || newPosition.getColumn() == getCurrentPosition().getColumn());
    }

    @Override
    public String toString() {
        return "Rook{" + getColour() + "}";
    }

    private boolean validateRookInBetweenPositions(Position newPosition, Piece[][] BOARD) {
        if (getCurrentPosition().getColumn() != newPosition.getColumn()) {
            int columnDifference = getCurrentPosition().getColumn() - newPosition.getColumn();
            int minColumn = Math.min(newPosition.getColumn(), getCurrentPosition().getColumn());

            for (int i = 0; i < Math.abs(columnDifference); i++) {
                if (BOARD[newPosition.getRow()][minColumn + i + 1] != null) {
                    return false;
                }
            }
        } else {
            int rowDifference = getCurrentPosition().getRow() - newPosition.getRow();
            int minRow = Math.min(newPosition.getRow(), getCurrentPosition().getRow());

            for (int i = 0; i < Math.abs(rowDifference); i++) {
                if (BOARD[minRow + i + 1][newPosition.getColumn()] != null) {
                    return false;
                }
            }
        }

        return true;
    }
}
