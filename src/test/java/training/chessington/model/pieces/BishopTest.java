package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class BishopTest {
    @Test
    public void bishopCanMoveDiagonally() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, bishop);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(1,1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2,2)));
        assertThat(moves).contains(new Move(coords, coords.plus(-3,-3)));
        assertThat(moves).contains(new Move(coords, coords.plus(3,-3)));
    }

    @Test
    public void bishopCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, bishop);

        Piece enemyPiece = new Bishop(PlayerColour.BLACK);
        Coordinates enemyCoords = coords.plus(-4, -4);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void bishopCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, bishop);

        Piece friendPiece = new Bishop(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-4, -4);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void bishopCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, bishop);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,1)));
    }

    @Test
    public void bishopIsBlockedByEnemyPiece() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, bishop);

        Piece enemyPiece = new Bishop(PlayerColour.BLACK);
        Coordinates friendCoords = coords.plus(-1, -1);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,-2)));
    }


    @Test
    public void bishopIsBlockedByOwnPiece() {
        // Arrange
        Board board = Board.empty();
        Piece bishop = new Bishop(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, bishop);

        Piece enemyPiece = new Bishop(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-1, -1);
        board.placePiece(friendCoords, enemyPiece);

        // Act
        List<Move> moves = bishop.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(-2,-2)));
    }
}
