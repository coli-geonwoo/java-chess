package chess.dao;

import chess.domain.board.ChessBoard;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

public interface BoardDao {
    void saveBoard(ChessBoard board);

    ChessBoard findBoard();

    void updatePiecePosition(final Position position, Piece piece);

    void updateEmptyPosition(final Position position);

    void resetBoard();
}
