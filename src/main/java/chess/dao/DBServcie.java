package chess.dao;

import chess.domain.game.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

public final class DBServcie {
    private final BoardDao boardDao;
    private final TurnDao turnDao;


    public DBServcie(BoardDao boardDao, TurnDao turnDao) {
        this.boardDao = boardDao;
        this.turnDao = turnDao;
    }

    public static DBServcie of() {
        return new DBServcie(BoardDao.of(), TurnDao.of());
    }

    public ChessGame loadPreviousGame() {
        return ChessGame.runningGame(boardDao.findBoard(), turnDao.findTurn());
    }

    public void updateMove(Position start, Position destination, Piece piece) {
        boardDao.updatePiecePosition(destination, piece);
        boardDao.updateEmptyPosition(start);
    }

    public void saveTurn(ChessGame game) {
        turnDao.saveTurn(game);
    }

    public void setNewGame() {
        ChessGame game = ChessGame.newGame();
        boardDao.resetBoard();
        boardDao.saveBoard(game.getBoard());
        saveTurn(game);
    }
}
