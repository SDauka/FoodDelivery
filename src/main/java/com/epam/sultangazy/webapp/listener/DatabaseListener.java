package com.epam.sultangazy.webapp.listener;

import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DatabaseListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(DatabaseListener.class);

    public DatabaseListener() {
    }

    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            ConnectionPool.getInstance().getConnection();
//        } catch (SQLException e) {
//            LOG.error("dao.exception in create database: " + e);
//        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().release();
    }
}
