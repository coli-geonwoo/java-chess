package chess.controller.command;

import chess.domain.game.ChessGame;
import chess.view.OutputView;

public final class StartCommand implements Command {

    private StartCommand() {
    }

    public static StartCommand of(String input) {
        return new StartCommand();
    }

    @Override
    public ChessGame execute(ChessGame game, OutputView outputView) {
        outputView.printChessBoardMessage(game.getBoard());
        return game;
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
