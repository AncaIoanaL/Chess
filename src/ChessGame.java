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
            System.out.print("Player " + playerNumber + " enter current position: ");
            String[] coordinates = scanner.nextLine().split(" ");
            Position currentPosition = new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

            System.out.print("Player " + playerNumber + " enter new position: ");
            coordinates = scanner.nextLine().split(" ");
            Position newPosition = new Position(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));


            BOARD[newPosition.getRow()][newPosition.getColumn()] = BOARD[currentPosition.getRow()][currentPosition.getColumn()];
            BOARD[currentPosition.getRow()][currentPosition.getColumn()] = null;

            playerNumber = playerNumber % 2 == 0 ? 1 : 2;

            printBoard();
        }
    }

    private static void initialiseBoard() {
        initialiseWhite();
        initialiseBlack();
    }

    private static void initialiseBlack() {
        BOARD[0][0] = new Rook(Colour.BLACK);
        BOARD[0][7] = new Rook(Colour.BLACK);

        BOARD[0][1] = new Knight(Colour.BLACK);
        BOARD[0][6] = new Knight(Colour.BLACK);

        BOARD[0][2] = new Bishop(Colour.BLACK);
        BOARD[0][5] = new Bishop(Colour.BLACK);

        BOARD[0][3] = new Queen(Colour.BLACK);

        BOARD[0][4] = new King(Colour.BLACK);

        for (int i = 0; i < 8; i++) {
            BOARD[1][i] = new Pawn(Colour.BLACK);
        }
    }

    private static void initialiseWhite() {
        BOARD[7][0] = new Rook(Colour.WHITE);
        BOARD[7][7] = new Rook(Colour.WHITE);

        BOARD[7][1] = new Knight(Colour.WHITE);
        BOARD[7][6] = new Knight(Colour.WHITE);

        BOARD[7][2] = new Bishop(Colour.WHITE);
        BOARD[7][5] = new Bishop(Colour.WHITE);

        BOARD[7][3] = new Queen(Colour.WHITE);

        BOARD[7][4] = new King(Colour.WHITE);

        for (int i = 0; i < 8; i++) {
            BOARD[6][i] = new Pawn(Colour.WHITE);
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
