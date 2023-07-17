package dao.impl;

import dao.ConnectionPool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionPoolImpl implements ConnectionPool {

    private static String url;
    private static String user;
    private static String password;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final int INITIAL_POOL_SIZE = 10;
    private static final List<Connection> connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);
    private static final List<Connection> usedConnections = new ArrayList<>(INITIAL_POOL_SIZE);
    private static final ConnectionPool mainConnectionPool = new ConnectionPoolImpl();

    static {

        getAppProperties();
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер подключения к базе данных не найден");
            e.printStackTrace();
        }

        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                connectionPool.add(DriverManager.getConnection(url, user, password));

            } catch (SQLException e) {
                System.out.println("Ошибка при получении соединения во время инициализации пула подключений");
                e.printStackTrace();
            }
        }
    }

    private static void getAppProperties() {
        Properties properties = new Properties();
        if (System.getProperty("url") == null) {
            try {
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
        } else {
            url = System.getProperty("url");
            user = System.getProperty("user");
            password = System.getProperty("password");
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
    public void releaseConnection(Connection connection) {
        connectionPool.add(connection);
        usedConnections.remove(connection);
    }
}
