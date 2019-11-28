package training.chessington.model.pieces;

import org.junit.Test;
import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class KnightTest {
    @Test
    public void knightCanMoveInLShape() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(4, 4);
        board.placePiece(coords, knight);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(2,1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2,1)));
        assertThat(moves).contains(new Move(coords, coords.plus(2,-1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2,-1)));
        assertThat(moves).contains(new Move(coords, coords.plus(2,1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2,1)));
        assertThat(moves).contains(new Move(coords, coords.plus(2,-1)));
        assertThat(moves).contains(new Move(coords, coords.plus(-2,-1)));
    }

    @Test
    public void knightCanCapture() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, knight);

        Piece enemyPiece = new Knight(PlayerColour.BLACK);
        Coordinates enemyCoords = coords.plus(-2, -1);
        board.placePiece(enemyCoords, enemyPiece);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, enemyCoords));
    }

    @Test
    public void knightCannotCaptureOwn() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(5, 4);
        board.placePiece(coords, knight);

        Piece friendPiece = new Knight(PlayerColour.WHITE);
        Coordinates friendCoords = coords.plus(-2, -1);
        board.placePiece(friendCoords, friendPiece);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, friendCoords));
    }

    @Test
    public void knightCannotMoveOffBoard() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, knight);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).doesNotContain(new Move(coords, coords.plus(1,2)));
    }

    @Test
    public void knightIsNotBlockedByAnyPiece() {
        // Arrange
        Board board = Board.empty();
        Piece knight = new Knight(PlayerColour.WHITE);
        Coordinates coords = new Coordinates(7, 4);
        board.placePiece(coords, knight);

        Piece enemyPiece1 = new Knight(PlayerColour.BLACK);
        Coordinates enemyCoords1 = coords.plus(-1, 0);
        board.placePiece(enemyCoords1, enemyPiece1);

        Piece enemyPiece2 = new Knight(PlayerColour.BLACK);
        Coordinates enemyCoords2 = coords.plus(-1, -1);
        board.placePiece(enemyCoords2, enemyPiece2);

        Piece enemyPiece3 = new Knight(PlayerColour.BLACK);
        Coordinates enemyCoords3 = coords.plus(-1, 1);
        board.placePiece(enemyCoords3, enemyPiece3);

        Piece enemyPiece4 = new Knight(PlayerColour.BLACK);
        Coordinates enemyCoords4 = coords.plus(0, 1);
        board.placePiece(enemyCoords4, enemyPiece4);

        Piece enemyPiece5 = new Knight(PlayerColour.BLACK);
        Coordinates enemyCoords5 = coords.plus(0, -1);
        board.placePiece(enemyCoords5, enemyPiece5);

        // Act
        List<Move> moves = knight.getAllowedMoves(coords, board);

        // Assert
        assertThat(moves).contains(new Move(coords, coords.plus(-1,-2)));
    }
}
