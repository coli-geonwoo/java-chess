package chess.domain.piece;

import chess.domain.position.Direction;
import chess.domain.position.DirectionJudge;
import chess.domain.position.Position;

import java.util.List;

import static chess.domain.position.Direction.*;

public class Queen extends Piece {
    private static final List<Direction> PASSING = List.of(UP, DOWN, RIGHT, LEFT, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT);
    private static final double score = 9;

    public Queen(Team team) {
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
