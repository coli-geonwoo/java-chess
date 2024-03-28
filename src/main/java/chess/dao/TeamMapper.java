package chess.dao;

import chess.domain.piece.Team;

import java.util.stream.Stream;

public enum TeamMapper {
    BLACK_TEAM(Team.BLACK, "black"),
    WHITE_TEAM(Team.WHITE, "white"),
    ;

    private final Team team;
    private final String teamMessage;

    TeamMapper(Team team, String teamMessage) {
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

    public static Team findTeam(String message) {
        return Stream.of(values())
                .filter(t -> t.teamMessage.equals(message))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("알 수 없는 팀입니다."))
                .team;
    }

    public Team getTeam() {
        return team;
    }

    public String getTeamMessage() {
        return teamMessage;
    }
}
