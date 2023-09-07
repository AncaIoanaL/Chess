public abstract class Piece {

    private final Colour colour;
    private Position currentPosition;

    public Piece(Colour colour, Position currentPosition) {
        this.colour = colour;
        this.currentPosition = currentPosition;
    }

    public Colour getColour() {
        return colour;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void move(Position newPosition, Piece[][] BOARD) {
        BOARD[newPosition.getRow()][newPosition.getColumn()] = BOARD[currentPosition.getRow()][currentPosition.getColumn()];
        BOARD[currentPosition.getRow()][currentPosition.getColumn()] = null;
        currentPosition = newPosition;
    }

    public boolean validateMove(Position newPosition, Piece[][] BOARD) {
        return BOARD[newPosition.getRow()][newPosition.getColumn()] == null ||
                BOARD[newPosition.getRow()][newPosition.getColumn()].getColour() != getColour() ||
                currentPosition != newPosition;
    }

    public abstract void attack();

}
