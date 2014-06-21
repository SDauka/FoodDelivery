package com.epam.sultangazy.webapp.db_pool;

import com.epam.sultangazy.webapp.helper.PropertyReader;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static volatile ConnectionPool instance;
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.DB_CONFIG);
    private String DRIVER_NAME;
    private List<Connection> freeConnections = new ArrayList<>();
    private String url;
    private String user;
    private String password;
    private int maxConn;


    private ConnectionPool() {
        this.DRIVER_NAME = propertyReader.getProperties("driver");
        this.url = propertyReader.getProperties("url");
        this.user = propertyReader.getProperties("user");
        this.password = propertyReader.getProperties("password");
        this.maxConn = Integer.parseInt(propertyReader.getProperties("connections"));
        loadDrivers();
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(DRIVER_NAME).newInstance();
            DriverManager.registerDriver(driver);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public synchronized Connection getConnection() throws SQLException {
        Connection conn;
        if (!freeConnections.isEmpty()) {
            conn = freeConnections.get(freeConnections.size() - 1);
            freeConnections.remove(conn);
            try {
                if (conn.isClosed()) {
                    conn = getConnection();
                }
            } catch (Exception e) {
                conn = getConnection();
            }
        } else {
            conn = newConnection();
        }
        return conn;
    }

    private synchronized Connection newConnection() throws SQLException {
        Connection con = null;
        try {
            if (user == null) {
                con = DriverManager.getConnection(url);
            } else {
                con = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public synchronized void setFreeConnection(Connection con) {
        if ((con != null) && (freeConnections.size() <= maxConn)) {
            freeConnections.add(con);
        }
    }

    public synchronized void release() {
        for (Connection con : freeConnections) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        freeConnections.clear();
    }
}
