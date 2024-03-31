package chess.dao.FakeBoardDao;

public class Square {
    private final String position;
    private boolean distinct_piece;
    private String piece_type;
    private String team;

    public Square(String position) {
        this.position = position;
        this.distinct_piece = false;
    }

    public boolean isEmpty() {
        return !distinct_piece;
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPiece_type() {
        return piece_type;
    }

    public void setPieceType(String piece_type) {
        this.piece_type = piece_type;
    }

    public void setDistinct(boolean distinct) {
        this.distinct_piece = distinct;
    }
}
