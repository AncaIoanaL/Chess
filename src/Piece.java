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

    public void move(Position newPosition, Piece[][] board) {
        board[newPosition.getRow()][newPosition.getColumn()] = board[currentPosition.getRow()][currentPosition.getColumn()];
        board[currentPosition.getRow()][currentPosition.getColumn()] = null;
        currentPosition = newPosition;
        count++;
    }

    public void validateMove(Position newPosition, Piece[][] board) {
        boolean isValid = board[newPosition.getRow()][newPosition.getColumn()] == null ||
                board[newPosition.getRow()][newPosition.getColumn()].getColour() != getColour() ||
                currentPosition != newPosition;

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    public abstract PieceType getPieceType();
}
