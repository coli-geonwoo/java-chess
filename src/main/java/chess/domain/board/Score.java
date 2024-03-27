package chess.domain.board;

public final class Score {
    private final double score;

    private Score(double score) {
        this.score = score;
    }

    public static Score from(double score) {
        return new Score(score);
    }

    public Score add(Score otherScore) {
        return Score.from(score + otherScore.getScore());
    }

    public double getScore() {
        return score;
    }
}
