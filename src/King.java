public class King extends Piece {

    public King(Colour colour, Position currentPosition) {
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
        boolean isValid = validateKingMove(newPosition);

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    public void validateCheck(Piece[][] board) {
        validateBishopCheck(board);
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

    public boolean validateRookCheck(Piece[][] board) {
        for (int i = getCurrentPosition().getColumn(); i < 8; i++) {
            if (board[getCurrentPosition().getRow()][i].getColour() != getColour()) {
                return true;
            }
        }

        for (int i = getCurrentPosition().getColumn(); i >= 0; i--) {
            if (board[getCurrentPosition().getRow()][i].getColour() != getColour()) {
                return true;
            }
        }

        for (int i = getCurrentPosition().getRow(); i < 8; i++) {
            if (board[i][getCurrentPosition().getColumn()].getColour() != getColour()) {
                return true;
            }
        }

        for (int i = getCurrentPosition().getRow(); i >= 0; i--) {
            if (board[i][getCurrentPosition().getColumn()].getColour() != getColour()) {
                return true;
            }
        }

        return false;
    }

    public void validateBishopCheck(Piece[][] board) {
        int currentRow = getCurrentPosition().getRow();
        int currentColumn = getCurrentPosition().getColumn();

        boolean diagonal1 = true;
        boolean diagonal2 = true;
        boolean diagonal3 = true;
        boolean diagonal4 = true;

        for (int i = 1; i < 8; i++) {
            diagonal1 = checkDiagonal(board, currentRow, currentColumn, diagonal1, i, i);
            diagonal2 = checkDiagonal(board, currentRow, currentColumn, diagonal2, -i, i);
            diagonal3 = checkDiagonal(board, currentRow, currentColumn, diagonal3, i, -i);
            diagonal4 = checkDiagonal(board, currentRow, currentColumn, diagonal4, -i, -i);
        }
    }

    private boolean checkDiagonal(Piece[][] board, int currentRow, int currentColumn, boolean diagonal, int incrementRow, int incrementColumn) {
        if (diagonal) {
            if (currentRow + incrementRow < 0 || currentRow + incrementRow >= 8 ||
                    currentColumn + incrementColumn < 0 || currentColumn + incrementColumn >= 8) {
                return false;
            } else {
                Piece pieceToValidate = board[getCurrentPosition().getRow() + incrementRow][getCurrentPosition().getColumn() + incrementColumn];

                if (pieceToValidate != null) {
                    if (pieceToValidate.getColour() != getColour() && pieceToValidate instanceof Bishop) {
                        throw new InvalidMoveDueToCheckException();
                    } else {
                        return false;
                    }
                }
            }
        }

        return diagonal;
    }

//    public boolean validateKnightCheck(Piece[][] board) {
//        if (board[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn() + 2].getColour() != getColour()) {
//            return true;
//        }
//
//        if (board[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn() - 2].getColour() != getColour()) {
//            return true;
//        }
//
//        if (BOARD[getCurrentPosition().getRow() - 1][getCurrentPosition().getColumn() + 2].getColour() != getColour()) {
//            return true;
//        }
//
//        if (BOARD[getCurrentPosition().getRow() - 1][getCurrentPosition().getColumn() - 2].getColour() != getColour()) {
//            return true;
//        }
//
//        if (BOARD[getCurrentPosition().getRow() + 2][getCurrentPosition().getColumn() + 1].getColour() != getColour()) {
//            return true;
//        }
//
//        if (BOARD[getCurrentPosition().getRow() + 2][getCurrentPosition().getColumn() - 1].getColour() != getColour()) {
//            return true;
//        }
//
//        if (BOARD[getCurrentPosition().getRow() - 2][getCurrentPosition().getColumn() + 1].getColour() != getColour()) {
//            return true;
//        }
//
//        if (BOARD[getCurrentPosition().getRow() - 2][getCurrentPosition().getColumn() - 1].getColour() != getColour()) {
//            return true;
//        }
//
//        return false;
//    }

//    public boolean validatePawnCheck(Piece[][] BOARD) {
//        if (BOARD[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn() + 1].getColour() != getColour()) {
//            return true;
//        }
//    }


}
