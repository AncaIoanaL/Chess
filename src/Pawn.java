import java.util.Scanner;

public class Pawn extends Piece {

    private final Position initialPosition;

    public Pawn(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
        initialPosition = currentPosition;
    }

    @Override
    public void move(Position newPosition, Piece[][] BOARD) {
        if (shouldPromotePawn(newPosition, BOARD)) {
            BOARD[newPosition.getRow()][newPosition.getColumn()] = promotePawn(newPosition);
            BOARD[getCurrentPosition().getRow()][getCurrentPosition().getColumn()] = null;
        } else {
            super.move(newPosition, BOARD);
        }
    }

    @Override
    public void attack() {

    }

    @Override
    public boolean validateMove(Position newPosition, Piece[][] BOARD) {
        if (BOARD[newPosition.getRow()][newPosition.getColumn()] == null) {
            return validateMove(newPosition) && validateInBetweenPositions(BOARD);
        } else if (BOARD[newPosition.getRow()][newPosition.getColumn()].getColour() != getColour()) {
            return validateAttack(newPosition);
        }

        return false;
    }

    @Override
    public String toString() {
        return "Pawn{" + getColour() + "}";
    }

    private boolean validateMove(Position newPosition) {
        if (getCurrentPosition().getColumn() != newPosition.getColumn()) {
            return false;
        }

        int moveBy = getCurrentPosition().equals(initialPosition) ? 2 : 1;

        if (Colour.BLACK.equals(getColour())) {
            return newPosition.getRow() > getCurrentPosition().getRow() && newPosition.getRow() <= getCurrentPosition().getRow() + moveBy;
        } else {
            return newPosition.getRow() < getCurrentPosition().getRow() && newPosition.getRow() >= getCurrentPosition().getRow() - moveBy;
        }
    }

    private boolean validateInBetweenPositions(Piece[][] BOARD) {
        if (getCurrentPosition().equals(initialPosition)) {
            if (Colour.BLACK.equals(getColour())) {
                return BOARD[getCurrentPosition().getRow() + 1][getCurrentPosition().getColumn()] == null;
            } else {
                return BOARD[getCurrentPosition().getRow() - 1][getCurrentPosition().getColumn()] == null;
            }
        }

        return true;
    }

    private boolean validateAttack(Position newPosition) {
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
