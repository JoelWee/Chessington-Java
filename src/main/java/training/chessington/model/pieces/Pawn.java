package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();

        int direction = getDirection();
        Move singleStepForward = new Move(from, from.plus(direction, 0));
        addMoveIfAllowed(allowedMoves, singleStepForward, board);

        if (hasNotMoved(from)) {
            Move twoStepsForward = new Move(from, from.plus(direction * 2, 0));
            addMoveIfAllowed(allowedMoves, twoStepsForward, board);
        }

        return allowedMoves;
    }

    private void addMoveIfAllowed(List<Move> allowedMoves, Move move, Board board) {
        if(board.get(move.getTo()) == null) {
            allowedMoves.add(move);
        }
    }

    private int getDirection() {
        return colour == PlayerColour.WHITE ? -1 : 1;
    }

    private boolean hasNotMoved(Coordinates from) {
        int startRow = colour == PlayerColour.WHITE ? 6 : 1;
        return startRow == from.getRow();
    }
}
