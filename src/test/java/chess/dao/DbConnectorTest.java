package chess.dao;

import chess.database.DbConnector;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DbConnectorTest {

    @Test
    void connectionTest() {
        Connection connection = new DbConnector().createNewConnection();
    }

}
