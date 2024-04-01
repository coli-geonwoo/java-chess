package chess.dao;

import chess.database.ConnectionGenerator;
import chess.domain.game.ChessGame;
import chess.domain.piece.Team;

import java.sql.SQLException;

public class TurnDao {

    private final ConnectionGenerator connectionGenerator;

    private TurnDao(ConnectionGenerator connectionGenerator) {
        initializeTurn();
        this.connectionGenerator = connectionGenerator;
    }

    public static TurnDao of (){
        return new TurnDao(new ConnectionGenerator());
    }

    public void saveTurn(ChessGame game) {
        final var query = "UPDATE turn SET team = ?;";
        try (final var connection = connectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, TeamMapper.messageOf(game.getTurn()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("턴 저장 기능 오류");
        }
    }

    public Team findTurn() {
        final var query = "SELECT team FROM turn";
        try (final var connection = connectionGenerator.getConnection();
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

    private void initializeTurn(){
        final var query = "INSERT INTO 'turn'(team) VALUE ('white');";
        try (final var connection = connectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("턴 초기화 오류");
        }
    }
}
