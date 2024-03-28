package chess.domain.piece;

import chess.domain.board.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceTest {
    @DisplayName("기물의 팀이 흑팀인지 확인할 수 있다")
    @Test
    void should_CheckPieceIsBlackTeam() {
        King blackPiece = new King(Team.BLACK);
        King whitePiece = new King(Team.WHITE);

        assertAll(
                () -> assertThat(blackPiece.isBlackTeam()).isTrue(),
                () -> assertThat(whitePiece.isBlackTeam()).isFalse()
        );
    }

    @DisplayName("각 기물의 점수를 알 수 있다")
    @Test
    void should_ReturnPieceScore() {
        Piece bishop = new Bishop(Team.BLACK);
        Piece rook = new Rook(Team.BLACK);
        Piece pawn = Pawn.of(Team.BLACK);
        Piece queen = new Queen(Team.BLACK);
        Piece knight = new Knight(Team.BLACK);
        Piece king = new King(Team.BLACK);

        assertAll(
                () -> assertThat(bishop.getScore()).isEqualTo(Score.from(3)),
                () -> assertThat(rook.getScore()).isEqualTo(Score.from(5)),
                () -> assertThat(pawn.getScore()).isEqualTo(Score.from(1)),
                () -> assertThat(queen.getScore()).isEqualTo(Score.from(9)),
                () -> assertThat(knight.getScore()).isEqualTo(Score.from(2.5)),
                () -> assertThat(king.getScore()).isEqualTo(Score.from(0))
        );
    }

    @DisplayName("NullPiece는 점수 반환 요청을 받으면 오류를 발생시킨다.")
    @Test
    void should_ThrowUnsupportedOperationException_When_GetScoreFromNullPiece() {
        Piece nullPiece = NullPiece.getInstance();

        assertThatThrownBy(() -> nullPiece.getScore())
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
