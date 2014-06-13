package com.epam.sultangazy.webapp.dao.mysql;

import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.OrderDAO;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.DaoUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLOrderDAO implements OrderDAO {
    private static final String INSERT_ORDER = "INSERT INTO fooddelivery.order (restaurantId,userId,amount,address,phone,dateTime,statusId) VALUES (?,?,?,?,?,?,(SELECT status.idstatus FROM fooddelivery.status WHERE status.name=?))";
    private static final String INSERT_ORDER_DISH = "INSERT INTO fooddelivery.order_dish (id_order,id_dish) VALUES (?,?)";
    private static final String SELECT_USER_ORDERS = "SELECT order.idorder, order.restaurantId , order.userId, " +
            "order.amount, order.address, order.phone, order.dateTime, status.name as 'statusName' FROM fooddelivery.order " +
            "JOIN fooddelivery.status ON order.userId=? AND order.statusId=status.idstatus";
    private static final String SELECT_ORDER_DISHES = "SELECT dish.idDish, dish.name, dish.cost " +
            "FROM fooddelivery.dish JOIN fooddelivery.order_dish ON order_dish.id_order=? AND order_dish.id_dish=dish.idDish";
    private static final String SELECT_ORDERS_BY_STATE = "SELECT order.idorder, order.restaurantId , order.userId, " +
            "order.amount, order.address, order.phone, order.dateTime, status.name as 'statusName' FROM fooddelivery.order JOIN fooddelivery.status ON order.statusId=? AND order.statusId=status.idstatus AND order.restaurantId = ?";
    private static final String UPDATE_ORDER_STATE = "UPDATE fooddelivery.order SET order.statusId=? WHERE order.idorder=?";
    private static final String SELECT_ORDER = "Select order.idorder, order.restaurantId , order.userId, " +
            "order.amount, order.address, order.phone, order.dateTime, status.name as 'statusName' FROM fooddelivery.order JOIN fooddelivery.status ON order.statusId = status.idstatus AND order.idorder=?";
    private DAOFactory factory;

    public MySQLOrderDAO(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public Order findOrder(int orderId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Order order = null;
        try {
            Object[] values = {orderId};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_ORDER, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt("idorder"));
                order.setRestaurant(resultSet.getInt("restaurantId"));
                order.setUser(resultSet.getInt("userId"));
                order.setAmount(resultSet.getInt("amount"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setDatetime(resultSet.getTimestamp("dateTime"));
                order.setStatus(resultSet.getString("statusName"));
                order.setFoods(getDishes(order.getId(), connection));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return order;
    }

    @Override
    public void updateState(int orderId, int stateCode) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {stateCode, orderId};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, UPDATE_ORDER_STATE, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public LinkedList<Order> findOrders(int state, int idRestaurant) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        LinkedList<Order> orders;
        try {
            Object[] values = {state, idRestaurant};
            connection = factory.createConnection();
            orders = new LinkedList<>();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_ORDERS_BY_STATE, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("idorder"));
                order.setRestaurant(resultSet.getInt("restaurantId"));
                order.setUser(resultSet.getInt("userId"));
                order.setAmount(resultSet.getInt("amount"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setDatetime(resultSet.getTimestamp("dateTime"));
                order.setStatus(resultSet.getString("statusName"));
                order.setFoods(getDishes(order.getId(), connection));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return orders;
    }

    @Override
    public LinkedList<Order> findOrders(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        LinkedList<Order> orders;
        try {
            Object[] values = {user.getId()};
            connection = factory.createConnection();
            orders = new LinkedList<>();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_USER_ORDERS, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("idorder"));
                order.setRestaurant(resultSet.getInt("restaurantId"));
                order.setUser(resultSet.getInt("userId"));
                order.setAmount(resultSet.getInt("amount"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setDatetime(resultSet.getTimestamp("dateTime"));
                order.setStatus(resultSet.getString("statusName"));
                order.setFoods(getDishes(order.getId(), connection));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return orders;
    }

    private List<Dish> getDishes(int orderId, Connection connection) throws DAOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Dish> dishes;
        try {
            Object[] values = {orderId};
            dishes = new LinkedList<>();
            preparedStatement = connection.prepareStatement(SELECT_ORDER_DISHES);
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_ORDER_DISHES, false, values);
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("idDish"));
                dish.setCost(resultSet.getInt("cost"));
                dish.setName(resultSet.getString("name"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(preparedStatement);
            DaoUtils.close(resultSet);
        }
        return dishes;
    }

    public boolean insertOrder(Order order) throws DAOException {
        boolean transaktion = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int orderId = 0;
        try {
            Object[] values = {order.getRestaurant(), order.getUser(), order.getAmount(), order.getAddress(), order.getPhone(), order.getDatetime(), order.getStatus()};
            connection = factory.createConnection();
            connection.setAutoCommit(false);
            preparedStatement = DaoUtils.prepareStatement(connection, INSERT_ORDER, true, values);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                orderId = resultSet.getInt(1);
            }
            for (Dish dish : order.getFoods()) {
                insertOrderDish(orderId, dish.getId(), connection);
            }
            connection.commit();
            transaktion = true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new DAOException(e1);
            } finally {
                DaoUtils.close(connection, preparedStatement);
                transaktion = false;
            }
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
            return transaktion;
        }
    }

    private void insertOrderDish(int orderId, int dishId, Connection connection) throws DAOException {
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {orderId, dishId};
            preparedStatement = DaoUtils.prepareStatement(connection, INSERT_ORDER_DISH, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(preparedStatement);
        }
    }
}
