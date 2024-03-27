package chess.domain.piece;

import chess.domain.board.Score;
import chess.domain.position.Direction;
import chess.domain.position.DirectionJudge;
import chess.domain.position.Position;

import java.util.List;

import static chess.domain.position.Direction.*;

public class Bishop extends Piece {
    private static final List<Direction> PASSING = List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
    private static final Score score = Score.from(3);

    public Bishop(Team team) {
        super(team, score);
    }

    @Override
    public boolean canMove(Position start, Position destination, Piece pieceAtDestination) {
        try {
            return PASSING.contains(DirectionJudge.judge(start, destination))
                    && checkDestinationPiece(pieceAtDestination);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean checkDestinationPiece(Piece pieceAtDestination) {
        return pieceAtDestination.isEmpty() || isOtherTeam(pieceAtDestination);
    }
}
