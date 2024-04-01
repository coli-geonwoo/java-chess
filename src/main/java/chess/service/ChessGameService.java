package chess.service;

import chess.dao.DBServcie;
import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.position.Position;

public class ChessGameService {
    // TODO chessGameService에 대한 테스트 작성해보기
    // TODO boardDao, TurnDao를 가지도록 리팩터링
    private final DBServcie dbServcie;
    private final ChessGame game;

    private ChessGameService(ChessGame game, DBServcie dbServcie) {
        this.game = game;
        this.dbServcie = dbServcie;
    }

    public static ChessGameService of() {
        DBServcie gameDao = DBServcie.of();
        ChessGame game = initializeGame(gameDao);
        return new ChessGameService(game, gameDao);
    }

    private static ChessGame initializeGame(DBServcie dbServcie) {
        ChessGame previousGame = dbServcie.loadPreviousGame();
        if (previousGame.isEndGame()) {
            dbServcie.saveNewGame();
            return ChessGame.newGame();
        }
        return previousGame;
    }

    public void playTurn(Position start, Position destination) {
        game.playTurn(start, destination);
        dbServcie.updateMove(start, destination, game.findPieceByPosition(destination));
        dbServcie.saveTurn(game);
    }

    public boolean isEndGame() {
        return game.isEndGame();
    }

    public ChessBoard gameBoard() {
        return game.getBoard();
    }

    public ChessGame getGame() {
        return game;
    }
}
