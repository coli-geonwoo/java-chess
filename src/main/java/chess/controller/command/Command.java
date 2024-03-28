package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.view.OutputView;

public sealed interface Command
        permits StartCommand, MoveCommand, StatusCommand, EndCommand {
    void execute(ChessGame game, OutputView outputView);

    boolean isStart();

    boolean isEnd();
}
