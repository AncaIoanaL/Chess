public class Bishop extends Piece {

    public Bishop(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void attack() {

    }

    @Override
    public void move(Position newPosition, Piece[][] board) {
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Piece[][] board) {
        super.validateMove(newPosition, board);
        boolean isValid = validateBishopMove(newPosition) && validateBishopInBetweenPositions(newPosition, board);

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public String toString() {
        return "Bishop{" + getColour() + "}";
    }

    private boolean validateBishopMove(Position newPosition) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return rowDifference == columnDifference;
    }

    private boolean validateBishopInBetweenPositions(Position newPosition, Piece[][] board) {
        for (int i = 1; i <= Math.abs(getCurrentPosition().getColumn() - newPosition.getColumn()); i++) {
            if (getCurrentPosition().getRow() > newPosition.getRow() && getCurrentPosition().getColumn() > newPosition.getColumn()) {
                if (board[getCurrentPosition().getRow() - i][getCurrentPosition().getColumn() - i] != null) {
                    return false;
                }
            } else if (getCurrentPosition().getRow() > newPosition.getRow() && getCurrentPosition().getColumn() < newPosition.getColumn()) {
                if (board[getCurrentPosition().getRow() - i][getCurrentPosition().getColumn() + i] != null) {
                    return false;
                }
            } else if (getCurrentPosition().getRow() < newPosition.getRow() && getCurrentPosition().getColumn() < newPosition.getColumn()) {
                if (board[getCurrentPosition().getRow() + i][getCurrentPosition().getColumn() + i] != null) {
                    return false;
                }
            } else if (getCurrentPosition().getRow() < newPosition.getRow() && getCurrentPosition().getColumn() > newPosition.getColumn()) {
                if (board[getCurrentPosition().getRow() + i][getCurrentPosition().getColumn() - i] != null) {
                    return false;
                }
            }
        }

        return true;
    }
}
