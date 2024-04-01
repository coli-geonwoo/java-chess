package chess.dao;

import chess.database.ConnectionGenerator;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BoardDao {
    // TODO :초기 보드 초기화 기능 추가
    private final ConnectionGenerator connectionGenerator;

    public BoardDao(ConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    public static BoardDao of() {
        return new BoardDao(new ConnectionGenerator());
    }

    public void saveBoard(ChessBoard board) {
        Map<Position, Piece> boardStatus = board.status();
        for (Map.Entry<Position, Piece> entry : boardStatus.entrySet()) {
            updatePiecePosition(entry.getKey(), entry.getValue());
        }
    }

    public ChessBoard findBoard() {
        final var query = "SELECT * FROM board WHERE distinct_piece = 1";
        try (final Connection connection = connectionGenerator.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query);
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            Map<Position, Piece> board = new HashMap<>();
            while (resultSet.next()) {
                Position p = convertMessageToPosition(resultSet.getString("position"));
                Piece piece = convertMessageToPiece(resultSet.getString("piece_type"), resultSet.getString("team"));
                board.put(p, piece);
            }
            return ChessBoard.normalBoard(board);
        } catch (final SQLException e) {
            throw new RuntimeException("보드 가져오기 기능 오류");
        }
    }

    public void updatePiecePosition(final Position position, Piece piece) {
        final var query = "UPDATE board SET distinct_piece = 1, piece_type = ? , team = ? WHERE position = ?;";
        try (final var connection = connectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, PieceMapper.typeMessageOf(piece));
            preparedStatement.setString(2, TeamMapper.messageOf(piece.getTeam()));
            preparedStatement.setString(3, Position.toKey(position.getRowPosition(), position.getColumnPosition()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("기물 위치 업데이트 기능 오류");
        }
    }

    public void updateEmptyPosition(final Position position) {
        final var query = "UPDATE board SET distinct_piece = 0, piece_type = null , team = null WHERE position = ?;";
        try (final var connection = connectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, Position.toKey(position.getRowPosition(), position.getColumnPosition()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("기물 위치 업데이트 기능 오류");
        }
    }

    public void resetBoard() {
        final var query = "UPDATE board SET distinct_piece = 0, piece_type =null, team = null;";
        try (final var connection = connectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("보드 리셋 오류");
        }
    }

    private Position convertMessageToPosition(String position) {
        return Position.of(position.charAt(0) - '0', position.charAt(1) - '0');
    }

    private Piece convertMessageToPiece(String pieceType, String team) {
        Team foundTeam = TeamMapper.findTeam(team);
        return PieceMapper.findPieceByType(pieceType, foundTeam);
    }
}
