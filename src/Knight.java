public class Knight extends Piece {

    public Knight(Colour colour, Position currentPosition) {
        super(colour, currentPosition);
    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Knight{" + getColour() + "}";
    }
}
