package chess.controller.command;

import chess.service.ChessGameService;
import chess.view.OutputView;

public final class StatusCommand implements Command {

    private StatusCommand() {
    }

    public static StatusCommand of(String input) {
        return new StatusCommand();
    }


    @Override
    public void execute(ChessGameService service, OutputView outputView) {
        outputView.printStatusMessage(service.getGame());
    }

    @Override
    public boolean isStart() {
        return false;
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
