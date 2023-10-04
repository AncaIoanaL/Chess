# Chess

This program allows two players to have a game of chess. 

The program uses Scanner to take the current position and new position from each player. The current player will have to input the row number, a spacebar and then the capital letter of the column according to chess board below. For example, if the player inputs 7 A (current position) and 5 A (new position), the program will move the black pawn accordingly. The program also recognises special moves for the pawn - en passant move and the promotion. 

![chess-board.jpg](image%2Fchess-board.jpg)

The program also has validating methods for the below:
- the basic rules of movement for each piece type (pawn, rook, knight, bishop, queen, king), including the special moves for pawn promotion, pawn en passant and castling
- if the player is trying to move one of its opponent's pieces
- validates if the current player is in check

The program catches and handles exceptions accordingly, prompting the players with messages that indicate what they are doing wrong.

Although the program is functional, I am still yet to add the below features to it:
- finish game once a player is in check mate (add validation to identify check mate)
- finalise the castling move
- learn frontend to add a UI to the game 

In order for the board to be properly aligned, please ensure you are using a monospaced font that supports unicode characters, such as NSimSun, MS Gothic and SimSun.