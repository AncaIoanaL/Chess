public abstract class Piece {

    private final Colour colour;

    public Piece(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public abstract void move();

    public abstract void attack();

}
