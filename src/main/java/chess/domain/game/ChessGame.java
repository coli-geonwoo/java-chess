package chess.domain.game;

import chess.domain.board.ChessBoard;
import chess.domain.board.ChessBoardCreator;
import chess.domain.board.Score;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public class ChessGame {
    private final ChessBoard board;
    private Team turn;

    private ChessGame(ChessBoard board, Team team) {
        this.board = board;
        this.turn = team;
    }

    public static ChessGame newGame() {
        ChessBoard board = ChessBoardCreator.normalGameCreator().create();
        return new ChessGame(board, Team.WHITE);
    }

    public static ChessGame runningGame(ChessBoard board, Team team) {
        return new ChessGame(board, team);
    }

    public void playTurn(Position start, Position destination) {
        checkPieceIsTurnTeamPiece(start);
        board.move(start, destination);
        switchTurn();
    }

    public Score teamScore(Team team) {
        return board.teamScore(team);
    }

    public Team winTeam() {
        if (isEndGame()) {
            return winTeamInEndGame();
        }
        return winTeamInRunningGame();
    }

    public boolean isEndGame() {
        return !board.isKingAlive(turn);
    }

    public Piece findPieceByPosition(Position position) {
        return board.findPieceByPosition(position);
    }

    private Team winTeamInEndGame() {
        if (board.isKingAlive(Team.BLACK)) {
            return Team.BLACK;
        }
        return Team.WHITE;
    }

    private Team winTeamInRunningGame() {
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

    public Team getTurn() {
        return turn;
    }
}
