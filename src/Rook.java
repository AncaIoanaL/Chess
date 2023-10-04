public class Rook extends Piece {

    public Rook(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void move(Position newPosition, Board board) {
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Board board) {
        super.validateMove(newPosition, board);
        boolean isValid = validateRookInBetweenPositions(newPosition, board) &&
                (newPosition.getRow() == getCurrentPosition().getRow() || newPosition.getColumn() == getCurrentPosition().getColumn());

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public String toString() {
        return "Rook{" + getColour() + "}";
    }

    private boolean validateRookInBetweenPositions(Position newPosition, Board board) {
        if (getCurrentPosition().getColumn() != newPosition.getColumn()) {
            int columnDifference = getCurrentPosition().getColumn() - newPosition.getColumn();
            int minColumn = Math.min(newPosition.getColumn(), getCurrentPosition().getColumn());

            for (int i = 0; i < Math.abs(columnDifference); i++) {
                if (board.getPiece(newPosition.getRow(), minColumn + i + 1) != null) {
                    return false;
                }
            }
        } else {
            int rowDifference = getCurrentPosition().getRow() - newPosition.getRow();
            int minRow = Math.min(newPosition.getRow(), getCurrentPosition().getRow());

            for (int i = 0; i < Math.abs(rowDifference); i++) {
                if (board.getPiece(minRow + i + 1, newPosition.getColumn()) != null) {
                    return false;
                }
            }
        }

        return true;
    }
}
