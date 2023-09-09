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
    public void move(Position newPosition, Piece[][] BOARD) {
        if (shouldPromotePawn(newPosition)) {
            BOARD[newPosition.getRow()][newPosition.getColumn()] = promotePawn(newPosition);
            BOARD[getCurrentPosition().getRow()][getCurrentPosition().getColumn()] = null;
        } else {
            previousPosition = getCurrentPosition();
            super.move(newPosition, BOARD);
        }
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean validateMove(Position newPosition, Piece[][] BOARD) {
        return validatePawnEnPassant(newPosition, BOARD) ||
                (validatePawnMove(newPosition, BOARD) && validatePawnInBetweenPositions(BOARD)) ||
                validatePawnAttack(newPosition, BOARD);
    }

    @Override
    public String toString() {
        return "Pawn{" + getColour() + "}";
    }

    private boolean validatePawnEnPassant(Position newPosition, Piece[][] BOARD) {
        if (Colour.BLACK.equals(getColour())) {
            if (!(BOARD[newPosition.getRow() - 1][newPosition.getColumn()] instanceof Pawn whitePawn) || !(BOARD[newPosition.getRow() - 2][newPosition.getColumn()] instanceof Pawn otherBlackPawn)) {
                return false;
            }

            return BOARD[newPosition.getRow()][newPosition.getColumn()] == null &&
                    Colour.WHITE.equals(whitePawn.getColour()) && whitePawn.getPreviousPosition().equals(whitePawn.getInitialPosition()) &&
                    Colour.BLACK.equals(otherBlackPawn.getColour());
        } else {
            if (!(BOARD[newPosition.getRow() + 1][newPosition.getColumn()] instanceof Pawn blackPawn) || !(BOARD[newPosition.getRow() + 2][newPosition.getColumn()] instanceof Pawn otherWhitePawn)) {
                return false;
            }

            return BOARD[newPosition.getRow()][newPosition.getColumn()] == null &&
                    Colour.BLACK.equals(blackPawn.getColour()) && blackPawn.getPreviousPosition().equals(blackPawn.getInitialPosition()) &&
                    Colour.WHITE.equals(otherWhitePawn.getColour());
        }
    }

    private boolean validatePawnMove(Position newPosition, Piece[][] BOARD) {
        if (BOARD[newPosition.getRow()][newPosition.getColumn()] != null || getCurrentPosition().getColumn() != newPosition.getColumn()) {
            return false;
        }

        int moveBy = getCurrentPosition().equals(initialPosition) ? 2 : 1;

        if (Colour.BLACK.equals(getColour())) {
            return newPosition.getRow() > getCurrentPosition().getRow() && newPosition.getRow() <= getCurrentPosition().getRow() + moveBy;
        } else {
            return newPosition.getRow() < getCurrentPosition().getRow() && newPosition.getRow() >= getCurrentPosition().getRow() - moveBy;
        }
    }

    private boolean validatePawnInBetweenPositions(Piece[][] BOARD) {
        if (getCurrentPosition().equals(initialPosition)) {
            if (Colour.BLACK.equals(getColour())) {
                return BOARD[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn()] == null;
            } else {
                return BOARD[getCurrentPosition().getRow() - 1][getCurrentPosition().getColumn()] == null;
            }
        }

        return true;
    }

    private boolean validatePawnAttack(Position newPosition, Piece[][] BOARD) {
        if (!getColour().equals(BOARD[newPosition.getRow()][newPosition.getColumn()].getColour())) {
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
