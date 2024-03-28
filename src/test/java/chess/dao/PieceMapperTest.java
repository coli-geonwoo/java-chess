package chess.dao;

import chess.domain.piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.dao.PieceMapper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PieceMapperTest {

    @DisplayName("각 기물에 해당하는 DBmessage로 전환할 수 있다")
    @Test
    void should_ReturnPieceTypeMessage_When_GivePieceType() {
        assertAll(
                () -> assertThat(PieceMapper.typeMessageOf(new Bishop(Team.WHITE))).isEqualTo(BISHOP.getTypeMessage()),
                () -> assertThat(PieceMapper.typeMessageOf(Pawn.of(Team.WHITE))).isEqualTo(WHITE_PAWN.getTypeMessage()),
                () -> assertThat(PieceMapper.typeMessageOf(Pawn.of(Team.BLACK))).isEqualTo(BLACK_PAWN.getTypeMessage()),
                () -> assertThat(PieceMapper.typeMessageOf(new Knight(Team.WHITE))).isEqualTo(KNIGHT.getTypeMessage()),
                () -> assertThat(PieceMapper.typeMessageOf(new Rook(Team.WHITE))).isEqualTo(ROOK.getTypeMessage()),
                () -> assertThat(PieceMapper.typeMessageOf(new King(Team.WHITE))).isEqualTo(KING.getTypeMessage()),
                () -> assertThat(PieceMapper.typeMessageOf(new Queen(Team.WHITE))).isEqualTo(QUEEN.getTypeMessage())
        );
    }

    @Test
    void findPieceByType() {
        assertAll(
                () -> assertThat(PieceMapper.findPieceByType("bishop", Team.BLACK).getClass()).isEqualTo(Bishop.class),
                () -> assertThat(PieceMapper.findPieceByType("pawn", Team.WHITE).getClass()).isEqualTo(WhitePawn.class),
                () -> assertThat(PieceMapper.findPieceByType("pawn", Team.BLACK).getClass()).isEqualTo(BlackPawn.class),
                () -> assertThat(PieceMapper.findPieceByType("knight", Team.BLACK).getClass()).isEqualTo(Knight.class),
                () -> assertThat(PieceMapper.findPieceByType("rook", Team.BLACK).getClass()).isEqualTo(Rook.class),
                () -> assertThat(PieceMapper.findPieceByType("king", Team.BLACK).getClass()).isEqualTo(King.class),
                () -> assertThat(PieceMapper.findPieceByType("queen", Team.BLACK).getClass()).isEqualTo(Queen.class)
        );
    }
}
