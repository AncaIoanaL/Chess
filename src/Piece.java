public abstract class Piece {

    private final Colour colour;
    private Position currentPosition;
    int count = 0;

    public Piece(Colour colour, Position currentPosition) {
        this.colour = colour;
        this.currentPosition = currentPosition;
    }

    public Colour getColour() {
        return colour;
    }

    public int getCount() {
        return count;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void move(Position newPosition, Board board) {
        board.movePiece(currentPosition, newPosition);
        currentPosition = newPosition;
        count++;
    }

    public void validateMove(Position newPosition, Board board) {
        boolean isValid = board.getPiece(newPosition) == null ||
                board.getPiece(newPosition).getColour() != getColour() ||
                currentPosition != newPosition;

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    public abstract PieceType getPieceType();
}
