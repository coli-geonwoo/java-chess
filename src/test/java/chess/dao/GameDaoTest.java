package chess.dao;

import chess.domain.game.ChessGame;
import chess.view.OutputView;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static chess.domain.position.Fixture.*;
import static chess.domain.position.Fixture.G5;
import static org.assertj.core.api.Assertions.assertThat;

class GameDaoTest {
    private final GameDao gameDao = new GameDao();

    @Test
    public void connection() {
        try (final var connection = gameDao.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveGame() {
        final ChessGame game = ChessGame.newGame();
        gameDao.saveGame(game);
    }

    @Test
    void findGame() {
        ChessGame game1 = ChessGame.newGame();
        game1.playTurn(D2, D4);
        game1.playTurn(E7, E6);
        game1.playTurn(C1, G5);
        gameDao.saveGame(game1);
        OutputView outputView = new OutputView();
        outputView.printChessBoardMessage(game1.getBoard());
        System.out.println();
        outputView.printChessBoardMessage(gameDao.findGame().getBoard());
    }

}
