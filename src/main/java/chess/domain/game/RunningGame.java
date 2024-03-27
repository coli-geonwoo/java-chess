package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import chess.domain.board.Score;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public class RunningGame implements ChessGame {
    private final ChessBoard board;
    private Team turn;

    private RunningGame(ChessBoard board, Team team) {
        this.board = board;
        this.turn = team;
    }

    public static ChessGame newGame() {
        ChessBoard board = ChessBoardCreator.normalGameCreator().create();
        return new RunningGame(board, Team.WHITE);
    }

    @Override
    public ChessGame playTurn(Position start, Position destination) {
        checkPieceIsTurnTeamPiece(start);
        board.move(start, destination);

        if (isEndGame()) {
            return new EndGame(board);
        }

        switchTurn();
        return this;
    }

    @Override
    public Score teamScore(Team team) {
        return board.teamScore(team);
    }

    @Override
    public Team winTeam() {
        Score whiteScore = board.teamScore(Team.WHITE);
        Score blackScore = board.teamScore(Team.BLACK);
        if (whiteScore.isHigherThan(blackScore)) {
            return Team.WHITE;
        }
        if (blackScore.isHigherThan(whiteScore)) {
            return Team.BLACK;
        }
        return Team.NONE;
    }

    @Override
    public boolean isEnd() {
        return false;
    }

    private boolean isEndGame() {
        return !board.isKingAlive(turn.opposite());
    }

    private void checkPieceIsTurnTeamPiece(Position start) {
        if (board.findPieceByPosition(start).isOtherTeam(turn)) {
            throw new IllegalArgumentException("현재 턴을 진행하는 팀의 기물이 아닙니다.");
        }
    }

    private void switchTurn() {
        turn = turn.opposite();
    }

    public ChessBoard getBoard() {
        return board;
    }
}
