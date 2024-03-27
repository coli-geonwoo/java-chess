package chess.domain.game;

import chess.domain.board.ChessBoard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static chess.domain.position.Fixture.A1;
import static chess.domain.position.Fixture.A2;
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


}
