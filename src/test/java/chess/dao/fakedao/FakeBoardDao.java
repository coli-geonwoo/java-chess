package chess.dao.fakedao;

import chess.dao.BoardDao;
import chess.domain.board.ChessBoard;
import chess.domain.board.ScoreCalculator;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

import java.util.HashMap;
import java.util.Map;

public class FakeBoardDao implements BoardDao {
    private ChessBoard board;

    public FakeBoardDao(ChessBoard board) {
        this.board = board;
    }

    @Override
    public void saveBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard findBoard() {
        return board;
    }

    @Override
    public void updatePiecePosition(Position position, Piece piece) {
        Map<Position, Piece> piecePositions = board.status();
        piecePositions.put(position, piece);
        this.board = this.board = new ChessBoard(piecePositions, ScoreCalculator.gameScoreCalculator());
    }

    @Override
    public void updateEmptyPosition(Position position) {
        Map<Position, Piece> piecePositons = board.status();
        piecePositons.remove(position);
        this.board = new ChessBoard(piecePositons, ScoreCalculator.gameScoreCalculator());
    }

    @Override
    public void resetBoard() {
        this.board = new ChessBoard(new HashMap<>(), ScoreCalculator.gameScoreCalculator());
    }
}
