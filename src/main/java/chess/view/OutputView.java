package chess.view;

import chess.domain.board.ChessBoard;
import chess.domain.game.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.ColumnPosition;
import chess.domain.position.Position;
import chess.domain.position.RowPosition;

import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static chess.view.CommandMapper.*;

public class OutputView {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String POSITION_EMPTY_MESSAGE = ".";

    private static final String SCORE_MESSAGE_FORMAT = "%s : %.1f";
    private static final String RESULT_MESSAGE_FORMAT = "경기 결과 : %s %s";
    private static final String DRAW_MESSAGE = "무승부";
    private static final String WIN_MESSAGE = "승";

    public void printStartMessage() {
        System.out.println(resolveStartMessage());
    }

    public void printChessBoardMessage(ChessBoard chessBoard) {
        System.out.println(resolveChessBoardMessage(chessBoard));
    }

    public void printStatusMessage(ChessGame game) {
        if (!game.isEndGame()) {
            printGameScores(game);
        }
        printWinTeam(game);
    }

    private void printWinTeam(ChessGame game) {
        System.out.println(resolveWinTeamMessage(game.winTeam()));
    }

    private void printGameScores(ChessGame game) {
        System.out.println(resolveScoreMessage(game, Team.WHITE));
        System.out.println(resolveScoreMessage(game, Team.BLACK));
    }

    private String resolveStartMessage() {
        return new StringJoiner(LINE_SEPARATOR)
                .add("체스 게임을 시작합니다.")
                .add(String.format("> 게임 시작 : %s", START.getCode()))
                .add(String.format("> 게임 종료 : %s", END.getCode()))
                .add(String.format("> 게임 결과 : %s", STATUS.getCode()))
                .add(String.format("> 게임 이동 : %s source위치 target위치 - 예. %s b2 b3", MOVE.getCode(), MOVE.getCode()))
                .toString();
    }

    private String resolveChessBoardMessage(ChessBoard chessBoard) {
        return IntStream.rangeClosed(RowPosition.MIN_NUMBER, RowPosition.MAX_NUMBER)
                .mapToObj(rowNumber -> resolveRowMessage(chessBoard, rowNumber))
                .collect(Collectors.joining(LINE_SEPARATOR));
    }

    private String resolveRowMessage(ChessBoard chessBoard, int rowNumber) {
        return IntStream.rangeClosed(ColumnPosition.MIN_NUMBER, ColumnPosition.MAX_NUMBER)
                .mapToObj(columnNumber -> Position.of(rowNumber, columnNumber))
                .map(position -> resolveSquareMessage(chessBoard, position))
                .collect(Collectors.joining());
    }

    private String resolveSquareMessage(ChessBoard chessBoard, Position position) {
        if (chessBoard.positionIsEmpty(position)) {
            return POSITION_EMPTY_MESSAGE;
        }
        Piece foundPiece = chessBoard.findPieceByPosition(position);
        return PieceMessage.messageOf(foundPiece);
    }

    private String resolveWinTeamMessage(Team winTeam) {
        if (winTeam == Team.NONE) {
            return String.format(RESULT_MESSAGE_FORMAT, TeamMessage.messageOf(Team.NONE), DRAW_MESSAGE);
        }
        return String.format(RESULT_MESSAGE_FORMAT, TeamMessage.messageOf(winTeam), WIN_MESSAGE);
    }

    private String resolveScoreMessage(ChessGame game, Team team) {
        return String.format(SCORE_MESSAGE_FORMAT, TeamMessage.messageOf(team), game.teamScore(team).getScore());
    }
}
