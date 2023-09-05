public class Pawn extends Piece {

    public Pawn(Colour colour) {
        super(colour);
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Pawn{" + getColour() + "}";
    }
}
