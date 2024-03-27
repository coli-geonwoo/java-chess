package chess.domain.piece;

public enum Team {
    WHITE,
    BLACK,
    NONE,
    ;

    public Team opposite() {
        if (this == WHITE) {
            return BLACK;
        }

        if (this == BLACK) {
            return WHITE;
        }
        return NONE;
    }
}
