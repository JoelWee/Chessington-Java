package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Knight extends AbstractPiece {
    public Knight(PlayerColour colour) {
        super(PieceType.KNIGHT, colour);
    }

    public static List<Coordinates> knightDirections;

    static {
        knightDirections = Stream.of(new int[][]{{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}})
                .map(dir -> new Coordinates(dir[0], dir[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        return knightDirections.stream()
                .map(dir -> getMoveOneStepInDirection(from, dir, board))
                .filter(Optional::isPresent)
                .map(o -> o.get())
                .collect(Collectors.toList());
    }
}
