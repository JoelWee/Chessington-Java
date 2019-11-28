package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Queen extends AbstractPiece {
    public Queen(PlayerColour colour) {
        super(PieceType.QUEEN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();

        for (Coordinates dir : Coordinates.axialDirections) {
            allowedMoves.addAll(getMovesInDirection(from, dir, board));
        }

        for (Coordinates dir : Coordinates.diagonalDirections) {
            allowedMoves.addAll(getMovesInDirection(from, dir, board));
        }

        return allowedMoves;
    }
}
