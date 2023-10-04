import java.util.Scanner;

public class Pawn extends Piece {

    private final Position initialPosition;
    private Position previousPosition;

    public Pawn(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
        initialPosition = currentPosition;
        previousPosition = currentPosition;
    }

    public Position getInitialPosition() {
        return initialPosition;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    @Override
    public void move(Position newPosition, Board board) {
        if (shouldPromotePawn(newPosition)) {
            board.setPiece(newPosition, promotePawn(newPosition));
            board.setPiece(getCurrentPosition(), null);
        } else {
            previousPosition = getCurrentPosition();
            super.move(newPosition, board);
        }
    }

    @Override
    public void validateMove(Position newPosition, Board board) {
        boolean isValid = validatePawnEnPassant(newPosition, board) ||
                (validatePawnMove(newPosition, board) && validatePawnInBetweenPositions(board)) ||
                validatePawnAttack(newPosition, board);

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public String toString() {
        return "Pawn{" + getColour() + "}";
    }

    private boolean validatePawnEnPassant(Position newPosition, Board board) {
        if (Colour.BLACK.equals(getColour())) {
            if (!(board.getPiece(newPosition.getRow() - 1, newPosition.getColumn()) instanceof Pawn whitePawn) ||
                    !(board.getPiece(newPosition.getRow() - 2, newPosition.getColumn()) instanceof Pawn otherBlackPawn)) {
                return false;
            }

            return board.getPiece(newPosition) == null &&
                    Colour.WHITE.equals(whitePawn.getColour()) && whitePawn.getPreviousPosition().equals(whitePawn.getInitialPosition()) &&
                    Colour.BLACK.equals(otherBlackPawn.getColour());
        } else {
            if (!(board.getPiece(newPosition.getRow() + 1, newPosition.getColumn()) instanceof Pawn blackPawn) ||
                    !(board.getPiece(newPosition.getRow() + 2, newPosition.getColumn()) instanceof Pawn otherWhitePawn)) {
                return false;
            }

            return board.getPiece(newPosition) == null &&
                    Colour.BLACK.equals(blackPawn.getColour()) && blackPawn.getPreviousPosition().equals(blackPawn.getInitialPosition()) &&
                    Colour.WHITE.equals(otherWhitePawn.getColour());
        }
    }

    private boolean validatePawnMove(Position newPosition, Board board) {
        if (board.getPiece(newPosition) != null || getCurrentPosition().getColumn() != newPosition.getColumn()) {
            return false;
        }

        int moveBy = getCurrentPosition().equals(initialPosition) ? 2 : 1;

        if (Colour.BLACK.equals(getColour())) {
            return newPosition.getRow() > getCurrentPosition().getRow() && newPosition.getRow() <= getCurrentPosition().getRow() + moveBy;
        } else {
            return newPosition.getRow() < getCurrentPosition().getRow() && newPosition.getRow() >= getCurrentPosition().getRow() - moveBy;
        }
    }

    private boolean validatePawnInBetweenPositions(Board board) {
        if (getCurrentPosition().equals(initialPosition)) {
            if (Colour.BLACK.equals(getColour())) {
                return board.getPiece(getCurrentPosition().getRow() + 1, getCurrentPosition().getColumn()) == null;
            } else {
                return board.getPiece(getCurrentPosition().getRow() - 1, getCurrentPosition().getColumn()) == null;
            }
        }

        return true;
    }

    public boolean validatePawnAttack(Position newPosition, Board board) {
        if (getColour().equals(board.getPiece(newPosition).getColour())) {
            return false;
        }

        int moveBy = Colour.BLACK.equals(getColour()) ? 1 : -1;

        return (newPosition.getRow() == getCurrentPosition().getRow() + moveBy) &&
                (newPosition.getColumn() == getCurrentPosition().getColumn() + 1 || newPosition.getColumn() == getCurrentPosition().getColumn() - 1);
    }

    private boolean shouldPromotePawn(Position newPosition) {
        if (Colour.BLACK.equals(getColour())) {
            return newPosition.getRow() == 7;
        } else {
            return newPosition.getRow() == 0;
        }
    }

    private Piece promotePawn(Position newPosition) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Pawn is valid for promotion, please choose the piece it will be promoted to: ");
            String promotionPiece = scanner.nextLine();

            switch (promotionPiece) {
                case "QUEEN" -> {
                    return new Queen(getColour(), newPosition);
                }
                case "ROOK" -> {
                    return new Rook(getColour(), newPosition);
                }
                case "BISHOP" -> {
                    return new Bishop(getColour(), newPosition);
                }
                case "KNIGHT" -> {
                    return new Knight(getColour(), newPosition);
                }
                default -> System.out.println("Input is invalid. Should be QUEEN, ROOK, BISHOP or KNIGHT");
            }
        }
    }
}
