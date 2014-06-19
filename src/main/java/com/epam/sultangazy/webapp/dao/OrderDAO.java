package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.User;

import java.util.LinkedList;

public interface OrderDAO {
    public boolean insertOrder(Order order) throws DAOException;

    public LinkedList<Order> findOrders(int state, int idRestaurant) throws DAOException;

    public LinkedList<Order> findOrders(User user) throws DAOException;

    public Order findOrder(int orderId) throws DAOException;

    public void updateState(int orderId, int stateCode) throws DAOException;
}
