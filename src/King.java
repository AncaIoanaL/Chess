public class King extends Piece {

    private final Position initialPosition;
    boolean isCastling;

    public King(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
        initialPosition = currentPosition;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    @Override
    public void move(Position newPosition, Piece[][] board) {
        int difference = newPosition.getColumn() - getCurrentPosition().getColumn();
        isCastling = true;
        if (validateCastling(newPosition, board)) {
            if (Colour.WHITE.equals(getColour())) {
                Piece whiteRook = board[7][7];
                if (difference > 0) {
                    whiteRook.move(new Position(getCurrentPosition().getRow(), getCurrentPosition().getColumn() - 1), board);
                } else {
                    whiteRook.move(new Position(getCurrentPosition().getRow(), getCurrentPosition().getColumn() + 1), board);
                }
            } else {
                Piece blackRook = board[0][7];;
                if (difference > 0) {
                    blackRook.move(new Position(getCurrentPosition().getRow(), getCurrentPosition().getColumn() - 1), board);
                } else {
                    blackRook.move(new Position(getCurrentPosition().getRow(), getCurrentPosition().getColumn() + 1), board);
                }
            }
            isCastling = false;
        } else {
            super.move(newPosition, board);
        }
    }

    @Override
    public void validateMove(Position newPosition, Piece[][] board) {
        super.validateMove(newPosition, board);

        if (validateCastling(newPosition, board)) {
            //TODO set rocada true
        } else if (!validateKingMove(newPosition)) {
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
        validatePawnCheck(board);
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

        boolean diagonal1 = true;
        boolean diagonal2 = true;
        boolean diagonal3 = true;
        boolean diagonal4 = true;

        for (int i = 1; i < 8; i++) {
            diagonal1 = validateTrajectory(board, currentRow, currentColumn, diagonal1, i, i, PieceType.BISHOP);
            diagonal2 = validateTrajectory(board, currentRow, currentColumn, diagonal2, -i, i, PieceType.BISHOP);
            diagonal3 = validateTrajectory(board, currentRow, currentColumn, diagonal3, i, -i, PieceType.BISHOP);
            diagonal4 = validateTrajectory(board, currentRow, currentColumn, diagonal4, -i, -i, PieceType.BISHOP);
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

    private void validatePawnCheck(Piece[][] board) {
        int currentRow = getCurrentPosition().getRow();
        int currentColumn = getCurrentPosition().getColumn();

        if (Colour.BLACK.equals(getColour())) {
            validateTrajectory(board, currentRow, currentColumn, true, 1, 1, PieceType.PAWN);
            validateTrajectory(board, currentRow, currentColumn, true, 1, -1, PieceType.PAWN);
        } else {
            validateTrajectory(board, currentRow, currentColumn, true, -1, 1, PieceType.PAWN);
            validateTrajectory(board, currentRow, currentColumn, true, -1, -1, PieceType.PAWN);
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

    private boolean validateCastling(Position newPosition, Piece[][] board) {
        int difference = newPosition.getColumn() - getCurrentPosition().getColumn();
        if (getCurrentPosition() != initialPosition && Math.abs(difference) != 2) {
            return false;
        }

        if (difference > 0) {
            for (int i = 1; i <= 2; i++) {
                Piece pieceToValidate = board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() + i];
                if (pieceToValidate != null && getCurrentPosition().getRow() != newPosition.getRow()) {
                    return false;
                }
            }

            Piece rook = board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() + 3];
            if (!PieceType.ROOK.equals(rook.getPieceType()) || rook.getCount() == 0) {
                return false;
            }

            for (int i = 0; i <= 2; i++) {
                move(new Position(getCurrentPosition().getRow(), getCurrentPosition().getColumn() + i), board);
                validateCheck(board);
            }
        } else {
            for (int i = 1; i <= 3; i++) {
                Piece pieceToValidate = board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() - i];
                if (pieceToValidate != null && getCurrentPosition().getRow() != newPosition.getRow()) {
                    return false;
                }
            }

            Piece rook = board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() - 4];
            if (!PieceType.ROOK.equals(rook.getPieceType()) || rook.getCount() == 0) {
                return false;
            }

            for (int i = 0; i <= 2; i++) {
                move(new Position(getCurrentPosition().getRow(), getCurrentPosition().getColumn() + i), board);
                validateCheck(board);
            }
        }

//        for (int i = 1; i < 8; i++) {
//            if (getCurrentPosition().getRow() - i < 0 || getCurrentPosition().getRow() + i >= 8 || getCurrentPosition().getColumn() - i < 0 || getCurrentPosition().getColumn() + i >= 8) {
//                return false;
//            } else {
//                if (difference < 0) {
//                    Piece pieceToValidate = board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() + i];
//                    if (pieceToValidate != null) {
//                        return false;
//                    }
//                } else {
//                    Piece pieceToValidate = board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() - i];
//                    if (pieceToValidate != null) {
//                        return false;
//                    }
//                }
//
//            }
//        }

        return true;
    }

//    private boolean validateCheckMate(Piece[][] board, int currentRow, int currentColumn, int incrementRow, int incrementColumn) {
//        int count = 0;
//
//
//        board[getCurrentPosition().getRow()][getCurrentPosition().getColumn() + 1];
//        board[getCurrentPosition().getRow()][getCurrentPosition().getColumn()];
//        board[getCurrentPosition().getRow() + -1][getCurrentPosition().getColumn()];
//        board[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn()];
//        board[getCurrentPosition().getRow() + -1][getCurrentPosition().getColumn() + -1];
//        board[getCurrentPosition().getRow() + -1][getCurrentPosition().getColumn() + 1];
//        board[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn() + 1];
//        board[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn() + -1];
//
//        validateCheck(board);
//    }
}
