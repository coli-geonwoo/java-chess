package chess.domain.board;

import chess.domain.piece.NullPiece;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;

import java.util.Map;

public class ChessBoard {
    private final Map<Position, Piece> board;
    private final ScoreCalculator calculator;

    public ChessBoard(Map<Position, Piece> board, ScoreCalculator calculator) {
        this.board = board;
        this.calculator = calculator;
    }

    public static ChessBoard normalBoard(Map<Position, Piece> board) {
        return new ChessBoard(board, ScoreCalculator.gameScoreCalculator());
    }

    public boolean positionIsEmpty(Position position) {
        return !board.containsKey(position);
    }

    public Piece findPieceByPosition(Position targetPosition) {
        if (positionIsEmpty(targetPosition)) {
            return NullPiece.getInstance();
        }
        return board.get(targetPosition);
    }

    public void move(Position start, Position destination) {
        if (canMove(start, destination)) {
            movePiece(start, destination);
            return;
        }
        throw new IllegalArgumentException("잘못된 움직임 명령입니다.");
    }

    public Score teamScore(Team team) {
        return calculator.calculateTeamScore(board, team);
    }

    public boolean isKingAlive(Team team) {
        return board.values()
                .stream()
                .anyMatch(piece -> piece.isKing() && piece.isSameTeam(team));
    }

    private void movePiece(Position start, Position destination) {
        Piece piece = findPieceByPosition(start);
        board.remove(start);
        board.put(destination, piece);
    }

    private boolean canMove(Position start, Position destination) {
        Piece pieceAtStart = findPieceByPosition(start);
        return pieceAtStart.canMove(start, destination, findPieceByPosition(destination))
                && pathIsAllEmpty(start, destination);
    }

    private boolean pathIsAllEmpty(Position start, Position destination) {
        return start.findPath(destination)
                .stream()
                .allMatch(this::positionIsEmpty);
    }

    public Map<Position, Piece> status() {
        return board;
    }
}
