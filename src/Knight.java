public class Knight extends Piece {

    public Knight(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }



    @Override
    public void move(Position newPosition, Board board) {
        super.move(newPosition, board);
    }

    @Override
    public void validateMove(Position newPosition, Board board) {
        super.validateMove(newPosition, board);
        boolean isValid = validateKnightMove(newPosition);

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public String toString() {
        if (Colour.BLACK.equals(getColour())) {
            return "♞";
        } else {
            return "♘";
        }
    }

    private boolean validateKnightMove(Position newPosition) {
        int rowDifference = Math.abs(newPosition.getRow() - getCurrentPosition().getRow());
        int columnDifference = Math.abs(newPosition.getColumn() - getCurrentPosition().getColumn());

        return (rowDifference == 1 && columnDifference == 2 || rowDifference == 2 && columnDifference == 1);
    }
}
