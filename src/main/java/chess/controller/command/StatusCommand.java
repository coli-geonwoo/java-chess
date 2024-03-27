package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.view.OutputView;

public final class StatusCommand implements Command {

    private StatusCommand() {
    }

    public static StatusCommand of(String input) {
        return new StatusCommand();
    }


    @Override
    public void execute(ChessGame game, OutputView outputView) {
        outputView.printStatusMessage(game);
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
