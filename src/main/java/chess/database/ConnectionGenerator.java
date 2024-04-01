package chess.database;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionGenerator {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "chess";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final Connection CACHE = makeConnection();

    private static Connection makeConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB 연결 오류");
        }
    }

    // TODO : cache는 makeconnection으로 초기화되는데 null이면 makeConnection도 null을 리턴하지 않을지
    public Connection getConnection() {
        if (CACHE == null) {
            return makeConnection();
        }
        return CACHE;
    }
}
