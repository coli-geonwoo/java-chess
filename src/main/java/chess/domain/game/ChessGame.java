package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public interface ChessGame {

    ChessGame playTurn(Position start, Position destination);

    Score teamScore(Team team);
    Team winTeam();

    ChessBoard getBoard();

}
