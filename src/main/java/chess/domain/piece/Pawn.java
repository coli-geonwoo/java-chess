package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Direction;
import chess.domain.position.DirectionJudge;
import chess.domain.position.Position;

import java.util.List;

import static chess.domain.piece.Team.BLACK;
import static chess.domain.piece.Team.WHITE;
import static chess.domain.position.Direction.*;

public class Pawn extends Piece {
    public static final int KILL_PASSING_DISTANCE = 2;
    public static final int FORWARDING_SQUARED_DISTANCE = 1;
    public static final int FIRST_FORWADING_SQUARED_DISTANCE = 4;
    private static final List<Direction> KILL_PASSING = List.of(NW, NE, SW, SE);

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Position start, Position destination, ChessBoard board) {
        //TODO: 조건식 가독성 있게 추상화 및 매직넘버 상수화
        return isFowardPassing(start, destination, board)
                || isFirstFowardPassing(start, destination, board)
                || isKillPassing(start, destination, board);
    }

    private boolean isFowardPassing(Position start, Position destination, ChessBoard board) {
        return isForward(start, destination)
                && start.squaredDistanceWith(destination) == FORWARDING_SQUARED_DISTANCE
                && board.positionIsEmpty(destination);
    }

    private boolean isFirstFowardPassing(Position start, Position destination, ChessBoard board) {
        return isForward(start, destination)
                && start.squaredDistanceWith(destination) == FIRST_FORWADING_SQUARED_DISTANCE
                && isInitialPawnRow(start)
                && board.pathIsAllEmpty(start.findPath(destination));
    }

    //TODO: 테스트 구현하기
    private boolean isKillPassing(Position start, Position destination, ChessBoard board) {
        return isForward(start, destination)
                && KILL_PASSING.contains(DirectionJudge.judge(start, destination))
                && start.squaredDistanceWith(destination) == KILL_PASSING_DISTANCE
                && board.piecesIsOtherTeam(start, destination);
    }

    private boolean isInitialPawnRow(Position start) {
        if (isBlackTeam()) {
            return start.rowIs(BLACK.getInitialPawnRow());
        }
        return start.rowIs(WHITE.getInitialPawnRow());
    }

    private boolean isForward(Position start, Position destination) {
        if (isBlackTeam()) {
            return BLACK.getDirection() == DirectionJudge.judge(start, destination);
        }
        return WHITE.getDirection() == DirectionJudge.judge(start, destination);
    }
}
