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
        boolean isValid = validateInBetweenPosition(newPosition, board) &&
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
        if (Colour.BLACK.equals(getColour())) {
            return "♜";
        } else {
            return "♖";
        }
    }
}
