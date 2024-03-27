package chess.domain.board;

import java.util.Objects;

public final class Score {
    private final double score;

    private Score(double score) {
        this.score = score;
    }

    public static Score from(double score) {
        return new Score(score);
    }

    public Score add(Score otherScore) {
        return Score.from(this.score + otherScore.getScore());
    }

    public Score minus(Score otherScore) {
        return Score.from(this.score - otherScore.getScore());
    }

    public Score multiply(Long multiplier) {
        return Score.from(this.score * multiplier);
    }

    public double getScore() {
        return score;
    }

    public boolean isHigherThan(Score otherScore) {
        return this.score > otherScore.getScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return Double.compare(score, score1.score) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
