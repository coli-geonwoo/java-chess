package chess.dao;

import chess.domain.game.ChessGame;
import chess.domain.piece.Team;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TurnDao {

    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public void saveTurn(ChessGame game) {
        final var query = "UPDATE turn SET team = ?;";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, TeamMapper.messageOf(game.getTurn()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("턴 저장 기능 오류");
        }
    }

    public Team findTurn() {
        final var query = "SELECT team FROM turn";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String turnTeam = resultSet.getString("team");
                return TeamMapper.findTeam(turnTeam);
            }
            throw new SQLException("턴 테이블에 팀이 없습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException("턴 조회 기능 오류");
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
