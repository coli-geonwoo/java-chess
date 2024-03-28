package chess.dao;

import chess.domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TeamMapperTest {

    @DisplayName("팀에 해당하는 DBmessage로 전환할 수 있다")
    @Test
    void should_ConvertTeamMessage_When_GiveTeam() {
        assertAll(
                () -> assertThat(TeamMapper.messageOf(Team.BLACK)).isEqualTo(TeamMapper.BLACK_TEAM.getTeamMessage()),
                () -> assertThat(TeamMapper.messageOf(Team.WHITE)).isEqualTo(TeamMapper.WHITE_TEAM.getTeamMessage())
        );
    }

    @DisplayName("DBmessage에 해당하는 팀을 찾을 수 있다")
    @Test
    void should_FindTeam_When_Give_DBmessage() {
        assertAll(
                () -> assertThat(TeamMapper.findTeam("black")).isEqualTo(Team.BLACK),
                () -> assertThat(TeamMapper.findTeam("white")).isEqualTo(Team.WHITE)
        );
    }
}
