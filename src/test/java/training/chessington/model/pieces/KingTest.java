package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KingTest {
    @Test
    public void whiteKingCanMoveUpOneStepInAnyDirection() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : dirs) {
            assertThat(moves).contains(new Move(coords, coords.plus(dir[0], dir[1])));
        }
    }

    @Test
    public void blackKingCanMoveUpOneStepInAnyDirection() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : dirs) {
            assertThat(moves).contains(new Move(coords, coords.plus(dir[0], dir[1])));
        }
    }

    @Test
    public void whiteKingCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, king);

        Piece enemyPiece = new Rook(PlayerColour.BLACK);
        Coordinates enemyCoords = coords.plus(-1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void blackKingCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, king);

        Piece enemyPiece = new Rook(PlayerColour.WHITE);
        Coordinates enemyCoords = coords.plus(-1, 1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void whiteKingCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, king);

        Piece friendPiece = new Rook(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-1, 1);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void blackKingCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, king);

        Piece friendPiece = new Rook(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(-1, 1);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void whiteKingCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,0)));
    }

    @Test
    public void blackKingCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, king);

        // Act
        List<Move> moves = king.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,0)));
    }
}
