package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public class EndGame implements ChessGame {

    private final ChessBoard board;

    public EndGame(ChessBoard board) {
        this.board = board;
    }

    @Override
    public Team winTeam() {
        if (board.isKingAlive(Team.BLACK)) {
            return Team.BLACK;
        }
        return Team.WHITE;
    }

    @Override
    public ChessGame playTurn(Position start, Position destination) {
        throw new UnsupportedOperationException("이 경기는 이미 끝난 경기입니다.");
    }

    @Override
    public Score teamScore(Team team) {
        return board.teamScore(team);
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
