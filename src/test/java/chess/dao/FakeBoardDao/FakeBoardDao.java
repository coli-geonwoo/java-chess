package chess.dao.FakeBoardDao;

import chess.dao.PieceMapper;
import chess.dao.TeamMapper;
import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.ColumnPosition;
import chess.domain.position.Position;
import chess.domain.position.RowPosition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeBoardDao {
    private List<Square> squares;

    public FakeBoardDao(List<Square> squares) {
        this.squares = squares;
    }

    public static FakeBoardDao normalBoard() {
        return new FakeBoardDao(initailaizeBoard());
    }

    public static FakeBoardDao runningBoard(Map<Position, Piece> status) {
        return new FakeBoardDao(convertMapToSqaures(status));
    }

    private static List<Square> initailaizeBoard() {
        List<Square> board = new ArrayList<>();
        for (int row = RowPosition.MIN_NUMBER; row <= RowPosition.MAX_NUMBER; row++) {
            for (int col = ColumnPosition.MIN_NUMBER; col <= ColumnPosition.MAX_NUMBER; col++) {
                board.add(new Square(String.valueOf(row) + col));
            }
        }
        return board;
    }

    private static List<Square> convertMapToSqaures(Map<Position, Piece> status) {
        List<Square> squares = new ArrayList<>();
        for (Map.Entry<Position, Piece> pieceEntry : status.entrySet()) {
            Position p = pieceEntry.getKey();
            Square square = new Square(Position.toKey(p.getRowPosition(), p.getColumnPosition()));
            square.setPieceType(PieceMapper.typeMessageOf(pieceEntry.getValue()));
            Team team = pieceEntry.getValue().getTeam();
            square.setTeam(TeamMapper.messageOf(team));
            square.setDistinct(true);
            squares.add(square);
        }
        return squares;
    }

    public void saveBoard(ChessBoard board) {
        this.squares = convertMapToSqaures(board.status());
    }

    public ChessBoard findBoard() {
        List<Square> squaresWithPiece = squares.stream()
                .filter(s -> !s.isEmpty())
                .toList();
        return ChessBoard.normalBoard(mapSqauresToStatus(squaresWithPiece));
    }

    public void updatePiecePosition(final Position position, Piece piece) {
        Square sqaure = findSquareByPosition(position);
        sqaure.setDistinct(true);
        sqaure.setPieceType(PieceMapper.typeMessageOf(piece));
        sqaure.setTeam(TeamMapper.messageOf(piece.getTeam()));
    }

    public void updateEmptyPosition(final Position position) {
        Square square = findSquareByPosition(position);
        square.setDistinct(false);
        square.setTeam(null);
        square.setPieceType(null);
    }

    public void resetBoard() {
        for (Square square : squares) {
            square.setDistinct(false);
            square.setTeam(null);
            square.setPieceType(null);
        }
    }

    private Square findSquareByPosition(Position position) {
        String p = convertPositionToMessage(position);
        return squares.stream()
                .filter(s -> s.getPosition().equals(p))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("일치하는 Entity가 존재하지 않습니다."));
    }

    private String convertPositionToMessage(Position position) {
        return Position.toKey(position.getRowPosition(), position.getColumnPosition());
    }

    private Position convertMessageToPosition(String position) {
        return Position.of(position.charAt(0) - '0', position.charAt(1) - '0');
    }

    private Map<Position, Piece> mapSqauresToStatus(List<Square> squares) {
        Map<Position, Piece> piecePositions = new HashMap<>();
        for (Square square : squares) {
            Position position = convertMessageToPosition(square.getPosition());
            Team team = TeamMapper.findTeam(square.getTeam());
            Piece piece = PieceMapper.findPieceByType(square.getPiece_type(), team);
            piecePositions.put(position, piece);
        }
        return piecePositions;
    }

    public ChessBoard getBoard() {
        return findBoard();
    }
}
