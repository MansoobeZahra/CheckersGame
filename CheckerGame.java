import java.awt.Color;

import javax.swing.SwingUtilities;

public class CheckerGame {
    private CheckerPiece[][] board;
    private boolean redTurn;

        // Load the winning sound effect
    // private SoundEffect winSound;

    
    public CheckerGame() {
        board = new CheckerPiece[8][8];
        redTurn = true;
        initializeBoard();
        // winSound = new SoundEffect("soundeffects\\clapping and cheering.wav"); // Replace with your win sound path
    }

    private void initializeBoard() {
        // Place red pieces in starting positions (rows 0-2)
        for (int row = 0; row < 3; row++) {
            for (int col = row % 2; col < 8; col += 2) {
                board[row][col] = new CheckerPiece(Color.RED, "CheckersGame\\images\\Red_piece.png");
            }
        }

        // Place black pieces in starting positions (rows 5-7)
        for (int row = 5; row < 8; row++) {
            for (int col = row % 2; col < 8; col += 2) {
                board[row][col] = new CheckerPiece(Color.BLACK, "CheckersGame\\images\\Black_piece.png");
            }
        }
    }

    public void resetGame() {
        redTurn = true;
        initializeBoard();
    }

    public CheckerPiece getPieceAt(int row, int col) {
        return board[row][col];
    }


    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        CheckerPiece piece = getPieceAt(fromRow, fromCol);
        if (piece != null && isValidMove(fromRow, fromCol, toRow, toCol)) {
            board[toRow][toCol] = piece;
            board[fromRow][fromCol] = null;
    
            // Handle pawn promotion
            if (!piece.isKing() && (toRow == 0 || toRow == 7)) {
                piece.makeKing();
                piece.setImagePath(piece.getColor() == Color.RED ? "CheckersGame\\images\\Red_king_piece.png" : "CheckersGame\\images\\Black_king_piece.png");
            }
    
            // Handle capturing pieces
            if (Math.abs(fromRow - toRow) == 2) {
                int capturedRow = (fromRow + toRow) / 2;
                int capturedCol = (fromCol + toCol) / 2;
                board[capturedRow][capturedCol] = null;
            }
    
              // Check if the game is won
              String winner = isGameOver();
              if (winner != null) {
                  // Play the sound in a new thread
                  new Thread(() -> {
                      SoundEffect winSound = new SoundEffect("CheckersGame\\soundeffects\\splash 03.wav");
                      winSound.play();
                  }).start();
  
                  // Display the winning frame
                  SwingUtilities.invokeLater(() -> new WinningFrame(winner).setVisible(true));
                }
    
            redTurn = !redTurn;
            return true;
        }
        return false;
    }
    
   
    private boolean isValidMove(int startX, int startY, int endX, int endY) {
        // Check if the end position is within the bounds of the board
        if (endX < 0 || endX >= 8 || endY < 0 || endY >= 8 || board[endX][endY] != null) {
            return false; // Destination is outside the board or already occupied
        }
    
        CheckerPiece piece = board[startX][startY];
        int direction = (piece.getColor() == Color.RED) ? 1 : -1; // Forward direction for regular pieces
    
        // Check if the move is a regular move (one step diagonally)
        if (Math.abs(endX - startX) == 1 && Math.abs(endY - startY) == 1) {
            if (piece.isKing() || (endX - startX == direction)) {
                return true; // King pieces can move in all directions; regular pieces can only move forward
            }
        }
    
        // Check if the move is a capturing move (jump over opponent's piece)
        if (Math.abs(endX - startX) == 2 && Math.abs(endY - startY) == 2) {
            int capturedX = (startX + endX) / 2;
            int capturedY = (startY + endY) / 2;
    
            // Check if there is an opponent's piece to capture
            CheckerPiece capturedPiece = board[capturedX][capturedY];
            if (capturedPiece != null && capturedPiece.getColor() != piece.getColor()) {
                if (piece.isKing() || (endX - startX == 2 * direction)) {
                    return true; // Allow capturing moves for kings in all directions, and regular pieces only forward
                }
            }
        }
    
        return false; // Invalid move
    }
    
    
    private boolean isKingRow(int row, Color color) {
        return (color == Color.RED && row == 7) || (color == Color.BLACK && row == 0);
    }

    public boolean isRedTurn() {
        return redTurn;
    }

    public String isGameOver() {
        int redPieces = 0;
        int blackPieces = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                CheckerPiece piece = board[row][col];
                if (piece != null) {
                    if (piece.getColor() == Color.RED) {
                        redPieces++;
                    } else if (piece.getColor() == Color.BLACK) {
                        blackPieces++;
                    }
                }
            }
        }

  
        if (redPieces == 0) {
            return "black"; // Black wins
        } else if (blackPieces == 0) {
            return "red"; // Red wins
        } else {
            // Check for king capture (no valid moves for a player)
            if (!hasValidMoves(redTurn ? Color.RED : Color.BLACK)) {
                return redTurn ? "black" : "red"; // Opponent wins if current player has no valid moves
            }
        }
    
        return null; // No winner yet
    }

    
    private boolean hasValidMoves(Color color) {
        // Iterate through all remaining pieces of the player with the specified color
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                CheckerPiece piece = board[row][col];
                if (piece != null && piece.getColor() == color) {
                    // Check for any valid capture or advance move for the piece
                    if (isValidMove(row, col, row + (color == Color.RED ? 1 : -1), col + 1) || // Diagonal right (capture or advance)
                        isValidMove(row, col, row + (color == Color.RED ? 1 : -1), col - 1) || // Diagonal left (capture or advance)
                        (piece.isKing() && (isValidMove(row, col, row - (color == Color.RED ? 1 : -1), col + 1) || // King: Diagonal right (capture or advance)
                                            isValidMove(row, col, row - (color == Color.RED ? 1 : -1), col - 1)))) { // King: Diagonal left (capture or advance)
                        return true; // Valid move found, player has options
                    }
                }
            }
        }
        return false; // No valid moves found for all pieces of the specified color
      }
}
