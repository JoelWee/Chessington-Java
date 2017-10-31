package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class RookTest {
    @Test
    public void whiteRookCanMoveVertically() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, rook);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        for (int i = 0; i < 8; i++) {
            if (i != coords.getRow()) {
                assertThat(moves).contains(new Move(coords, new Coordinates(i, coords.getCol())));
            }
        }
    }

    @Test
    public void blackRookCanMoveVertically() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, rook);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        for (int i = 0; i < 8; i++) {
            if (i != coords.getRow()) {
                assertThat(moves).contains(new Move(coords, new Coordinates(i, coords.getCol())));
            }
        }
    }

    @Test
    public void whiteRookCanMoveHorizontally() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, rook);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        for (int i = 0; i < 8; i++) {
            if (i != coords.getCol()) {
                assertThat(moves).contains(new Move(coords, new Coordinates(coords.getRow(), i)));
            }
        }
    }

    @Test
    public void blackRookCanMoveHorizontally() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(6, 4);
        board.placePiece(coords, rook);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        for (int i = 0; i < 8; i++) {
            if (i != coords.getCol()) {
                assertThat(moves).contains(new Move(coords, new Coordinates(coords.getRow(), i)));
            }
        }
    }

    @Test
    public void whiteRookCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, rook);

        Piece enemyPiece = new Rook(PlayerColour.BLACK);
        Coordinates enemyCoords = coords.plus(-4, 0);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void blackRookCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, rook);

        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoords = coords.plus(-4, 0);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void whiteRookCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, rook);

        Piece friendPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-4, 0);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void blackRookCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, rook);

        Piece friendPiece = new Rook(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(0, 3);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void whiteRookCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, rook);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,0)));
    }

    @Test
    public void blackRookCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, rook);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,0)));
    }

    @Test
    public void whiteRookIsBlockedByEnemyPiece() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, rook);

        Piece enemyPiece = new Rook(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(-1, 0);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,0)));
    }

    @Test
    public void blackRookIsBlockedByEnemyPiece() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, rook);

        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-1, 0);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,0)));
    }


    @Test
    public void whiteRookIsBlockedByOwnPiece() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, rook);

        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-1, 0);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,0)));
    }

    @Test
    public void blackRookIsBlockedByOwnPiece() {
        // Arrange
        Board board = Board.empty();
        Piece rook = new Rook(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, rook);

        Piece enemyPiece = new Rook(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(-1, 0);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = rook.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,0)));
    }
}
