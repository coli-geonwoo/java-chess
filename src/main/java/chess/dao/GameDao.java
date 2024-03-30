package chess.dao;

import chess.domain.game.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

public final class GameDao {
    private final BoardDao boardDao;
    private final TurnDao turnDao;


    public GameDao(BoardDao boardDao, TurnDao turnDao) {
        this.boardDao = boardDao;
        this.turnDao = turnDao;
    }

    public static GameDao of() {
        return new GameDao(BoardDao.of(), new TurnDao());
    }

    public void saveGame(ChessGame game) {
        boardDao.saveBoard(game.getBoard());
        turnDao.saveTurn(game);
    }

    public ChessGame findGame() {
        return ChessGame.runningGame(boardDao.findBoard(), turnDao.findTurn());
    }

    public void updateMove(Position start, Position destination, Piece piece){
        boardDao.updatePiecePosition(destination, piece);
        boardDao.updateEmptyPosition(start);
    }

    public void saveTurn(ChessGame game){
        turnDao.saveTurn(game);
    }
}
