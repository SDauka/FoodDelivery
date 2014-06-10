package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.dao.exception.DAOException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface RestaurantDAO {
    public List<Restaurant> findRestaurants() throws DAOException, SQLException;

    public void deleteRestaurantById(int id, int idrestorator) throws DAOException;

    public Restaurant selectRestaurantByID(int id) throws DAOException;

    public boolean updateRestaurant(Restaurant restaurant) throws DAOException;

    public Restaurant selectRestaurantByRestoratorID(int id) throws DAOException;

    public boolean checkRestorator(int id) throws DAOException;

    public void insertRestaurant(Restaurant restaurant, int restorator) throws DAOException;

    public HashMap<Integer, String> selectRestaurantsName() throws DAOException, SQLException;
}
