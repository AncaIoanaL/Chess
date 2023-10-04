import java.util.Scanner;

public class ChessGame {

    public static void main(String[] args) {
        Board board = new Board();
        board.initialiseBoard();
        board.printBoard();

        Scanner scanner = new Scanner(System.in);

        int playerNumber = 1;

        while (true) {
            try {
                System.out.println(board.getPieces(Colour.WHITE));
                System.out.println(board.getPieces(Colour.BLACK));
                System.out.println();

                Position currentPosition = askPlayerForCurrentPosition(playerNumber, scanner);
                Position newPosition = askPlayerForNewPosition(playerNumber, scanner);

                Piece pieceToMove = board.getPiece(currentPosition);

                try {
                    validatePlayerColour(playerNumber, pieceToMove);
                    pieceToMove.validateMove(newPosition, board);

                    pieceToMove.move(newPosition, board);

                    if (playerNumber == 1) {
                        board.getWhiteKing().validateCheck(board);
                    } else {
                        board.getBlackKing().validateCheck(board);
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
                    pieceToMove.move(currentPosition, board);
                    System.out.println();
                    System.out.println("Player " + playerNumber + " this is an invalid move as you are in check, please try again.");
                }

                board.printBoard();
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

    private static void validatePlayerColour(int playerNumber, Piece pieceToMove) {
        boolean isValid = (!(Colour.BLACK.equals(pieceToMove.getColour()) && playerNumber == 1)) && (!(Colour.WHITE.equals(pieceToMove.getColour()) && playerNumber == 2));

        if (!isValid) {
            throw new InvalidPieceToMoveColourException();
        }
    }
}
