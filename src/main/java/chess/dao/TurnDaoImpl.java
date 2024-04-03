package chess.dao;

import chess.database.ConnectionPool;
import chess.domain.game.ChessGame;
import chess.domain.piece.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDaoImpl implements TurnDao {

    private final ConnectionPool connectionPool;

    private TurnDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        if (isFirstCall()) {
            initializeTurn();
        }
    }

    public static TurnDaoImpl of() {
        return new TurnDaoImpl(new ConnectionPool());
    }

    @Override
    public void saveTurn(ChessGame game) {
        final var query = "UPDATE turn SET team = ?;";
        try (final var connection = connectionPool.getConnection(0);
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, TeamMapper.messageOf(game.getTurn()));
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (final SQLException e) {
            throw new RuntimeException("턴 저장 기능 오류");
        }
    }

    @Override
    public Team findTurn() {
        final var query = "SELECT team FROM turn";
        try (final var connection = connectionPool.getConnection(0);
             final var preparedStatement = connection.prepareStatement(query);
             final var resultSet = preparedStatement.executeQuery()) {
            connectionPool.returnConnection(connection);

            if (resultSet.next()) {
                String turnTeam = resultSet.getString("team");
                return TeamMapper.findTeam(turnTeam);
            }
            throw new SQLException("턴 테이블에 팀이 없습니다.");
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("턴 조회 기능 오류");
        }
    }

    private boolean isFirstCall() {
        final var query = "SELECT * FROM turn";
        try (Connection connection = connectionPool.getConnection(0);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            connectionPool.returnConnection(connection);
            return !resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException("Turn table 조회 기능 오류");
        }
    }


    private void initializeTurn() {
        final var query = "INSERT INTO turn(team) VALUE ('white');";
        try (final var connection = connectionPool.getConnection(0);
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("턴 초기화 오류");
        }
    }
}
