package chess.domain.piece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
        Piece pawn = Pawn.blackPawn();
        Piece queen = new Queen(Team.BLACK);
        Piece knight = new Knight(Team.BLACK);
        Piece king = new King(Team.BLACK);
        Piece nullPiece = NullPiece.getInstance();

        assertAll(
                () -> assertThat(bishop.getScore()).isEqualTo(3),
                () -> assertThat(rook.getScore()).isEqualTo(5),
                () -> assertThat(pawn.getScore()).isEqualTo(1),
                () -> assertThat(queen.getScore()).isEqualTo(9),
                () -> assertThat(knight.getScore()).isEqualTo(2.5),
                () -> assertThat(king.getScore()).isEqualTo(0),
                () -> assertThat(nullPiece.getScore()).isEqualTo(0)
        );
    }
}
