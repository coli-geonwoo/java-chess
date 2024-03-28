package chess.dao;

import chess.domain.game.ChessGame;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
        ChessGame game = gameDao.findGame();
    }

}
