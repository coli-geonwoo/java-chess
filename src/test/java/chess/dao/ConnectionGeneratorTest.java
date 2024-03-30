package chess.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionGeneratorTest {

    @Test
    void connectionTest(){
        Connection connection = new ConnectionGenerator().getConnection();
    }

}
