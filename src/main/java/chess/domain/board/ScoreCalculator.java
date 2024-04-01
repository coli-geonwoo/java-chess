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
    // TODO 어떤 minus Score인지 이름에서 표현
    private static final int MINUS_SCORE_THRESHOLD = 2;
    private static final Score MINUS_SCORE = Score.from(0.5);
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

    // TODO : 팀이 기물이 하나도 없다면 0점이 반환되어야 하는 것이 맞지 않은가?
    private Score calculateTotalTeamScore(Map<Position, Piece> board, Team team) {
        return board.values()
                .stream()
                .filter(piece -> piece.isSameTeam(team))
                .map(Piece::getScore)
                .reduce(Score::add)
                .orElseThrow(() -> new IllegalStateException("팀의 기물이 하나도 없습니다."));
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
        if (oneColumnPawnCount >= MINUS_SCORE_THRESHOLD) {
            return MINUS_SCORE.multiply(oneColumnPawnCount);
        }
        return ZERO_SCORE;
    }
}
