package chess.dao;

import chess.domain.game.ChessGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class GameDao {
    private final ConnectionGenerator connectionGenerator;
    private final BoardDao boardDao;
    private final TurnDao turnDao;


    public GameDao(ConnectionGenerator connectionGenerator, BoardDao boardDao, TurnDao turnDao) {
        this.connectionGenerator = connectionGenerator;
        this.boardDao = boardDao;
        this.turnDao = turnDao;
    }

    public static GameDao of() {
        return new GameDao(new ConnectionGenerator(), BoardDao.of(), new TurnDao());
    }

    public void saveGame(ChessGame game) {
        boardDao.resetBoard();

        if (game.isEndGame()) {
            return;
        }
        boardDao.saveBoard(game.getBoard());
        turnDao.saveTurn(game);
    }

    public ChessGame findGame() {
        if (canFindRunningGame()) {
            return ChessGame.runningGame(boardDao.findBoard(), turnDao.findTurn());
        }
        return ChessGame.newGame();
    }

    private boolean canFindRunningGame() {
        final var query = "SELECT * FROM board WHERE distinct_piece = 1;";
        try (final Connection connection = connectionGenerator.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException("현재 실행중인 게임 조회과정에 오류가 생겼습니다.");
        }
    }
}
