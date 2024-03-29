package chess.dao;

import chess.domain.game.ChessGame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class GameDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private final BoardDao boardDao;
    private final TurnDao turnDao;


    public GameDao() {
        this.boardDao = new BoardDao();
        this.turnDao = new TurnDao();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
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
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException("현재 실행중인 게임 조회과정에 오류가 생겼습니다.");
        }
    }
}
