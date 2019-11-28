package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class QueenTest {
    @Test
    public void queenCanMoveAxially() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, queen);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        for (int i = 0; i < 8; i++) {
            if (i != coords.getRow()) {
                assertThat(moves).contains(new Move(coords, new Coordinates(i, coords.getCol())));
            }
        }

        for (int i = 0; i < 8; i++) {
            if (i != coords.getCol()) {
                assertThat(moves).contains(new Move(coords, new Coordinates(coords.getRow(), i)));
            }
        }
    }
    @Test
    public void queenCanMoveDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, queen);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1,1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2,2)));
        assertThat(moves).contains(new Move(coords, coords.plus(-3,-3)));
        assertThat(moves).contains(new Move(coords, coords.plus(3,-3)));
    }

    @Test
    public void queenCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, queen);

        Piece enemyPiece = new Queen(PlayerColour.BLACK);
        Coordinates enemyCoords = coords.plus(-4, -4);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void queenCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, queen);

        Piece friendPiece = new Queen(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-4, -4);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void queenCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, queen);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,1)));
    }

    @Test
    public void queenIsBlockedByEnemyPieceAxially() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, queen);

        Piece enemyPiece = new Queen(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(-1, 0);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,0)));
    }

    @Test
    public void queenIsBlockedByEnemyPieceDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, queen);

        Piece enemyPiece = new Queen(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(-1, -1);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,-2)));
    }

    @Test
    public void queenIsBlockedByOwnPieceAxially() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, queen);

        Piece enemyPiece = new Queen(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-1, 0);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,0)));
    }

    @Test
    public void queenIsBlockedByOwnPieceDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece queen = new Queen(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, queen);

        Piece enemyPiece = new Queen(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-1, -1);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = queen.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,-2)));
    }
}
