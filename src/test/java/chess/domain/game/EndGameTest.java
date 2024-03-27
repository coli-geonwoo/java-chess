package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.piece.King;
import chess.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static chess.domain.position.Fixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EndGameTest {

    @DisplayName("끝난 경기에서는 턴을 진행할 수 없다")
    @Test
    void should_ThrowUnsupportedOpperationException_When_CallPlayTurnMehtod() {
        EndGame endGame = new EndGame(ChessBoard.normalBoard(new HashMap<>()));

        assertThatThrownBy(() -> endGame.playTurn(A1, A2))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("이 경기는 이미 끝난 경기입니다.");
    }

    @DisplayName("끝난 경기에서는 킹이 살아있는 팀을 승리팀으로 반환한다")
    @Test
    void should_ReturnWinTeamWhichKingIsAlive(){
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, new King(Team.BLACK))));
        ChessGame endGame = new EndGame(board);

        assertThat(endGame.winTeam()).isEqualTo(Team.BLACK);
    }
}
