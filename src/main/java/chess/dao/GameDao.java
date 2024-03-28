package chess.dao;

import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class GameDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

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
        resetBoard();

        if (game.isEndGame()) {
            return;
        }
        saveBoard(game.getBoard());
        saveTurn(game);
    }

    public ChessGame findGame() {
        if(canFindRunningGame()){
            return ChessGame.runningGame(findBoard(), findTurn());
        }

        return ChessGame.newGame();
    }

    private boolean canFindRunningGame(){
        final var query = "SELECT * FROM board WHERE distinct_piece = 1;";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetBoard() {
        final var query = "UPDATE board SET distinct_piece = 0;";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveBoard(ChessBoard board) {
        Map<Position, Piece> boardStatus = board.status();
        for (Map.Entry<Position, Piece> entry : boardStatus.entrySet()) {
            updatePosition(entry.getKey(), entry.getValue());
        }
    }

    private void saveTurn(ChessGame game) {
        final var query = "UPDATE turn SET team = ?;";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, TeamMapper.messageOf(game.getTurn()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePosition(final Position position, Piece piece) {
        final var query = "UPDATE board SET distinct_piece = 1, piece_type = ? , team = ? WHERE position = ?;";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceMapper.typeMessageOf(piece));
            preparedStatement.setString(2, TeamMapper.messageOf(piece.getTeam()));
            preparedStatement.setString(3, Position.toKey(position.getRowPosition(), position.getColumnPosition()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 보드 역직렬화
    private ChessBoard findBoard() {
        final var query = "SELECT * FROM board WHERE distinct_piece = 1";
        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            Map<Position, Piece> board = new HashMap<>();
            if (resultSet.next()) {
                String position = resultSet.getString("position");
                Position p = Position.of(position.charAt(0) - '0', position.charAt(1) - '0');
                String piece_type = resultSet.getString("piece_type");
                Team team = TeamMapper.findTeam(resultSet.getString("team"));
                Piece piece = PieceMapper.findPieceByType(piece_type, team);
                board.put(p, piece);
            }
            return ChessBoard.normalBoard(board);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 턴 역직렬화
    private Team findTurn() {
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
            throw new RuntimeException(e);
        }
    }
}
