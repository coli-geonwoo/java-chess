package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.service.ChessGameService;
import chess.view.OutputView;

public sealed interface Command
        permits StartCommand, MoveCommand, StatusCommand, EndCommand {
    void execute(ChessGameService service, OutputView outputView);

    boolean isStart();

    boolean isEnd();
}
