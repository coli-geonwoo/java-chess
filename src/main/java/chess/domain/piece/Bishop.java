package chess.domain.piece;

import chess.domain.board.ChessBoard;
import chess.domain.position.Direction;
import chess.domain.position.DirectionJudge;
import chess.domain.position.Position;

import java.util.List;

import static chess.domain.position.Direction.*;

public class Bishop extends Piece {
    private static final List<Direction> PASSING = List.of(NW, NE, SW, SE);

    public Bishop(Team team) {
        super(team);
    }

    @Override
    public boolean canMove(Position start, Position destination, ChessBoard board) {
        try {
            return PASSING.contains(DirectionJudge.judge(start, destination))
                    && board.pathIsAllEmpty(start.findPath(destination));
        } catch (IllegalArgumentException e) {
            return false;
        }
        // .judge()에서 예외가 발생하는 걸로 보이는데, 정말 예외였나요? Effective Java item.69: 예외는 진짜 예외 상황에만 사용하라
    }
}
