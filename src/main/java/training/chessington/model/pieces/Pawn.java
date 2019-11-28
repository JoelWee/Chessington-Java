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

        int direction = colour == PlayerColour.WHITE ? -1 : 1;
        Move singleStepForward = new Move(from, new Coordinates(from.getRow() + direction, from.getCol()));
        allowedMoves.add(singleStepForward);

        return allowedMoves;
    }
}
