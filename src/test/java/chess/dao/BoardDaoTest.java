package chess.dao;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import org.junit.jupiter.api.Test;

class BoardDaoTest {
    BoardDao boardDao=  BoardDao.of();

    @Test
    void resetBoard() {
        boardDao.resetBoard();
    }

    @Test
    void saveBoard() {
        ChessBoard board = ChessBoardCreator.normalGameCreator().create();
        boardDao.saveBoard(board);
    }

    @Test
    void findBoard() {
    }
}
