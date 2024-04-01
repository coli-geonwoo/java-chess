package chess.domain.game;

import chess.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.domain.position.Fixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChessGameTest {
    /* white turn
   RNBQKBNR  8 (rank 8)
   PPPPPPPP  7
   ........  6
   ........  5
   ........  4
   .....n..  3
   pppppppp  2
   rnbqkb.r  1 (rank 1)

   abcdefgh
    */
    @DisplayName("턴을 진행하는 팀의 기물을 움직일 수 있다")
    @Test
    void should_CanMovePiece_When_PieceBelongTurnTeam() {
        ChessGame game = ChessGame.newGame();
        assertThatCode(() -> game.playTurn(G1, F3)).doesNotThrowAnyException();
    }

    /* white turn
    R.BQKBNR  8 (rank 8)
    PPPPPPPP  7
    ..N.....  6
    ........  5
    ........  4
    ........  3
    pppppppp  2
    rnbqkbnr  1 (rank 1)

    abcdefgh
     */
    @DisplayName("턴이 아닌 팀의 기물을 움직일 수 없다")
    @Test
    void should_ThrowIllegalArgumentException_When_MovePiece_Which_NotBelongTurnTeam() {
        ChessGame game = ChessGame.newGame();
        assertThatThrownBy(() -> game.playTurn(B8, C6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 턴을 진행하는 팀의 기물이 아닙니다.");
    }

    /*
    RNBQKBNR  8 (rank 8)
    PPPPPPPP  7
    ........  6
    ....p...  5
    ........  4
    ........  3
    ppp.pppp  2
    rnbqkbnr  1 (rank 1)

    abcdefgh
     */
    @DisplayName("진행 중인 게임에서 무승부인 상황에서는 NONE팀을 반환한다")
    @Test
    void should_ReturnNONETeam_When_ScoreIsDraw() {
        ChessGame game = ChessGame.newGame();
        game.playTurn(D2, D4);
        game.playTurn(E7, E5);
        game.playTurn(D4, E5);

        assertThat(game.winTeam()).isEqualTo(Team.NONE);
    }

    /* whiteBishop이 blackQueen을 먹은 상황
    RNBbKBNR  8 (rank 8)
    PPPP*PPP  7
    .....*..  6
    ....P.b.  5
    ...p.*..  4
    ....*...  3
    ppp*pppp  2
    rnbqkbnr  1 (rank 1)

    abcdefgh
     */
    @DisplayName("진행 중인 게임에서는 점수가 높은 팀을 승리팀으로 반환한다")
    @Test
    void should_ReturnWinTeamWhichScoreIsHigher_When_RunningGame() {
        ChessGame game = ChessGame.newGame();
        game.playTurn(D2, D4);
        game.playTurn(E7, E6);
        game.playTurn(C1, G5);
        game.playTurn(E6, E5);
        game.playTurn(G5, D8);

        assertThat(game.winTeam()).isEqualTo(Team.WHITE);
    }

    @DisplayName("끝난 게임에서는 킹이 살아있는 팀을 승리팀으로 반환한다")
    @Test
    void should_ReturnWinTeamWhichKingIsAlive_When_EndGame() {
        ChessGame game = ChessGame.newGame();
        game.playTurn(E2, E3);
        game.playTurn(F7, F6);
        game.playTurn(D1, H5);
        game.playTurn(G8, H6);
        game.playTurn(H5, E8);

        assertAll(
                () -> assertThat(game.isEndGame()).isTrue(),
                () -> assertThat(game.winTeam()).isEqualTo(Team.WHITE)
        );
    }
}
