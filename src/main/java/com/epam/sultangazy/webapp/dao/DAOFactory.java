package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLUserDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOFactory {
    private static Logger LOG = Logger.getLogger(DAOFactory.class);
    private static ConnectionPool pool = null;

    public DAOFactory(ConnectionPool pool) {
        DAOFactory.pool = pool;
    }

    public synchronized Connection createConnection() {
        Connection conn = null;
        try {
            conn = pool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            LOG.debug("get pool connection");
        }
        return conn;
    }

    public UserDAO getUserDAO() {
        return new MySQLUserDAO(this);
    }

    public RestaurantDAO getRestaurantDAO() {
        return new MySQLRestaurantDAO(this);
    }

    public DishDAO getDishDAO() {
        return new MySQLDishDAO(this);
    }

    public OrderDAO getOrderDAO() {
        return new MySQLOrderDAO(this);
    }
}

