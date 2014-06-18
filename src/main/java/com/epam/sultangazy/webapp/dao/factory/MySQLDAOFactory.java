package com.epam.sultangazy.webapp.dao.factory;

import com.epam.sultangazy.webapp.dao.DishDAO;
import com.epam.sultangazy.webapp.dao.OrderDAO;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.dao.UserDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLUserDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDAOFactory extends DAOFactory {
    private static Logger LOG = Logger.getLogger(MySQLDAOFactory.class);
    private static ConnectionPool pool = null;

    public MySQLDAOFactory(ConnectionPool pool) {
        MySQLDAOFactory.pool = pool;
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

