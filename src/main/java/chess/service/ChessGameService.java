package chess.service;

import chess.dao.GameDao;
import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.position.Position;

public class ChessGameService {
    private final GameDao gameDao;
    private ChessGame game;

    private ChessGameService(ChessGame game, GameDao gameDao) {
        this.game = game;
        this.gameDao = gameDao;
    }

    public static ChessGameService of(){
        GameDao gameDao = GameDao.of();
        ChessGame game = initializeGame(gameDao);
        return new ChessGameService(game, gameDao);
    }

    private static ChessGame initializeGame(GameDao gameDao) {
        ChessGame foundGame = gameDao.findGame();
        if (foundGame.isEndGame()) {
            return ChessGame.newGame();
        }
        return foundGame;
    }

    public void playTurn(Position start, Position destination) {
        game.playTurn(start, destination);
        gameDao.updateMove(start, destination, game.findPieceByPosition(destination));
        gameDao.saveTurn(game);
    }

    public boolean isEndGame() {
        return game.isEndGame();
    }

    public void end(){
        gameDao.saveGame(game);
    }

    public ChessBoard gameBoard(){
        return game.getBoard();
    }

    public ChessGame getGame() {
        return game;
    }
}
