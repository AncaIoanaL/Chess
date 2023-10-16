import java.util.List;

public class King extends Piece {

    private final Position initialPosition;
    boolean isCastling;

    public King(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
        initialPosition = currentPosition;
    }

    @Override
    public void move(Position newPosition, Board board) {
        int difference = newPosition.getColumn() - getCurrentPosition().getColumn();

        if (isCastling) {
            if (Colour.WHITE.equals(getColour())) {
                if (difference > 0) {
                    Piece whiteRook = board.getPiece(7, 7);
                    whiteRook.move(new Position(whiteRook.getCurrentPosition(), 0, -2), board);
                } else {
                    Piece whiteRook = board.getPiece(7, 0);
                    whiteRook.move(new Position(whiteRook.getCurrentPosition(), 0, 3), board);
                }
            } else {
                if (difference > 0) {
                    Piece blackRook = board.getPiece(0, 7);
                    blackRook.move(new Position(blackRook.getCurrentPosition(), 0, -2), board);
                } else {
                    Piece blackRook = board.getPiece(0, 0);
                    blackRook.move(new Position(blackRook.getCurrentPosition(), 0, 3), board);
                }
            }

        }

        isCastling = false;
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Board board) {
        super.validateMove(newPosition, board);

        if (validateCastling(newPosition, board)) {
            isCastling = true;
        } else if (!validateKingMove(newPosition)) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    public void validateCheck(Board board) {
        validateBishopCheck(board);
        validateRookCheck(board);
        validateKnightCheck(board);
        validatePawnCheck(board);
    }

    private boolean validateKingMove(Position newPosition) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return (rowDifference == 1 || columnDifference == 1);
    }

    private void validateBishopCheck(Board board) {
        int row = getCurrentPosition().getRow();
        int column = getCurrentPosition().getColumn();

        // left up
        validateInBetweenPositions(new Position(row - Math.min(row, column), column - Math.min(row, column)), board, PieceType.BISHOP);

        //right up
        validateInBetweenPositions(new Position(row - Math.min(row, 7 - column), column + Math.min(row, 7 - column)), board, PieceType.BISHOP);

        // left down
        validateInBetweenPositions(new Position(row + Math.min(7 - row, column), column - Math.min(7 - row, column)), board, PieceType.BISHOP);

        //right down
        validateInBetweenPositions(new Position(row + Math.min(7 - row, 7 - column), column + Math.min(7 - row, 7 - column)), board, PieceType.BISHOP);
    }

    private void validateRookCheck(Board board) {
        validateInBetweenPositions(new Position(getCurrentPosition().getRow(), 0), board, PieceType.ROOK);
        validateInBetweenPositions(new Position(getCurrentPosition().getRow(), 7), board, PieceType.ROOK);
        validateInBetweenPositions(new Position(0, getCurrentPosition().getColumn()), board, PieceType.ROOK);
        validateInBetweenPositions(new Position(7, getCurrentPosition().getColumn()), board, PieceType.ROOK);
    }

    private void validatePawnCheck(Board board) {
        int row = getCurrentPosition().getRow();
        int column = getCurrentPosition().getColumn();

        if (Colour.WHITE.equals(getColour())) {
            validateInBetweenPositions(new Position(row - 1, column + 1), board, PieceType.PAWN);
            validateInBetweenPositions(new Position(row - 1, column - 1), board, PieceType.PAWN);
        } else {
            validateInBetweenPositions(new Position(row + 1, column + 1), board, PieceType.PAWN);
            validateInBetweenPositions(new Position(row + 1, column - 1), board, PieceType.PAWN);
        }
    }

    private void validateInBetweenPositions(Position newPosition, Board board, PieceType pieceType) {
        List<Position> inBetweenPositions = getCurrentPosition().getInBetweenPositions(newPosition);
        inBetweenPositions.add(newPosition);

        for (Position inBetweenPosition : inBetweenPositions) {
            if (inBetweenPosition.validPosition()) {
                Piece pieceToValidate = board.getPiece(inBetweenPosition);

                if (pieceToValidate != null) {
                    if (pieceToValidate.getColour() != getColour() && (pieceType.equals(pieceToValidate.getPieceType()) || PieceType.QUEEN.equals(pieceToValidate.getPieceType()))) {
                        throw new InvalidMoveDueToCheckException();
                    }
                }
            }
        }
    }

    private void validateKnightCheck(Board board) {
        int row = getCurrentPosition().getRow();
        int column = getCurrentPosition().getColumn();

        validateKnightPosition(board, new Position(row + 1, column + 2));
        validateKnightPosition(board, new Position(row + 1, column - 2));
        validateKnightPosition(board, new Position(row - 1, column + 2));
        validateKnightPosition(board, new Position(row - 1, column - 2));
        validateKnightPosition(board, new Position(row + 2, column + 1));
        validateKnightPosition(board, new Position(row + 2, column - 1));
        validateKnightPosition(board, new Position(row - 2, column - 1));
        validateKnightPosition(board, new Position(row - 2, column + 1));
    }

    private void validateKnightPosition(Board board, Position position) {
        if (position.validPosition()) {
            Piece pieceToValidate = board.getPiece(position);

            if (pieceToValidate != null && pieceToValidate.getColour() != getColour() && PieceType.KNIGHT.equals(pieceToValidate.getPieceType())) {
                throw new InvalidMoveDueToCheckException();
            }
        }
    }

    private boolean validateCastling(Position newPosition, Board board) {
        int columnDifference = newPosition.getColumn() - getCurrentPosition().getColumn();
        if (getCurrentPosition() != initialPosition || getCurrentPosition().getRow() != newPosition.getRow() || Math.abs(columnDifference) != 2) {
            return false;
        }

        if (columnDifference > 0) {
            for (int i = 1; i <= 2; i++) {
                Piece pieceToValidate = board.getPiece(getCurrentPosition(), 0, i);
                if (pieceToValidate != null) {
                    return false;
                }
            }

            Piece rook = board.getPiece(getCurrentPosition(), 0, 3);
            if (rook == null || !PieceType.ROOK.equals(rook.getPieceType()) || rook.getMovesCounter() != 0) {
                return false;
            }

            for (int i = 0; i <= 2; i++) {
                move(new Position(getCurrentPosition(), 0, i), board);
                validateCheck(board);
            }
        } else {
            for (int i = 1; i <= 3; i++) {
                Piece pieceToValidate = board.getPiece(getCurrentPosition(), 0, -i);
                if (pieceToValidate != null) {
                    return false;
                }
            }

            Piece rook = board.getPiece(getCurrentPosition(), 0, -4);
            if (rook == null || !PieceType.ROOK.equals(rook.getPieceType()) || rook.getMovesCounter() != 0) {
                return false;
            }

            for (int i = 0; i <= 2; i++) {
                move(new Position(getCurrentPosition(), 0, -i), board);
                validateCheck(board);
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (Colour.BLACK.equals(getColour())) {
            return "♚";
        } else {
            return "♔";
        }
    }
}
