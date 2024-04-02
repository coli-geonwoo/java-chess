package chess.dao;

import chess.dao.FakeBoardDao.FakeBoardDao;
import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import chess.domain.piece.Pawn;
import chess.domain.piece.Team;
import chess.view.OutputView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static chess.domain.position.Fixture.D3;
import static org.assertj.core.api.Assertions.assertThat;

class BoardDaoImplTest {

    @DisplayName("저장되어 있는 board를 가져올 수 잇다")
    @Test
    void should_FindBoard() {
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, Pawn.of(Team.BLACK))));
        FakeBoardDao boardDao = FakeBoardDao.runningBoard(board.status());
        OutputView outputView = new OutputView();

        String expectedBoard = outputView.printChessBoardMessage(board);
        String acutalBoard = outputView.printChessBoardMessage(boardDao.findBoard());

        assertThat(expectedBoard).isEqualTo(acutalBoard);
    }

    @DisplayName("board의 상태를 저장할 수 있다")
    @Test
    void should_SaveBoard() {
        FakeBoardDao boardDao = FakeBoardDao.normalBoard();
        ChessBoard board = ChessBoardCreator.normalGameCreator().create();
        OutputView outputView = new OutputView();
        boardDao.saveBoard(board);
        ChessBoard savedBoard = boardDao.getBoard();

        String expectedBoard = outputView.printChessBoardMessage(board);
        String acutalBoard = outputView.printChessBoardMessage(savedBoard);

        assertThat(expectedBoard).isEqualTo(acutalBoard);
    }


    @DisplayName("board의 상태를 리셋할 수 있다")
    @Test
    void should_ResetBoard() {
        ChessBoard board = ChessBoardCreator.normalGameCreator().create();
        ChessBoard emptyBoard = ChessBoard.normalBoard(new HashMap<>());
        FakeBoardDao boardDao = FakeBoardDao.runningBoard(board.status());
        OutputView outputView = new OutputView();

        boardDao.resetBoard();
        String expectedBoard = outputView.printChessBoardMessage(emptyBoard);
        String acutalBoard = outputView.printChessBoardMessage(boardDao.findBoard());

        assertThat(expectedBoard).isEqualTo(acutalBoard);
    }

    @DisplayName("board의 특정 위치에 기물 정보를 업데이트할 수 있다")
    @Test
    void should_UpdateBoardStatus_When_GivePieceAndPosition() {
        ChessBoard afterBoard = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, Pawn.of(Team.BLACK))));
        FakeBoardDao boardDao = FakeBoardDao.normalBoard();
        OutputView outputView = new OutputView();

        boardDao.updatePiecePosition(D3, Pawn.of(Team.BLACK));
        String actualBoard = outputView.printChessBoardMessage(boardDao.getBoard());
        String expectedBoard = outputView.printChessBoardMessage(afterBoard);

        assertThat(actualBoard).isEqualTo(expectedBoard);
    }

    @DisplayName("board의 특정 위치에 빈칸 정보를 업데이트할 수 있다")
    @Test
    void should_UpdateEmptySquare_When_GivePieceAndPosition() {
        ChessBoard emptyBoard = ChessBoard.normalBoard(new HashMap<>());
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, Pawn.of(Team.BLACK))));
        FakeBoardDao boardDao = FakeBoardDao.runningBoard(board.status());
        OutputView outputView = new OutputView();

        boardDao.updateEmptyPosition(D3);
        String actualBoard = outputView.printChessBoardMessage(boardDao.getBoard());
        String expectedBoard = outputView.printChessBoardMessage(emptyBoard);

        assertThat(actualBoard).isEqualTo(expectedBoard);
    }
}
