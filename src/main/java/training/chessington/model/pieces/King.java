package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class King extends AbstractPiece {
    public King(PlayerColour colour) {
        super(PieceType.KING, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();

        for (Coordinates dir : Coordinates.axialDirections) {
            addMoveIfAllowed(allowedMoves, new Move(from, from.plus(dir)), board);
        }

        for (Coordinates dir : Coordinates.diagonalDirections) {
            addMoveIfAllowed(allowedMoves, new Move(from,  from.plus(dir)), board);
        }

        return allowedMoves;
    }

    private void addMoveIfAllowed(List<Move> allowedMoves, Move move, Board board) {
        if (move.getTo().isOnBoard() && board.isEmptyOrCapturable(move.getTo(), colour)) {
            allowedMoves.add(move);
        }
    }

}
