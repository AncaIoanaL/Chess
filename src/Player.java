import java.util.List;
import java.util.Objects;

public class Player {

    List<Piece> pieces;
    Colour colour;
    int number;

    public Player(Colour colour, int number) {
        this.colour = colour;
        this.number = number;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Colour getColour() {
        return colour;
    }

    public int getNumber() {
        return number;
    }

    public void validatePieceColour(Piece pieceToMove) {
        if (!colour.equals(pieceToMove.getColour())) {
            throw new InvalidPieceToMoveColourException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return number == player.number && Objects.equals(pieces, player.pieces) && colour == player.colour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces, colour, number);
    }

    @Override
    public String toString() {
        return "Player " + number;
    }
}
