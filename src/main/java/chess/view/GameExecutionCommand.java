package chess.view;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum GameExecutionCommand {
    START("start"),
    END("end");

    private final String code;

    GameExecutionCommand(String code) {
        this.code = code;
    }

    public static GameExecutionCommand from(String code) {
        return Arrays.stream(values())
                .filter(code::equals)
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 커맨드를 찾을 수 없습니다"));
    }
}
