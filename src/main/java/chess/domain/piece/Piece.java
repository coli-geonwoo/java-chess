package chess.domain.piece;

import chess.domain.board.Score;
import chess.domain.position.Position;

public abstract class Piece {
    private final Team team;
    private final Score score;

    public Piece(Team team, Score score) {
        this.team = team;
        this.score = score;
    }

    public abstract boolean canMove(Position start, Position destination, Piece pieceAtDestination);

    public boolean isBlackTeam() {
        return team == Team.BLACK;
    }

    public boolean isOtherTeam(Piece otherPiece) {
        return this.team != otherPiece.team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isOtherTeam(Team team) {
        return !isSameTeam(team);
    }

    public boolean isEmpty() {
        return false;
    }

    public Score getScore() {
        return score;
    }

    public boolean isPawn() {
        return false;
    }

    public boolean isKing() {
        return false;
    }

    public Team getTeam() {
        return team;
    }
}
