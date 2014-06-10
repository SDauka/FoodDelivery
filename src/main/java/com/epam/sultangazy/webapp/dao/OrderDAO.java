package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.dao.exception.DAOException;

import java.util.List;

public interface OrderDAO {
    public boolean insertOrder(Order order) throws DAOException;

    public List<Order> findOrders(int state, int idRestaurant) throws DAOException;

    public List<Order> findOrders(User user) throws DAOException;

    public Order findOrder(int orderId) throws DAOException;

    public void updateState(int orderId, int stateCode) throws DAOException;
}
