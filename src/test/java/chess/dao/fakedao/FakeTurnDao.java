package chess.dao.fakedao;

import chess.dao.TurnDao;
import chess.domain.game.ChessGame;
import chess.domain.piece.Team;

public class FakeTurnDao implements TurnDao {
    private Team team;

    public FakeTurnDao(Team team) {
        this.team = team;
    }

    @Override
    public void saveTurn(ChessGame game) {
        this.team = game.getTurn();
    }

    @Override
    public Team findTurn() {
        return team;
    }
}
