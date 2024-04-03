package chess.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final int MAX_RETRY_COUNT = 10;

    private static final int MAX_POOL_SIZE = 10;
    private static final String SQL_VERIFYCONN = "select 1";

    private static final DbConnector connector = new DbConnector();
    private final Stack<Connection> freePool;
    private final Set<Connection> occupiedPool;
    private int connectionNumber;

    public ConnectionPool() {
        this.freePool = new Stack<>();
        this.occupiedPool = new HashSet<>();
        this.connectionNumber = 0;
    }

    public synchronized Connection getConnection(int tryCnt) throws SQLException {
        if (isAllConnectionUsed()) {
            checkRetryCount(tryCnt);
            waitOneSeconds();
            return getConnection(tryCnt + 1);
        }

        Connection connection = getConnectionFromPool();
        if (connection == null) {
            connection = createNewConnectionForPool();
        }

        return makeAvailable(connection); //일정 시간이 지나 끊긴 connection을 다시 연결 후 반환
    }

    public void returnConnection(Connection usedConnection) {
        if (usedConnection == null) {
            throw new NullPointerException("반납한 connection이 null입니다.");
        }
        if (!occupiedPool.remove(usedConnection)) {
            throw new IllegalStateException("사용된 커넥션이 풀에 존재하지 않습니다.");
        }
        freePool.push(usedConnection);
    }

    private Connection createNewConnectionForPool() {
        connectionNumber += 1;
        Connection connection = connector.createNewConnection();
        occupiedPool.add(connection);
        return connection;
    }

    private void checkRetryCount(int tryCnt) {
        if (tryCnt == MAX_RETRY_COUNT) {
            throw new RuntimeException("커넥션 풀이 모두 차있습니다");
        }
    }

    private void waitOneSeconds() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnectionFromPool() {
        if (freePool.isEmpty()) {
            return null;
        }
        Connection connection = freePool.pop();
        occupiedPool.add(connection);
        return connection;
    }

    private boolean isAllConnectionUsed() {
        return freePool.isEmpty() && connectionNumber >= MAX_POOL_SIZE;
    }

    private Connection makeAvailable(Connection connection) throws SQLException {
        if (isConnectionAvailable(connection)) {
            return connection;
        }
        closeNotAvaliableConnection(connection);
        return createNewConnectionForPool();
    }

    private void closeNotAvaliableConnection(Connection connection) throws SQLException {
        occupiedPool.remove(connection);
        connectionNumber--;
        connection.close();
    }

    private boolean isConnectionAvailable(Connection conn) {
        try (Statement st = conn.createStatement()) {
            st.executeQuery(SQL_VERIFYCONN);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
