package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.User;

import java.util.List;

public interface OrderDAO {
    boolean insertOrder(Order order) throws DAOException;

    List<Order> findOrders(int state, int idRestaurant) throws DAOException;

    List<Order> findOrders(User user) throws DAOException;

    Order findOrder(int orderId) throws DAOException;

    void updateState(int orderId, int stateCode) throws DAOException;
}
