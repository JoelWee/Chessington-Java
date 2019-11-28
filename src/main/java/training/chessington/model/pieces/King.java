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
            addMoveIfAllowed(allowedMoves, new Move(from, from.plus(dir)), board);
        }

        addCastleMoves(allowedMoves, from, board);

        return allowedMoves;
    }

    private void addCastleMoves(List<Move> allowedMoves, Coordinates from, Board board) {
        if (!hasBeenMoved) {
            Coordinates kingSideRookCoords = new Coordinates(Board.getBackRowIndex(colour), 7);
            if (canCastle(from, kingSideRookCoords, board)) {
                allowedMoves.add(new Move(from, from.plus(0, 2)));
            }

            Coordinates queenSideRookCoords = new Coordinates(Board.getBackRowIndex(colour), 0);
            if (canCastle(from, queenSideRookCoords, board)) {
                allowedMoves.add(new Move(from, from.plus(0, -2)));
            }
        }
    }

    private boolean canCastle(Coordinates kingCoords, Coordinates rookCoords, Board board) {
        Piece rook = board.get(rookCoords);
        int direction = kingCoords.getCol() - rookCoords.getCol() > 0 ? -1 : 1;

        if (rook != null && rook.getType() == PieceType.ROOK &&
                rook.getColour() == colour && !board.get(rookCoords).hasBeenMoved()) {
            for (int i = 1; i <= 2; i++) {
                Coordinates curr = kingCoords.plus(0, direction * i);
                if (board.get(curr) != null || board.isSquareUnderThreat(curr, colour)) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private void addMoveIfAllowed(List<Move> allowedMoves, Move move, Board board) {
        if (move.getTo().isOnBoard() && board.isEmptyOrCapturable(move.getTo(), colour)) {
            allowedMoves.add(move);
        }
    }

}
