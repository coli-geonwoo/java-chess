package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ChessBoard {
    private final Map<Position, Piece> board;

    public ChessBoard(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean positionIsEmpty(Position position) {
        return !board.containsKey(position);
    }

    public Piece findPieceByPosition(Position targetPosition) {
        if (positionIsEmpty(targetPosition)) {
            throw new NoSuchElementException("해당 위치에 기물이 존재하지 않습니다.");
        }
        return board.get(targetPosition);
    }

    public void move(Position start, Position destination) {
        if (canMove(start, destination)) {
            movePiece(start, destination);
            return;
        }
        throw new IllegalArgumentException();
    }

    private void movePiece(Position start, Position destination) {
        Piece piece = findPieceByPosition(start);
        board.remove(start);
        board.put(destination, piece);
    }

    public boolean canMove(Position start, Position destination) {
        Piece piece = findPieceByPosition(start);

        return piece.canMove(start, destination, this) && isMovablePosition(start, destination);
    }

    public boolean pathIsAllEmpty(List<Position> path) {
        return path.stream()
                .allMatch(this::positionIsEmpty);
    }

    public boolean piecesIsOtherTeam(Position start, Position destination) {
        if(positionIsEmpty(start) || positionIsEmpty(destination)){
            return false;
        }

        Piece startPiece = findPieceByPosition(start);
        Piece desinationPiece = findPieceByPosition(destination);
        return startPiece.isOtherTeam(desinationPiece);
    }

    private boolean isMovablePosition(Position start, Position destination) {
        return positionIsEmpty(destination) || piecesIsOtherTeam(start, destination);
    }
}
