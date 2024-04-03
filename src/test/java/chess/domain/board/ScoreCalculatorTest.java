package chess.domain.board;

import chess.domain.piece.*;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static chess.domain.position.Fixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ScoreCalculatorTest {
    /*
   ........  8
   ...q....  7
   ........  6
   .....B..  5
   ........  4
   ...R....  3
   ........  2
   ........  1
   abcdefgh
   */
    @DisplayName("각 팀의 점수 합을 계산할 수 있다.")
    @Test
    void should_CalculateTeamScore() {
        Map<Position, Piece> board = Map.ofEntries(
                Map.entry(D3, new Rook(Team.BLACK)),
                Map.entry(F5, new Bishop(Team.BLACK)),
                Map.entry(D7, new Queen(Team.WHITE)));
        ScoreCalculator calculator = ScoreCalculator.gameScoreCalculator();

        Score blackTeamScore = calculator.calculateTeamScore(board, Team.BLACK);
        Score whiteTeamScore = calculator.calculateTeamScore(board, Team.WHITE);

        assertAll(
                () -> assertThat(blackTeamScore).isEqualTo(Score.from(8)),
                () -> assertThat(whiteTeamScore).isEqualTo(Score.from(9))
        );
    }

    /*
    ........  8
    ........  7
    ........  6
    ........  5
    ........  4
    ........  3
    ........  2
    ........  1
    abcdefgh
    */
    @DisplayName("팀의 기물이 없을 경우 오류를 발생시킨다.")
    @Test
    void should_ThrowIllegalStateException_When_NoOtherTeamPiecesInBoard() {
        Map<Position, Piece> board = new HashMap<>();
        ScoreCalculator calculator = ScoreCalculator.gameScoreCalculator();

        Score blackTeamScore = calculator.calculateTeamScore(board, Team.BLACK);

        assertThat(blackTeamScore).isEqualTo(Score.from(0));
    }

    /*
    P.......  8
    .P......  7
    ........  6
    ........  5
    ........  4
    ........  3
    ........  2
    ........  1
    abcdefgh
    */
    @DisplayName("열에 폰이 하나만 있다면 1점으로 계산한다.")
    @Test
    void should_CountOneScoreByOnePawn_When_OnlyOnePawnInColumn() {
        Map<Position, Piece> board = Map.ofEntries(
                Map.entry(A8, Pawn.of(Team.BLACK)),
                Map.entry(B7, Pawn.of(Team.BLACK)));
        ScoreCalculator calculator = ScoreCalculator.gameScoreCalculator();

        Score blackTeamScore = calculator.calculateTeamScore(board, Team.BLACK);

        assertThat(blackTeamScore).isEqualTo(Score.from(2));
    }

    /*
    P.......  8
    P.......  7
    ........  6
    ........  5
    .....P..  4
    ........  3
    ........  2
    ........  1
    abcdefgh
    */
    @DisplayName("열에 같은 팀 폰이 한 개 이상 있다면 0.5점으로 계산한다.")
    @Test
    void should_CountPointFiveScoreByOnePawn_When_MoreThanOnePawnInColumn() {
        Map<Position, Piece> board = Map.ofEntries(
                Map.entry(A8, Pawn.of(Team.BLACK)),
                Map.entry(A7, Pawn.of(Team.BLACK)),
                Map.entry(F4, Pawn.of(Team.BLACK)));
        ScoreCalculator calculator = ScoreCalculator.gameScoreCalculator();

        Score blackTeamScore = calculator.calculateTeamScore(board, Team.BLACK);

        assertThat(blackTeamScore).isEqualTo(Score.from(2));
    }
}
