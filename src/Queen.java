public class Queen extends Piece {

    public Queen(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }


    @Override
    public void move(Position newPosition, Board board) {
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Board board) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        super.validateMove(newPosition, board);
        boolean isValid = (validateInBetweenPositionsRookLikeTrajectory(newPosition, board) ||
                validateInBetweenPositionsBishopLikeTrajectory(newPosition, board)) &&
                ((rowDifference == columnDifference) || (newPosition.getRow() == getCurrentPosition().getRow() ||
                        newPosition.getColumn() == getCurrentPosition().getColumn()));

        if (!isValid) {
            throw new InvalidMoveException();
        }

    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public String toString() {
        if (Colour.BLACK.equals(getColour())) {
            return "♛";
        } else {
            return "♕";
        }
    }

    private boolean validateInBetweenPositionsRookLikeTrajectory(Position newPosition, Board board) {
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

    private boolean validateInBetweenPositionsBishopLikeTrajectory(Position newPosition, Board board) {
        for (int i = 1; i <= Math.abs(getCurrentPosition().getColumn() - newPosition.getColumn()); i++) {
            if (getCurrentPosition().getRow() > newPosition.getRow() && getCurrentPosition().getColumn() > newPosition.getColumn()) {
                if (board.getPiece(getCurrentPosition(), -i, -i) != null) {
                    return false;
                }
            } else if (getCurrentPosition().getRow() > newPosition.getRow() && getCurrentPosition().getColumn() < newPosition.getColumn()) {
                if (board.getPiece(getCurrentPosition(), -i, i) != null) {
                    return false;
                }
            } else if (getCurrentPosition().getRow() < newPosition.getRow() && getCurrentPosition().getColumn() < newPosition.getColumn()) {
                if (board.getPiece(getCurrentPosition(), i, i) != null) {
                    return false;
                }
            } else if (getCurrentPosition().getRow() < newPosition.getRow() && getCurrentPosition().getColumn() > newPosition.getColumn()) {
                if (board.getPiece(getCurrentPosition(), i, -i) != null) {
                    return false;
                }
            }
        }

        return true;
    }
}
