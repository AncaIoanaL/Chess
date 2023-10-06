public class Bishop extends Piece {

    public Bishop(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void move(Position newPosition, Board board) {
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Board board) {
        super.validateMove(newPosition, board);
        boolean isValid = validateBishopMove(newPosition) && validateInBetweenPosition(newPosition, board);

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    private boolean validateBishopMove(Position newPosition) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return rowDifference == columnDifference;
    }

    @Override
    public String toString() {
        if (Colour.BLACK.equals(getColour())) {
            return "♝";
        } else {
            return "♗";
        }
    }
}
