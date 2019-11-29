package training.chessington.model;

import org.junit.Test;
import training.chessington.model.pieces.King;
import training.chessington.model.pieces.Piece;
import training.chessington.model.pieces.Rook;

import java.util.List;

import static training.chessington.model.pieces.PieceAssert.*;
import static org.assertj.core.api.Assertions.*;

public class GameTest {
    @Test
    public void whenCheckedMustMoveToAddressIt() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates kingCoords = new Coordinates(5, 4);
        board.placePiece(kingCoords, king);

        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates rookCoords = new Coordinates(6, 6);
        board.placePiece(rookCoords, rook);

        Coordinates enemyRookCoords = new Coordinates(7, 4);
        board.placePiece(enemyRookCoords, new Rook(PlayerColour.BLACK));

        Game game = new Game(board);

        // Act
        List<Move> kingMoves = game.getAllowedMoves(kingCoords);
        List<Move> rookMoves = game.getAllowedMoves(rookCoords);

        // Assert
        assertThat(kingMoves).contains(new Move(kingCoords, kingCoords.plus(1, 1)));
        assertThat(kingMoves).contains(new Move(kingCoords, kingCoords.plus(-1, 1)));
        assertThat(kingMoves).contains(new Move(kingCoords, kingCoords.plus(1, -1)));
        assertThat(kingMoves).contains(new Move(kingCoords, kingCoords.plus(0, 1)));
        assertThat(kingMoves).doesNotContain(new Move(kingCoords, kingCoords.plus(1, 0)));
        assertThat(kingMoves).doesNotContain(new Move(kingCoords, kingCoords.plus(-1, 0)));

        assertThat(rookMoves).contains(new Move(rookCoords, rookCoords.plus(0, -2)));
        assertThat(rookMoves).doesNotContain(new Move(rookCoords, rookCoords.plus(-4, 0)));
        assertThat(rookMoves).doesNotContain(new Move(rookCoords, rookCoords.plus(0, 2)));
    }

    @Test
    public void cannotMoveIntoCheck() {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.WHITE);
        Coordinates kingCoords = new Coordinates(5, 3);
        board.placePiece(kingCoords, king);

        Piece rook = new Rook(PlayerColour.WHITE);
        Coordinates rookCoords = new Coordinates(6, 6);
        board.placePiece(rookCoords, rook);

        Coordinates enemyRookCoords = new Coordinates(7, 4);
        board.placePiece(enemyRookCoords, new Rook(PlayerColour.BLACK));

        Game game = new Game(board);

        // Act
        List<Move> kingMoves = game.getAllowedMoves(kingCoords);

        // Assert
        assertThat(kingMoves).doesNotContain(new Move(kingCoords, kingCoords.plus(1, 1)));
        assertThat(kingMoves).doesNotContain(new Move(kingCoords, kingCoords.plus(-1, 1)));
        assertThat(kingMoves).doesNotContain(new Move(kingCoords, kingCoords.plus(0, 1)));
    }


    @Test
    public void gameEndsWhenCheckmate() throws InvalidMoveException {
        // Arrange
        Board board = Board.empty();
        Piece king = new King(PlayerColour.BLACK);
        Coordinates kingCoords = new Coordinates(7, 3);
        board.placePiece(kingCoords, king);

        Coordinates enemyRookCoords1 = new Coordinates(5, 6);
        board.placePiece(enemyRookCoords1, new Rook(PlayerColour.WHITE));

        Coordinates enemyRookCoords2 = new Coordinates(6, 7);
        board.placePiece(enemyRookCoords2, new Rook(PlayerColour.WHITE));

        Game game = new Game(board);

        // Act
        game.makeMove(new Move(enemyRookCoords1, enemyRookCoords1.plus(2, 0)));

        // Assert
        assertThat(game.isEnded()).isTrue();
    }
}
