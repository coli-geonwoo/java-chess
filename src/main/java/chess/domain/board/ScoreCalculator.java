package chess.domain.board;

import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.ColumnPosition;
import chess.domain.position.Position;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ScoreCalculator {
    private static final int PAWN_MINUS_SCORE_THRESHOLD = 2;
    private static final Score PAWN_MINUS_SCORE = Score.from(0.5);
    private static final Score ZERO_SCORE = Score.from(0);

    private ScoreCalculator() {
    }

    public static ScoreCalculator gameScoreCalculator() {
        return new ScoreCalculator();
    }

    public Score calculateTeamScore(Map<Position, Piece> board, Team team) {
        Score totalScore = calculateTotalTeamScore(board, team);
        List<Position> pawnPositions = findTeamPawnPositions(board, team);
        return totalScore.minus(calculateMinusPawnScore(pawnPositions));
    }

    private Score calculateTotalTeamScore(Map<Position, Piece> board, Team team) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameTeam(team))
                .map(Piece::getScore)
                .reduce(Score::add)
                .orElse(ZERO_SCORE);
    }

    private List<Position> findTeamPawnPositions(Map<Position, Piece> board, Team team) {
        return board.entrySet()
                .stream()
                .filter(p -> p.getValue().isPawn())
                .filter(p -> p.getValue().isSameTeam(team))
                .map(Map.Entry::getKey)
                .toList();
    }

    private Score calculateMinusPawnScore(List<Position> pawnPositions) {
        Score result = ZERO_SCORE;

        Map<ColumnPosition, Long> group = pawnPositions.stream()
                .collect(groupingBy(Position::getColumnPosition, counting()));

        for (ColumnPosition column : ColumnPosition.POOL.values()) {
            result = result.add(oneColumnPawnScore(group.getOrDefault(column, 0L)));
        }
        return result;
    }

    private Score oneColumnPawnScore(Long oneColumnPawnCount) {
        if (oneColumnPawnCount >= PAWN_MINUS_SCORE_THRESHOLD) {
            return PAWN_MINUS_SCORE.multiply(oneColumnPawnCount);
        }
        return ZERO_SCORE;
    }
}
