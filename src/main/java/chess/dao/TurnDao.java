package chess.dao;

import chess.domain.game.ChessGame;
import chess.domain.piece.Team;

public interface TurnDao {
    void saveTurn(ChessGame game);

    Team findTurn();
}
