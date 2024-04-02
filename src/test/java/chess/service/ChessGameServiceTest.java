package chess.service;

import chess.domain.game.ChessGame;
import chess.view.OutputView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.domain.position.Fixture.*;
import static org.assertj.core.api.Assertions.assertThat;

class ChessGameServiceTest {

    @DisplayName("기물 이동을 통해 턴을 진행할 수 있다")
    @Test
    void should_PlayTurn_When_GiveStartAndDestination() {
        OutputView outputView = new OutputView();
        ChessGame newGame = ChessGame.newGame();
        ChessGameService service1 = new ChessGameService(new FakeBoardDao(newGame.getBoard()), new FakeTurnDao(newGame.getTurn()), newGame);
        ChessGame movedGame = ChessGame.newGame();
        movedGame.playTurn(D2, D4);

        service1.playTurn(D2, D4);
        String expectedGame = outputView.printChessBoardMessage(movedGame.getBoard());
        String actualGame = outputView.printChessBoardMessage(service1.gameBoard());

        assertThat(actualGame).isEqualTo(expectedGame);
    }

    @DisplayName("게임이 끝났는지 알 수 있다")
    @Test
    void should_NoticeEndGame(){
        ChessGame newGame = ChessGame.newGame();
        ChessGameService service = new ChessGameService(new FakeBoardDao(newGame.getBoard()), new FakeTurnDao(newGame.getTurn()), newGame);
        service.playTurn(E2, E3);
        service.playTurn(F7, F6);
        service.playTurn(D1, H5);
        service.playTurn(G8, H6);
        service.playTurn(H5, E8);

        assertThat(service.isEndGame()).isTrue();
    }

    @DisplayName("게임이 끝났다면 새 게임을 저장한다")
    @Test
    void should_SaveNewGame_When_IsEndGame(){
        OutputView outputView = new OutputView();
        ChessGame newGame = ChessGame.newGame();
        ChessGameService service = new ChessGameService(new FakeBoardDao(newGame.getBoard()), new FakeTurnDao(newGame.getTurn()), newGame);
        service.playTurn(E2, E3);
        service.playTurn(F7, F6);
        service.playTurn(D1, H5);
        service.playTurn(G8, H6);
        service.playTurn(H5, E8);
        service.end();

        ChessGameService service2 = ChessGameService.of(new FakeBoardDao(newGame.getBoard()), new FakeTurnDao(newGame.getTurn()));
        String actualGame = outputView.printChessBoardMessage(service2.gameBoard());
        String expectedGame = outputView.printChessBoardMessage(ChessGame.newGame().getBoard());

        assertThat(actualGame).isEqualTo(expectedGame);
    }

    @DisplayName("게임이 끝나지 않았다면 기존 게임을 저장한다")
    @Test
    void should_SavePreviousGame_When_IsNotEndGame(){
        OutputView outputView = new OutputView();
        ChessGame newGame = ChessGame.newGame();
        ChessGameService service = new ChessGameService(new FakeBoardDao(newGame.getBoard()), new FakeTurnDao(newGame.getTurn()), newGame);
        service.playTurn(E2, E3);
        service.playTurn(F7, F6);

        service.end();
        service = ChessGameService.of(new FakeBoardDao(newGame.getBoard()), new FakeTurnDao(newGame.getTurn()));
        String actualGame = outputView.printChessBoardMessage(service.gameBoard());
        String expectedGame = outputView.printChessBoardMessage(newGame.getBoard());

        assertThat(actualGame).isEqualTo(expectedGame);
    }
}
