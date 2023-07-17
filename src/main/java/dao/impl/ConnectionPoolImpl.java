package dao.impl;

import dao.ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolImpl implements ConnectionPool {

    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final int INITIAL_POOL_SIZE = 10;
    private static final List<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
    private static final List<Connection> usedConnections = new ArrayList<>(INITIAL_POOL_SIZE);
    private static final ConnectionPool mainConnectionPool = new ConnectionPoolImpl();

    static {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                Class.forName(DRIVER);
                connectionPool.add(DriverManager.getConnection(URL, USER, PASSWORD));
            } catch (ClassNotFoundException e) {
                System.out.println("Класс драйвера БД не найден!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Ошибка при получении соединения во время инициализации пула подключений");
                e.printStackTrace();
            }
        }
    }

    public static ConnectionPool getConnectionPool() {
        return mainConnectionPool;
    }

    @Override
    public Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }
}
