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
        boolean isValid = (validateInBetweenPosition(newPosition, board)) &&
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
}
