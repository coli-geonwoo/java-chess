package chess.service;

import chess.view.OutputView;
import org.junit.jupiter.api.Test;

import static chess.domain.position.Fixture.D2;
import static chess.domain.position.Fixture.D4;

class ChessGameServiceTest {

    @Test
    void playTurn() {
        ChessGameService service = ChessGameService.of();
        OutputView outputView = new OutputView();
        outputView.printChessBoardMessage(service.gameBoard());

        service.playTurn(D2, D4);

        outputView.printChessBoardMessage(service.gameBoard());
    }

}
