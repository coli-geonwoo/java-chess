package chess.controller.command;

import chess.service.ChessGameService;
import chess.view.OutputView;

public final class EndCommand implements Command {
    private EndCommand() {
    }

    public static EndCommand of(String input) {
        return new EndCommand();
    }

    @Override
    public void execute(ChessGameService service, OutputView outputView) {
        service.end();
    }

    @Override
    public boolean isStart() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return true;
    }
}
