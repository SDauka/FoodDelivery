package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.Restaurant;

import java.util.HashMap;
import java.util.List;

public interface RestaurantDAO {
    List<Restaurant> findRestaurants() throws DAOException;

    void deleteRestaurantById(int id, int idrestorator) throws DAOException;

    Restaurant selectRestaurantByID(int id) throws DAOException;

    boolean updateRestaurant(Restaurant restaurant) throws DAOException;

    Restaurant selectRestaurantByRestoratorID(int id) throws DAOException;

    boolean checkRestorator(int id) throws DAOException;

    void insertRestaurant(Restaurant restaurant, int restorator) throws DAOException;

    HashMap<Integer, String> selectRestaurantsName() throws DAOException;
}
