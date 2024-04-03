package chess.service;

import chess.dao.fakedao.FakeBoardDao;
import chess.dao.fakedao.FakeTurnDao;
import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.piece.NullPiece;
import chess.domain.piece.Piece;
import chess.domain.piece.WhitePawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.domain.position.Fixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChessGameServiceTest {
    private ChessGame game;
    private ChessGameService service;

    @BeforeEach
    void setUp() {
        game = ChessGame.newGame();
        service = new ChessGameService(new FakeBoardDao(game.getBoard()), new FakeTurnDao(game.getTurn()), game);
    }

    @DisplayName("기물 이동을 통해 턴을 진행할 수 있다")
    @Test
    void should_PlayTurn_When_GiveStartAndDestination() {
        service.playTurn(D2, D4);
        ChessBoard gameBoard = service.gameBoard();
        Piece startPositionPiece = gameBoard.findPieceByPosition(D2);
        Piece destinationPositionPiece = gameBoard.findPieceByPosition(D4);

        assertAll(
                () -> assertThat(startPositionPiece.getClass()).isEqualTo(NullPiece.class),
                () -> assertThat(destinationPositionPiece.getClass()).isEqualTo(WhitePawn.class)
        );
    }

    @DisplayName("게임이 끝났는지 알 수 있다")
    @Test
    void should_NoticeEndGame() {
        service.playTurn(E2, E3);
        service.playTurn(F7, F6);
        service.playTurn(D1, H5);
        service.playTurn(G8, H6);
        service.playTurn(H5, E8);

        assertThat(service.isEndGame()).isTrue();
    }

    @DisplayName("게임이 끝났다면 새 게임을 저장한다")
    @Test
    void should_SaveNewGame_When_IsEndGame() {
        service.playTurn(E2, E3);
        service.playTurn(F7, F6);
        service.playTurn(D1, H5);
        service.playTurn(G8, H6);
        service.playTurn(H5, E8); // 경기 종료
        service.end();

        service = ChessGameService.of(new FakeBoardDao(game.getBoard()), new FakeTurnDao(game.getTurn()));

        assertThat(service.isEndGame()).isFalse();
    }

    @DisplayName("게임이 끝나지 않았다면 기존 게임을 저장한다")
    @Test
    void should_SavePreviousGame_When_IsNotEndGame() {
        service.playTurn(E2, E3);
        service.playTurn(F7, F6);

        service.end();
        service = ChessGameService.of(new FakeBoardDao(game.getBoard()), new FakeTurnDao(game.getTurn()));
        ChessBoard actualBoard = service.getGame().getBoard();

        assertThat(actualBoard).isEqualTo(game.getBoard());
    }
}
