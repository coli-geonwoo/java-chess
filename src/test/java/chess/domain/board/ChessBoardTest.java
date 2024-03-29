package chess.domain.board;

import chess.domain.piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static chess.domain.position.Fixture.D2;
import static chess.domain.position.Fixture.D3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChessBoardTest {
    /*
    ........  8
    ........  7
    ........  6
    ........  5
    ........  4
    ...P....  3
    ........  2
    ........  1
    abcdefgh
    */
    @DisplayName("체스보드의 특정위치에 기물이 없는지 확인할 수 있다")
    @Test
    void should_CheckPositionEmptiness_When_GivenPosition() {
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, Pawn.of(Team.BLACK))));
        assertAll(
                () -> assertThat(board.positionIsEmpty(D3)).isFalse(),
                () -> assertThat(board.positionIsEmpty(D2)).isTrue()
        );
    }

    /*
    ........  8
    ........  7
    ........  6
    ........  5
    ........  4
    ...P....  3
    ........  2
    ........  1
    abcdefgh
    */
    @DisplayName("체스보드 특정 위치의 기물을 가져올 수 있다")
    @Test
    void should_GetPieceByPosition_When_GiveTargetPosition() {
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, Pawn.of(Team.BLACK))));

        assertThat(board.findPieceByPosition(D3)).isInstanceOf(Pawn.class);
    }

    @DisplayName("체스보드 특정 위치의 기물을 가져올 때, 위치에 기물이 없으면 NullPiece를 반환한다")
    @Test
    void should_ReturnNullPiece_When_TargetPositionIsEmpty() {
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, Pawn.of(Team.BLACK))));
        Piece foundPiece = board.findPieceByPosition(D2);
        assertThat(foundPiece).isEqualTo(NullPiece.getInstance());
    }

    @DisplayName("특정 팀의 킹이 살아있는지 확인할 수 있다")
    @Test
    void should_CheckKingIsAlive() {
        ChessBoard board = ChessBoard.normalBoard(Map.ofEntries(Map.entry(D3, new King(Team.BLACK))));

        assertAll(
                () -> assertThat(board.isKingAlive(Team.BLACK)).isTrue(),
                () -> assertThat(board.isKingAlive(Team.WHITE)).isFalse()
        );
    }
}
