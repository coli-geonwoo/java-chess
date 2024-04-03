package chess.controller.command;

import chess.service.ChessGameService;
import chess.view.OutputView;

public final class StartCommand implements Command {

    private StartCommand() {
    }

    public static StartCommand of(String input) {
        return new StartCommand();
    }

    @Override
    public void execute(ChessGameService service, OutputView outputView) {
        outputView.printChessBoardMessage(service.gameBoard());
    }

    @Override
    public boolean isStart() {
        return true;
    }

    @Override
    public boolean isEnd() {
        return false;
    }
}
