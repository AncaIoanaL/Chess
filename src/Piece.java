import java.util.List;

public abstract class Piece {

    private final Colour colour;
    private Position currentPosition;
    int movesCounter = 0;

    public Piece(Colour colour, Position currentPosition) {
        this.colour = colour;
        this.currentPosition = currentPosition;
    }

    public Colour getColour() {
        return colour;
    }

    public int getMovesCounter() {
        return movesCounter;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void move(Position newPosition, Board board) {
        board.movePiece(currentPosition, newPosition);
        currentPosition = newPosition;
        movesCounter++;
    }

    public void validateMove(Position newPosition, Board board) {
        boolean isValid = board.getPiece(newPosition) == null ||
                board.getPiece(newPosition).getColour() != getColour() ||
                currentPosition != newPosition;

        if (!isValid) {
            throw new InvalidMoveException();
        }
    }

    public boolean validateInBetweenPosition(Position newPosition, Board board) {
        List<Position> inBetweenPositions = getCurrentPosition().getInBetweenPositions(newPosition);

        for (Position inBetweenPosition : inBetweenPositions) {
            if (board.getPiece(inBetweenPosition) != null) {
                return false;
            }
        }

        return true;
    }

    public abstract PieceType getPieceType();
}
