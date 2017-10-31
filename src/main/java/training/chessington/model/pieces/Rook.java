package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Rook extends AbstractPiece {
    public Rook(PlayerColour colour) {
        super(PieceType.ROOK, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();

        for (Coordinates dir : Coordinates.axialDirections) {
            addMovesInDirection(allowedMoves, from, dir, board);
        }

        return allowedMoves;
    }

    private void addMovesInDirection(List<Move> allowedMoves, Coordinates from, Coordinates dir, Board board) {
        for(Coordinates to = from.plus(dir); to.isOnBoard(); to = to.plus(dir)) {
            if(board.isEmpty(to) || board.isCapturable(to, colour)) {
                allowedMoves.add(new Move(from, to));
            }
            if(!board.isEmpty(to)) {
                break;
            }
        }
    }
}
