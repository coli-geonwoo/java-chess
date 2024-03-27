package chess.domain.piece;

import chess.domain.position.Position;

public class King extends Piece {
    private static final int STRAIGHT_DISTANCE = 1;
    private static final int DIAGONAL_DISTANCE = 2;
    private static final double score = 0;

    public King(Team team) {
        super(team, score);
    }

    @Override
    public boolean canMove(Position start, Position destination, Piece pieceAtDestination) {
        int squaredDistance = start.squaredDistanceWith(destination);
        return squaredDistance == STRAIGHT_DISTANCE || squaredDistance == DIAGONAL_DISTANCE;
    }
}
