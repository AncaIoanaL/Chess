import java.util.*;

public class Board {

    private final Piece[][] positions = new Piece[8][0];
    private King whiteKing;
    private King blackKing;
    private final Map<Colour, List<Piece>> pieces = new HashMap<>();

    public King getWhiteKing() {
        return whiteKing;
    }

    public King getBlackKing() {
        return blackKing;
    }

    public Piece getPiece(Position position) {
        return positions[position.getRow()][position.getColumn()];
    }

    public Piece getPiece(int row, int column) {
        return positions[row][column];
    }

    public void setPiece(Position position, Piece piece) {
        positions[position.getRow()][position.getColumn()] = piece;
    }

    public void movePiece(Position currentPosition, Position newPosition) {
        positions[newPosition.getRow()][newPosition.getColumn()] = positions[currentPosition.getRow()][currentPosition.getColumn()];
        positions[currentPosition.getRow()][currentPosition.getColumn()] = null;
    }

    public List<Piece> getPieces(Colour colour) {
        return pieces.get(colour);
    }

    public void initialiseBoard() {
        initialiseWhite();
        initialiseBlack();
    }

    public void printBoard() {
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(positions[i]));
        }
        System.out.println();
    }

    private void initialiseWhite() {
        positions[7][0] = new Rook(Colour.WHITE, new Position(7, 0));
        positions[7][7] = new Rook(Colour.WHITE, new Position(7, 7));

        positions[7][1] = new Knight(Colour.WHITE, new Position(7, 1));
        positions[7][6] = new Knight(Colour.WHITE, new Position(7, 6));

        positions[7][2] = new Bishop(Colour.WHITE, new Position(7, 2));
        positions[7][5] = new Bishop(Colour.WHITE, new Position(7, 5));

        positions[7][3] = new Queen(Colour.WHITE, new Position(7, 3));

        whiteKing = new King(Colour.WHITE, new Position(7, 4));
        positions[7][4] = whiteKing;

        for (int i = 0; i < 8; i++) {
            positions[6][i] = new Pawn(Colour.WHITE, new Position(6, i));
        }

        for (int i = 0; i < 8; i++) {
            pieces.get(Colour.WHITE).add(i, positions[6][i]);
        }
        for (int i = 0; i < 8; i++) {
            pieces.get(Colour.WHITE).add(i + 8, positions[7][i]);
        }
    }

    private void initialiseBlack() {
        positions[0][0] = new Rook(Colour.BLACK, new Position(0, 0));
        positions[0][7] = new Rook(Colour.BLACK, new Position(0, 7));

        positions[0][1] = new Knight(Colour.BLACK, new Position(0, 1));
        positions[0][6] = new Knight(Colour.BLACK, new Position(0, 6));

        positions[0][2] = new Bishop(Colour.BLACK, new Position(0, 2));
        positions[0][5] = new Bishop(Colour.BLACK, new Position(0, 5));

        positions[0][3] = new Queen(Colour.BLACK, new Position(0, 3));

        blackKing = new King(Colour.BLACK, new Position(0, 4));
        positions[0][4] = blackKing;

        for (int i = 0; i < 8; i++) {
            positions[1][i] = new Pawn(Colour.BLACK, new Position(1, i));
        }

        for (int i = 0; i < 8; i++) {
            pieces.get(Colour.BLACK).add(i, positions[1][i]);
        }
        for (int i = 0; i < 8; i++) {
            pieces.get(Colour.BLACK).add(i + 8, positions[0][i]);
        }
    }
}
