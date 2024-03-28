package chess.view;

import chess.domain.piece.Team;

import java.util.stream.Stream;

public enum TeamMessage {
    BLACK_TEAM_MESSAGE(Team.BLACK, "블랙"),
    WHITE_TEAM_MESSAGE(Team.WHITE, "화이트"),
    NONE_TEAM_MESSAGE(Team.NONE, ""),
    ;

    private final Team team;
    private final String teamMessage;

    TeamMessage(Team team, String teamMessage) {
        this.team = team;
        this.teamMessage = teamMessage;
    }

    public static String messageOf(Team targetTeam) {
        return Stream.of(values())
                .filter(t -> t.team == targetTeam)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("알 수 없는 팀입니다."))
                .teamMessage;
    }
}
