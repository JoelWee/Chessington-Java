package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    public static final int SIZE = 8;
    private final Board board;

    private PlayerColour nextPlayer = PlayerColour.WHITE;

    private boolean isEnded = false;

    public Game(Board board) {
        this.board = board;
    }

    public Piece pieceAt(int row, int col) {
        return board.get(new Coordinates(row, col));
    }

    public List<Move> getAllowedMoves(Coordinates from) {
        if (isEnded) {
            return new ArrayList<>();
        }

        Piece piece = board.get(from);
        if (piece == null || piece.getColour() != nextPlayer) {
            return new ArrayList<>();
        }

        List<Move> allowedMoves = piece.getAllowedMoves(from, board);

        allowedMoves = allowedMoves.stream()
                .filter(this::noOwnCheckAfterMove)
                .collect(Collectors.toList());

        return allowedMoves;
    }

    private boolean isUnderCheck(PlayerColour colour) {
        return board.findAny(Piece.PieceType.KING, colour)
                .map(coords -> board.isSquareUnderThreat(coords, colour))
                .orElse(false);
    }

    private boolean noOwnCheckAfterMove(Move move) {
        Piece origToPiece = board.get(move.getTo());
        Piece origFromPiece = board.get(move.getFrom());

        board.placePiece(move.getTo(), origFromPiece);
        board.placePiece(move.getFrom(), null);

        boolean stopsCheck = !isUnderCheck(nextPlayer);

        board.placePiece(move.getTo(), origToPiece);
        board.placePiece(move.getFrom(), origFromPiece);

        return stopsCheck;
    }

    public void makeMove(Move move) throws InvalidMoveException {
        if (isEnded) {
            throw new InvalidMoveException("Game has ended!");
        }

        Coordinates from = move.getFrom();
        Coordinates to = move.getTo();

        Piece piece = board.get(from);
        if (piece == null) {
            throw new InvalidMoveException(String.format("No piece at %s", from));
        }

        if (piece.getColour() != nextPlayer) {
            throw new InvalidMoveException(String.format("Wrong colour piece - it is %s's turn", nextPlayer));
        }

        if (!piece.getAllowedMoves(move.getFrom(), board).contains(move)) {
            throw new InvalidMoveException(String.format("Cannot move piece %s from %s to %s", piece, from, to));
        }

        board.move(from, to);
        nextPlayer = nextPlayer == PlayerColour.WHITE ? PlayerColour.BLACK : PlayerColour.WHITE;

        if(isUnderCheck(nextPlayer)) {
            isEnded = board.hasNoMoves(nextPlayer);
        }
    }

    public boolean isEnded() {
        return isEnded;
    }

    public String getResult() {
        return null;
    }
}
