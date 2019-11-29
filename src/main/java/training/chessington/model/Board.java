package training.chessington.model;

import training.chessington.model.pieces.*;

import java.util.Optional;

public class Board {
    private Piece[][] board = new Piece[8][8];
    private Move lastMove;

    private Board() {
    }

    public static Board forNewGame() {
        Board board = new Board();
        board.setBackRow(0, PlayerColour.BLACK);
        board.setBackRow(7, PlayerColour.WHITE);

        for (int col = 0; col < 8; col++) {
            board.board[1][col] = new Pawn(PlayerColour.BLACK);
            board.board[6][col] = new Pawn(PlayerColour.WHITE);
        }

        return board;
    }

    public static int getBackRowIndex(PlayerColour colour) {
        return colour == PlayerColour.WHITE ? 7 : 0;
    }

    public static Board empty() {
        return new Board();
    }

    private void setBackRow(int rowIndex, PlayerColour colour) {
        board[rowIndex][0] = new Rook(colour);
        board[rowIndex][1] = new Knight(colour);
        board[rowIndex][2] = new Bishop(colour);
        board[rowIndex][3] = new Queen(colour);
        board[rowIndex][4] = new King(colour);
        board[rowIndex][5] = new Bishop(colour);
        board[rowIndex][6] = new Knight(colour);
        board[rowIndex][7] = new Rook(colour);
    }

    public Piece get(Coordinates coords) {
        return board[coords.getRow()][coords.getCol()];
    }

    public Optional<Coordinates> findAny(Piece.PieceType pieceType, PlayerColour colour) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinates curr = new Coordinates(i, j);
                Piece currPiece = get(curr);
                if (currPiece != null && currPiece.getColour() == colour && currPiece.getType() == pieceType) {
                    return Optional.of(curr);
                }
            }
        }
        return Optional.empty();
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void move(Coordinates from, Coordinates to) {
        if (isCastleMove(from, to)) {
            castle(from, to);
        } else {
            board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
            board[from.getRow()][from.getCol()] = null;
        }

        lastMove = new Move(from, to);
        get(to).setMoved();
    }

    public boolean isSquareUnderThreat(Coordinates square, PlayerColour colour) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Coordinates curr = new Coordinates(i, j);
                Piece currPiece = get(curr);
                if (currPiece != null && currPiece.getColour() != colour &&
                        currPiece.getAllowedMoves(curr, this)
                                .stream()
                                .map(Move::getTo)
                                .anyMatch(square::equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCastleMove(Coordinates from, Coordinates to) {
        return get(from).getType() == Piece.PieceType.KING && Math.abs(from.getCol() - to.getCol()) > 1;
    }

    private void castle(Coordinates from, Coordinates to) {
        int direction = from.getCol() - to.getCol() > 0 ? -1 : 1;
        int rookCol = direction > 0 ? 7 : 0;
        move(new Coordinates(from.getRow(), rookCol), from.plus(0, direction));

        board[to.getRow()][to.getCol()] = board[from.getRow()][from.getCol()];
        board[from.getRow()][from.getCol()] = null;
    }

    public void placePiece(Coordinates coords, Piece piece) {
        board[coords.getRow()][coords.getCol()] = piece;
    }

    public boolean isEmptyOrCapturable(Coordinates coords, PlayerColour ownColour) {
        return isEmpty(coords) || isCapturable(coords, ownColour);
    }

    public boolean isEmpty(Coordinates coords) {
        return get(coords) == null;
    }

    public boolean isCapturable(Coordinates coords, PlayerColour ownColour) {
        return !isEmpty(coords) && get(coords).getColour() != ownColour;
    }

    public boolean hasLastMove() {
        return lastMove != null;
    }

    public boolean hasNoMoves(PlayerColour colour) {
        int numMoves = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8;j++){
                Coordinates curr = new Coordinates(i, j);
                Piece currPiece = get(curr);
                if (currPiece != null && currPiece.getColour() == colour) {
                    numMoves += currPiece.getAllowedMoves(curr, this).size();
                }
            }
        }
        return numMoves == 0;
    }
}
