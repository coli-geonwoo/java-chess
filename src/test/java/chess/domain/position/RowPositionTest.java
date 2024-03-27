package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RowPositionTest {
    @DisplayName("수직 대칭인 행 번호를 반환할 수 있다")
    @Test
    void should_ReturnVerticalReverseRowNumber() {
        RowPosition testRowPosition = RowPosition.from(0);

        assertThat(testRowPosition.reverse()).isEqualTo(RowPosition.from(7));
    }

    @DisplayName("두 행 번호의 차이를 반환할 수 있다")
    @Test
    void should_ReturnDifferenceWithOtherRowNumber() {
        RowPosition testRowPosition_1 = RowPosition.from(7);
        RowPosition testRowPosition_2 = RowPosition.from(0);

        int expectedDifference = 7;

        assertThat(testRowPosition_1.intervalWith(testRowPosition_2)).isEqualTo(expectedDifference);
    }

    @DisplayName("행 번호가 더 높은지 확인할 수 있다")
    @Test
    void should_CheckIsHigherThanOtherRowNumber() {
        RowPosition lowerPosition = RowPosition.from(0);
        RowPosition higherPosition = RowPosition.from(1);

        assertAll(
                () -> assertThat(higherPosition.isHigherThan(lowerPosition)).isTrue(),
                () -> assertThat(lowerPosition.isHigherThan(higherPosition)).isFalse()
        );
    }

    @DisplayName("행 번호가 더 낮은지 확인할 수 있다")
    @Test
    void should_CheckIsLowerThanOtherRowNumber() {
        RowPosition lowerPosition = RowPosition.from(1);
        RowPosition higherPosition = RowPosition.from(0);

        assertAll(
                () -> assertThat(higherPosition.isLowerThan(lowerPosition)).isTrue(),
                () -> assertThat(lowerPosition.isLowerThan(higherPosition)).isFalse()
        );
    }
}
