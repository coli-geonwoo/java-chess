package chess.domain.piece;

import chess.domain.position.Direction;
import chess.domain.position.DirectionJudge;
import chess.domain.position.Position;

import java.util.List;

import static chess.domain.position.Direction.*;

public class Rook extends Piece {
    private static final List<Direction> PASSING = List.of(N, S, E, W);

    public Rook(Team team) {
        super(team);
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
