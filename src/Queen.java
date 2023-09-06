public class Queen extends Piece {

    public Queen(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Queen{" + getColour() + "}";
    }
}
