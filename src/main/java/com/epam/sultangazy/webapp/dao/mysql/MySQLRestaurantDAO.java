package com.epam.sultangazy.webapp.dao.mysql;

import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.DaoUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MySQLRestaurantDAO implements RestaurantDAO {
    private static Logger LOG = Logger.getLogger(MySQLUserDAO.class);
    private static final String SELECT_ALL_RESTAURANTS = "SELECT * FROM fooddelivery.restaurant where restaurant.deleted not like 'true'";
    private static final String SELECT_RESTAURANT_BY_RESTORATOR_ID = "SELECT *FROM fooddelivery.restaurant WHERE restaurant.id_restorator = ?";
    private static final String CHECK_RESTORATOR = "SELECT restaurant.idrestaurant FROM fooddelivery.restaurant WHERE restaurant.id_restorator = ?";
    private static final String INSERT_RESTAURANT = "insert into fooddelivery.restaurant (name, delivery_time, note, image, id_restorator, deleted ) values (?,?,?,?,?,'false')";
    private static final String UPDATE_RESTAURANT = "update fooddelivery.restaurant set restaurant.name = ? , restaurant.delivery_time = ? , restaurant.note = ?, restaurant.image = ? where restaurant.idrestaurant = ?";
    private static final String SELECT_RESTAURANT_BY_ID = "SELECT * FROM fooddelivery.restaurant WHERE restaurant.idrestaurant = ?";
    private static final String DELETE_RESTAURANT_BY_ID = "update fooddelivery.restaurant set restaurant.deleted = 'true' where restaurant.idrestaurant = ?";
    private static final String DELETE_RESTORATOR_BY_ID = "update fooddelivery.user set user.email = 'none' , user.deleted = 'true' where user.idUser = ?";
    private DAOFactory factory;

    public MySQLRestaurantDAO(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public void deleteRestaurantById(int id, int idrestorator) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int orderId = 0;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            connection.setAutoCommit(false);
            preparedStatement = DaoUtils.prepareStatement(connection, DELETE_RESTAURANT_BY_ID, true, values);
            preparedStatement.executeUpdate();
            deleteUser(idrestorator, connection);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e1);
            } finally {
                DaoUtils.close(connection, preparedStatement);
            }
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
    }

    public void deleteUser(int id, Connection connection) throws DAOException {
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {id};
            preparedStatement = DaoUtils.prepareStatement(connection, DELETE_RESTORATOR_BY_ID, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(preparedStatement);
        }
    }

    @Override
    public Restaurant selectRestaurantByID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Restaurant restaurant;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            restaurant = new Restaurant();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_RESTAURANT_BY_ID, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurant.setId(resultSet.getInt("idrestaurant"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setDeliveryTime(resultSet.getInt("delivery_time"));
                restaurant.setNote(resultSet.getString("note"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setRestorator(resultSet.getInt("id_restorator"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return restaurant;
    }

    @Override
    public boolean updateRestaurant(Restaurant restaurant) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {restaurant.getName(), restaurant.getDeliveryTime(), restaurant.getNote(), restaurant.getImage(), restaurant.getId()};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, UPDATE_RESTAURANT, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
            return true;
        }
    }

    @Override
    public void insertRestaurant(Restaurant restaurant, int restorator) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {restaurant.getName(), restaurant.getDeliveryTime(), restaurant.getNote(), restaurant.getImage(), restorator};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, INSERT_RESTAURANT, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public HashMap<Integer, String> selectRestaurantsName() throws DAOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        HashMap<Integer, String> restaurants;
        try {
            connection = factory.createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_RESTAURANTS);
            restaurants = new HashMap<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(resultSet.getInt("idrestaurant"));
                    restaurant.setName(resultSet.getString("name"));
                    restaurants.put(restaurant.getId(), restaurant.getName());
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().setFreeConnection(connection);
            statement.close();
            resultSet.close();
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> findRestaurants() throws DAOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Restaurant> restaurants;
        try {
            connection = factory.createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_RESTAURANTS);
            restaurants = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(resultSet.getInt("idrestaurant"));
                    restaurant.setName(resultSet.getString("name"));
                    restaurant.setDeliveryTime(resultSet.getInt("delivery_time"));
                    restaurant.setNote(resultSet.getString("note"));
                    restaurant.setImage(resultSet.getString("image"));
                    restaurant.setRestorator(resultSet.getInt("id_restorator"));
                    restaurants.add(restaurant);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().setFreeConnection(connection);
            statement.close();
            resultSet.close();
        }
        return restaurants;
    }

    @Override
    public Restaurant selectRestaurantByRestoratorID(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Restaurant restaurant;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_RESTAURANT_BY_RESTORATOR_ID, false, values);
            restaurant = new Restaurant();
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                restaurant.setId(resultSet.getInt("idrestaurant"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setDeliveryTime(resultSet.getInt("delivery_time"));
                restaurant.setNote(resultSet.getString("note"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setRestorator(resultSet.getInt("id_restorator"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return restaurant;
    }

    @Override
    public boolean checkRestorator(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, CHECK_RESTORATOR, false, values);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
    }
}
