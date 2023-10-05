import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChessGame {

    private static final Player PLAYER_1 = new Player(Colour.WHITE, 1);
    private static final Player PLAYER_2 = new Player(Colour.BLACK, 2);

    public static void main(String[] args) {
        Board board = new Board();
        Map<Colour, List<Piece>> pieces = board.initialiseBoard();
        board.printBoard();

        PLAYER_1.setPieces(pieces.get(Colour.WHITE));
        PLAYER_2.setPieces(pieces.get(Colour.BLACK));
        Player currentPlayer = PLAYER_1;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                Position currentPosition = askPlayerForCurrentPosition(currentPlayer, scanner);
                Position newPosition = askPlayerForNewPosition(currentPlayer, scanner);

                Piece pieceToMove = board.getPiece(currentPosition);

                try {
                    currentPlayer.validatePieceColour(pieceToMove);
                    pieceToMove.validateMove(newPosition, board);

                    pieceToMove.move(newPosition, board);

                    if (PLAYER_1.equals(currentPlayer)) {
                        board.getWhiteKing().validateCheck(board);
                        currentPlayer = PLAYER_2;
                    } else {
                        board.getBlackKing().validateCheck(board);
                        currentPlayer = PLAYER_1;
                    }

                } catch (NullPointerException e) {
                    System.out.println("\n" + currentPlayer + " there is no piece at the position provided, please try again.");
                } catch (InvalidMoveException e) {
                    System.out.println("\n" + currentPlayer + " this is an invalid move, please try again.");
                } catch (InvalidPieceToMoveColourException e) {
                    System.out.println("\n" + currentPlayer + " this is an invalid move as you cannot move your opponents' pieces, please try again.");
                } catch (InvalidMoveDueToCheckException e) {
                    pieceToMove.move(currentPosition, board);
                    System.out.println("\n" + currentPlayer + " this is an invalid move as you are in check, please try again.");
                }

                board.printBoard();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Position askPlayerForCurrentPosition(Player player, Scanner scanner) {
        System.out.print("\n" + player + " enter current position: ");
        String[] coordinates = scanner.nextLine().split(" ");

        int row = 8 - Integer.parseInt(coordinates[0]);
        int column = coordinates[1].charAt(0) - 65;
        return new Position(row, column);
    }

    private static Position askPlayerForNewPosition(Player player, Scanner scanner) {
        System.out.print(player + " enter new position: ");
        String[] coordinates = scanner.nextLine().split(" ");

        int row = 8 - Integer.parseInt(coordinates[0]);
        int column = coordinates[1].charAt(0) - 65;
        return new Position(row, column);
    }
}
