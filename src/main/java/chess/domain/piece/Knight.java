package chess.domain.piece;

import chess.domain.board.Score;
import chess.domain.position.Position;

public class Knight extends Piece {
    private static final int L_SHAPE_DISTANCE = 5;
    private static final Score score = Score.from(2.5);


    public Knight(Team team) {
        super(team, score);
    }

    @Override
    public boolean canMove(Position start, Position destination, Piece pieceAtDestination) {
        return start.squaredDistanceWith(destination) == L_SHAPE_DISTANCE;
    }
}
