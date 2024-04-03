package chess.service;

import chess.dao.BoardDao;
import chess.dao.TurnDao;
import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.position.Position;

public class ChessGameService {
    private final BoardDao boardDao;
    private final TurnDao turnDao;
    private final ChessGame game;

    public ChessGameService(BoardDao boardDao, TurnDao turnDao, ChessGame game) {
        this.boardDao = boardDao;
        this.turnDao = turnDao;
        this.game = game;
    }

    public static ChessGameService of(BoardDao boardDao, TurnDao turnDao) {
        ChessGame game = loadPreviousGame(boardDao, turnDao);

        if (game.isEndGame()) {
            game = ChessGame.newGame();
        }

        return new ChessGameService(boardDao, turnDao, game);
    }

    private static ChessGame loadPreviousGame(BoardDao boardDao, TurnDao turnDao) {
        return ChessGame.runningGame(boardDao.findBoard(), turnDao.findTurn());
    }

    public void end() {
        if (game.isEndGame()) {
            saveGame(ChessGame.newGame());
        }
        saveGame(game);
    }

    public void playTurn(Position start, Position destination) {
        game.playTurn(start, destination);
        updateMove(start, destination, game.findPieceByPosition(destination));
        turnDao.saveTurn(game);
    }

    public boolean isEndGame() {
        return game.isEndGame();
    }

    private void saveGame(ChessGame game) {
        boardDao.resetBoard();
        boardDao.saveBoard(game.getBoard());
        turnDao.saveTurn(game);
    }

    private void updateMove(Position start, Position destination, Piece piece) {
        boardDao.updatePiecePosition(destination, piece);
        boardDao.updateEmptyPosition(start);
    }

    public ChessBoard gameBoard() {
        return game.getBoard();
    }

    public ChessGame getGame() {
        return game;
    }
}
