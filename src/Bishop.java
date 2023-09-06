public class Bishop extends Piece {

    public Bishop(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Bishop{" + getColour() + "}";
    }
}
