public class Rook extends Piece {

    public Rook(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Rook{" + getColour() + "}";
    }
}
