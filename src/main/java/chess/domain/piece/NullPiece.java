package chess.domain.piece;

import chess.domain.board.Score;
import chess.domain.position.Position;

public final class NullPiece extends Piece {
    private static final NullPiece instance = new NullPiece(Team.NONE);

    private NullPiece(Team team) {
        super(team, Score.from(0));
    }

    public static NullPiece getInstance() {
        return instance;
    }

    @Override
    public boolean canMove(Position start, Position destination, Piece destinationPiece) {
        throw new UnsupportedOperationException("NullPiece 객체입니다");
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Score getScore() {
        throw new UnsupportedOperationException("NullPiece 객체입니다");
    }
}
