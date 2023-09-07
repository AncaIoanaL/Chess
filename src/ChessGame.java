import java.util.Arrays;
import java.util.Scanner;

public class ChessGame {

    private static final Piece[][] BOARD = new Piece[8][8];

    public static void main(String[] args) {
        initialiseBoard();
        printBoard();

        Scanner scanner = new Scanner(System.in);

        int playerNumber = 1;

        while (true) {
            try {
                System.out.print("Player " + playerNumber + " enter current position: ");
                String[] coordinates = scanner.nextLine().split(" ");
                Position currentPosition = new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

                System.out.print("Player " + playerNumber + " enter new position: ");
                coordinates = scanner.nextLine().split(" ");
                Position newPosition = new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

                Piece pieceToMove = BOARD[currentPosition.getRow()][currentPosition.getColumn()];
                if (pieceToMove != null && pieceToMove.validateMove(newPosition, BOARD)) {
                    pieceToMove.move(newPosition, BOARD);
                    playerNumber = playerNumber == 1 ? 2 : 1;
                } else {
                    System.out.println();
                    System.out.println("Player " + playerNumber + " this is an invalid move, please try again.");
                }

                printBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void initialiseBoard() {
        initialiseWhite();
        initialiseBlack();
    }

    private static void initialiseBlack() {
        BOARD[0][0] = new Rook(Colour.BLACK, new Position(0, 0));
        BOARD[0][7] = new Rook(Colour.BLACK, new Position(0, 7));

        BOARD[0][1] = new Knight(Colour.BLACK, new Position(0, 1));
        BOARD[0][6] = new Knight(Colour.BLACK, new Position(0, 6));

        BOARD[0][2] = new Bishop(Colour.BLACK, new Position(0, 2));
        BOARD[0][5] = new Bishop(Colour.BLACK, new Position(0, 5));

        BOARD[0][3] = new Queen(Colour.BLACK, new Position(0, 3));

        BOARD[0][4] = new King(Colour.BLACK, new Position(0, 4));

        for (int i = 0; i < 8; i++) {
            BOARD[1][i] = new Pawn(Colour.BLACK, new Position(1, i));
        }
    }

    private static void initialiseWhite() {
        BOARD[7][0] = new Rook(Colour.WHITE, new Position(7, 0));
        BOARD[7][7] = new Rook(Colour.WHITE, new Position(7, 7));

        BOARD[7][1] = new Knight(Colour.WHITE, new Position(7, 1));
        BOARD[7][6] = new Knight(Colour.WHITE, new Position(7, 6));

        BOARD[7][2] = new Bishop(Colour.WHITE, new Position(7, 2));
        BOARD[7][5] = new Bishop(Colour.WHITE, new Position(7, 5));

        BOARD[7][3] = new Queen(Colour.WHITE, new Position(7, 3));

        BOARD[7][4] = new King(Colour.WHITE, new Position(7, 4));

        for (int i = 0; i < 8; i++) {
            BOARD[6][i] = new Pawn(Colour.WHITE, new Position(6, i));
        }
    }

    private static void printBoard() {
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.println(Arrays.toString(BOARD[i]));
        }
        System.out.println();
    }
}
