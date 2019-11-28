package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPiece implements Piece {

    protected final Piece.PieceType type;
    protected final PlayerColour colour;

    protected AbstractPiece(Piece.PieceType type, PlayerColour colour) {
        this.type = type;
        this.colour = colour;
    }

    @Override
    public Piece.PieceType getType() {
        return type;
    }

    @Override
    public PlayerColour getColour() {
        return colour;
    }

    @Override
    public String toString() {
        return colour.toString() + " " + type.toString();
    }

    List<Move> getMovesInDirection(Coordinates from, Coordinates dir, Board board) {
        List<Move> allowedMoves = new ArrayList<>();
        for(Coordinates to = from.plus(dir); to.isOnBoard(); to = to.plus(dir)) {
            if(board.isEmptyOrCapturable(to, colour)) {
                allowedMoves.add(new Move(from, to));
            }
            if(!board.isEmpty(to)) {
                break;
            }
        }
        return allowedMoves;
    }

    Optional<Move> getMoveOneStepInDirection(Coordinates from, Coordinates dir, Board board) {
        Coordinates to = from.plus(dir);

        if(to.isOnBoard() && board.isEmptyOrCapturable(to, colour)) {
            return Optional.of(new Move(from, to));
        } else {
            return Optional.empty();
        }
    }
}
