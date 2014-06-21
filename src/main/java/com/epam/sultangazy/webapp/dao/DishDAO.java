package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.Dish;

import java.util.HashSet;
import java.util.List;

public interface DishDAO {
    List<Dish> findDishesByRestaurantID(int id) throws DAOException;

    Dish selectDishByID(int id) throws DAOException;

    List<String> selectCategories() throws DAOException;

    boolean updateDish(Dish dish) throws DAOException;

    void insertDish(Dish dish, int idRestaurant) throws DAOException;

    void deleteDish(int id) throws DAOException;

    HashSet<String> selectRestaurantDishCategories(int idRestaurant) throws DAOException;

    List<Dish> selectMenuByCategory(int idRestaurant, String categories) throws DAOException;
}
