import java.util.Arrays;
import java.util.Scanner;

public class ChessGame {

    private static final Piece[][] BOARD = new Piece[8][8];
    private static King whiteKing;
    private static King blackKing;

    public static void main(String[] args) {
        initialiseBoard();
        printBoard();

        Scanner scanner = new Scanner(System.in);

        int playerNumber = 1;

        while (true) {
            try {
                Position currentPosition = askPlayerForCurrentPosition(playerNumber, scanner);
                Position newPosition = askPlayerForNewPosition(playerNumber, scanner);

                Piece pieceToMove = BOARD[currentPosition.getRow()][currentPosition.getColumn()];

                try {
                    pieceToMove.validateMove(newPosition, BOARD);
                    validatePlayerColour(playerNumber, pieceToMove);

                    pieceToMove.move(newPosition, BOARD);

                    if (playerNumber == 1) {
                        whiteKing.validateCheck(BOARD);
                    }

                    playerNumber = playerNumber == 1 ? 2 : 1;
                } catch (NullPointerException e) {
                    System.out.println();
                    System.out.println("Player " + playerNumber + " there is no piece at the position provided, please try again.");
                } catch (InvalidMoveException e) {
                    System.out.println();
                    System.out.println("Player " + playerNumber + " this is an invalid move, please try again.");
                } catch (InvalidPieceToMoveColourException e) {
                    System.out.println();
                    System.out.println("Player " + playerNumber + " this is an invalid move as you cannot move your opponents' pieces, please try again.");
                } catch (InvalidMoveDueToCheckException e) {
                    pieceToMove.move(currentPosition, BOARD);
                    System.out.println();
                    System.out.println("Player " + playerNumber + " this is an invalid move as you are in check, please try again.");
                }

                printBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Position askPlayerForCurrentPosition(int playerNumber, Scanner scanner) {
        System.out.print("Player " + playerNumber + " enter current position: ");
        String[] coordinates = scanner.nextLine().split(" ");

        int row = 8 - Integer.parseInt(coordinates[0]);
        int column = coordinates[1].charAt(0) - 65;
        return new Position(row, column);
    }

    private static Position askPlayerForNewPosition(int playerNumber, Scanner scanner) {
        System.out.print("Player " + playerNumber + " enter new position: ");
        String[] coordinates = scanner.nextLine().split(" ");

        int row = 8 - Integer.parseInt(coordinates[0]);
        int column = coordinates[1].charAt(0) - 65;
        return new Position(row, column);
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

        blackKing = new King(Colour.BLACK, new Position(0, 4));
        BOARD[0][4] = blackKing;

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

        whiteKing = new King(Colour.WHITE, new Position(7, 4));
        BOARD[7][4] = whiteKing;

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

    private static void validatePlayerColour(int playerNumber, Piece pieceToMove) {
        boolean isValid = (!Colour.BLACK.equals(pieceToMove.getColour()) || playerNumber != 1) && (!Colour.WHITE.equals(pieceToMove.getColour()) || playerNumber != 2);

        if (!isValid) {
            throw new InvalidPieceToMoveColourException();
        }
    }
}
