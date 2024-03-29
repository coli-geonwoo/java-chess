package chess.domain.position;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ColumnPositionTest {
    @DisplayName("두 열 번호의 차이를 반환할 수 있다")
    @Test
    void should_ReturnDifferenceWithOtherColumnNumber() {
        ColumnPosition testColumnPosition_1 = ColumnPosition.from(7);
        ColumnPosition testColumnPosition_2 = ColumnPosition.from(0);

        int expectedDifference = 7;

        assertThat(testColumnPosition_1.intervalWith(testColumnPosition_2)).isEqualTo(expectedDifference);
    }
}
