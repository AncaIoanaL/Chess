public class King extends Piece {

    public King(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void move(Position newPosition, Piece[][] board) {
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Piece[][] board) {
        super.validateMove(newPosition, board);
        boolean isValid = validateKingMove(newPosition);

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    public void validateCheck(Piece[][] board) {
        validateBishopCheck(board);
        validateRookCheck(board);
        validateKnightCheck(board);
    }

    @Override
    public String toString() {
        return "King{" + getColour() + "}";
    }

    private boolean validateKingMove(Position newPosition) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return (rowDifference == 1 || columnDifference == 1);
    }

    private void validateBishopCheck(Piece[][] board) {
        int currentRow = getCurrentPosition().getRow();
        int currentColumn = getCurrentPosition().getColumn();

        boolean shouldValidateDiagonal1 = true;
        boolean shouldValidateDiagonal2 = true;
        boolean shouldValidateDiagonal3 = true;
        boolean shouldValidateDiagonal4 = true;

        for (int i = 1; i < 8; i++) {
            shouldValidateDiagonal1 = validateTrajectory(board, currentRow, currentColumn, shouldValidateDiagonal1, i, i, PieceType.BISHOP);
            shouldValidateDiagonal2 = validateTrajectory(board, currentRow, currentColumn, shouldValidateDiagonal2, -i, i, PieceType.BISHOP);
            shouldValidateDiagonal3 = validateTrajectory(board, currentRow, currentColumn, shouldValidateDiagonal3, i, -i, PieceType.BISHOP);
            shouldValidateDiagonal4 = validateTrajectory(board, currentRow, currentColumn, shouldValidateDiagonal4, -i, -i, PieceType.BISHOP);
        }
    }

    private void validateRookCheck(Piece[][] board) {
        int currentRow = getCurrentPosition().getRow();
        int currentColumn = getCurrentPosition().getColumn();

        boolean line1 = true;
        boolean line2 = true;
        boolean line3 = true;
        boolean line4 = true;

        for (int i = 1; i < 8; i++) {
            line1 = validateTrajectory(board, currentRow, currentColumn, line1, 0, i, PieceType.ROOK);
            line2 = validateTrajectory(board, currentRow, currentColumn, line2, i, 0, PieceType.ROOK);
            line3 = validateTrajectory(board, currentRow, currentColumn, line3, 0, -i, PieceType.ROOK);
            line4 = validateTrajectory(board, currentRow, currentColumn, line4, -i, 0, PieceType.ROOK);
        }
    }

    private boolean validateTrajectory(Piece[][] board, int currentRow, int currentColumn, boolean shouldValidateTrajectory, int incrementRow, int incrementColumn, PieceType pieceType) {
        if (shouldValidateTrajectory) {
            if (currentRow + incrementRow < 0 || currentRow + incrementRow >= 8 || currentColumn + incrementColumn < 0 || currentColumn + incrementColumn >= 8) {
                return false;
            } else {
                Piece pieceToValidate = board[getCurrentPosition().getRow() + incrementRow][getCurrentPosition().getColumn() + incrementColumn];

                if (pieceToValidate != null) {
                    if (pieceToValidate.getColour() != getColour() && (pieceType.equals(pieceToValidate.getPieceType()) || PieceType.QUEEN.equals(pieceToValidate.getPieceType()))) {
                        throw new InvalidMoveDueToCheckException();
                    } else {
                        return false;
                    }
                }
            }
        }

        return shouldValidateTrajectory;
    }

    private void validateKnightCheck(Piece[][] board) {
        int currentRow = getCurrentPosition().getRow();
        int currentColumn = getCurrentPosition().getColumn();

        validateKnightPosition(board, currentRow, currentColumn, 1, 2);
        validateKnightPosition(board, currentRow, currentColumn, 1, -2);
        validateKnightPosition(board, currentRow, currentColumn, -1, 2);
        validateKnightPosition(board, currentRow, currentColumn, -1, -2);
        validateKnightPosition(board, currentRow, currentColumn, 2, 1);
        validateKnightPosition(board, currentRow, currentColumn, 2, -1);
        validateKnightPosition(board, currentRow, currentColumn, -2, -1);
        validateKnightPosition(board, currentRow, currentColumn, -2, 1);
    }

    private void validateKnightPosition(Piece[][] board, int currentRow, int currentColumn, int incrementRow, int incrementColumn) {
        if (currentRow + incrementRow >= 0 && currentRow + incrementRow < 8 && currentColumn + incrementColumn >= 0 && currentColumn + incrementColumn < 8) {
            Piece pieceToValidate = board[getCurrentPosition().getRow() + incrementRow][getCurrentPosition().getColumn() + incrementColumn];

            if (pieceToValidate != null && pieceToValidate.getColour() != getColour() && PieceType.KNIGHT.equals(pieceToValidate.getPieceType())) {
                throw new InvalidMoveDueToCheckException();
            }
        }
    }

//    public boolean validatePawnCheck(Piece[][] BOARD) {
//        if (BOARD[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn() + 1].getColour() != getColour()) {
//            return true;
//        }
//    }


}
