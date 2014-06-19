package com.epam.sultangazy.webapp.dao.mysql;


import com.epam.sultangazy.webapp.dao.UserDAO;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.DaoUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLUserDAO implements UserDAO {
    private static final String CHECK_USER = "SELECT user.idUser FROM fooddelivery.user WHERE user.email =? AND user.password =?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT * FROM fooddelivery.user WHERE user.email = ?";
    private static final String CHECK_USER_LOGIN = "SELECT user.idUser FROM fooddelivery.user WHERE user.email = ?";
    private static final String INSERT_USER = "insert into fooddelivery.user (email, password, name, address, phone, user_type_id, deleted) values (?,?,?,?,?,?,?);";
    private static final String INSERT_USER_COOPERATION = "insert into fooddelivery.cooperation (email, password, name, address, phone, type_id) values (?,?,?,?,?,?);";
    private static final String UPDATE_USER = "update fooddelivery.user set user.password = ? , user.name = ? , user.address = ? , user.phone = ? where user.idUser = ?";
    private static final String SELECT_ALL_COOPERATION_REQUEST = "select * from fooddelivery.cooperation";
    private static final String DELETE_COOPERATION_REQUEST_BY_ID = "DELETE FROM fooddelivery.cooperation WHERE cooperation.idcooperation =?";
    private static final String SELECT_ALL_USERS_NOT_DELETED = "select * from fooddelivery.user where user.deleted not like 'true' and user.user_type_id = 1";
    private static final String DELETE_USER_BY_ID = "update fooddelivery.user set user.email = 'none' , user.deleted = 'true' where user.idUser = ?";
    private static final String SELECT_ALL_RESTORATORS_NOT_DELETED = "select * from fooddelivery.user where user.deleted not like 'true' and user.user_type_id = 2";
    private MySQLDAOFactory factory;

    public MySQLUserDAO(MySQLDAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void deleteUser(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, DELETE_USER_BY_ID, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public Map<Integer, User> findRestorators() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Map<Integer, User> users;
        try {
            connection = factory.createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_RESTORATORS_NOT_DELETED);
            users = new HashMap<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("idUser"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setRoleOrdinal(resultSet.getInt("user_type_id"));
                    users.put(user.getId(), user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().setFreeConnection(connection);
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public List<User> findUsers() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users;
        try {
            connection = factory.createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_USERS_NOT_DELETED);
            users = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("idUser"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setRoleOrdinal(resultSet.getInt("user_type_id"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().setFreeConnection(connection);
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void deleteCooperationRequest(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, DELETE_COOPERATION_REQUEST_BY_ID, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public boolean checkUser(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Object[] values = {login};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, CHECK_USER_LOGIN, false, values);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void insertUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {user.getEmail(), user.getPassword(), user.getName(), user.getAddress(), user.getPhone(), user.getRole().ordinal(), "false"};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, INSERT_USER, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public void insertUserCooperation(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {user.getEmail(), user.getPassword(), user.getName(), user.getAddress(), user.getPhone(), user.getRole().ordinal()};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, INSERT_USER_COOPERATION, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public List<User> findCooperationRequests() throws DAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users;
        try {
            connection = factory.createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_COOPERATION_REQUEST);
            users = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("idcooperation"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setName(resultSet.getString("name"));
                    user.setAddress(resultSet.getString("address"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setRoleOrdinal(resultSet.getInt("type_id"));
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().setFreeConnection(connection);
            try {
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User findUser(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;
        try {
            Object[] values = {login};
            connection = factory.createConnection();
            user = new User();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_USER_BY_LOGIN, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("idUser"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setAddress(resultSet.getString("address"));
                user.setPhone(resultSet.getString("phone"));
                user.setRoleOrdinal(resultSet.getInt("user_type_id"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {user.getPassword(), user.getName(), user.getAddress(), user.getPhone(), user.getId()};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, UPDATE_USER, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
            return true;
        }
    }
}
