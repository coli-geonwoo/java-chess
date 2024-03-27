package chess.domain.piece;

import chess.domain.position.Position;

public class Knight extends Piece {
    private static final int L_SHAPE_DISTANCE = 5;
    private static final double score = 2.5;


    public Knight(Team team) {
        super(team, score);
    }

    @Override
    public boolean canMove(Position start, Position destination, Piece pieceAtDestination) {
        return start.squaredDistanceWith(destination) == L_SHAPE_DISTANCE;
    }
}
