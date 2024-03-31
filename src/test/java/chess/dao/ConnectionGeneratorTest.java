package chess.dao;

import chess.database.ConnectionGenerator;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class ConnectionGeneratorTest {

    @Test
    void connectionTest() {
        Connection connection = new ConnectionGenerator().getConnection();
    }

}
