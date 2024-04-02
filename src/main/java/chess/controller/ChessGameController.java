package chess.controller;

import chess.controller.command.Command;
import chess.dao.BoardDaoImpl;
import chess.dao.TurnDaoImpl;
import chess.domain.game.ChessGame;
import chess.service.ChessGameService;
import chess.view.CommandMapper;
import chess.view.InputView;
import chess.view.OutputView;

import static chess.util.retryHelper.retryUntilNoError;
import static chess.view.CommandMapper.START;

public class ChessGameController {
    private final InputView inputView;
    private final OutputView outputView;

    public ChessGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Command command;
        ChessGameService gameService = ChessGameService.of(BoardDaoImpl.of(), TurnDaoImpl.of());
        ChessGame game = startChessGame(gameService);

        do {
            command = retryUntilNoError(this::readCommand);
            command.execute(gameService, outputView);
        } while (!command.isEnd() && !gameService.isEndGame());

        outputView.printStatusMessage(game);
    }

    private ChessGame startChessGame(ChessGameService service) {
        outputView.printStartMessage();
        Command startCommand = retryUntilNoError(this::readStartCommand);
        startCommand.execute(service, outputView);
        return service.getGame();
    }

    private Command readStartCommand() {
        Command command = readCommand();
        if (command.isStart()) {
            return command;
        }
        throw new IllegalArgumentException(START.getCode() + "를 입력해야 게임이 시작됩니다.");
    }

    private Command readCommand() {
        return CommandMapper.from(inputView.readGameCommand());
    }
}
