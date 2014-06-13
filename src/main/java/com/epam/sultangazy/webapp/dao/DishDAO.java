package com.epam.sultangazy.webapp.dao;

import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.entity.Dish;

import java.util.HashSet;
import java.util.List;

public interface DishDAO {
    public List<Dish> findDishesByRestaurantID(int id) throws DAOException;

    public Dish selectDishByID(int id) throws DAOException;

    public List<String> selectCategories() throws DAOException;

    public boolean updateDish(Dish dish) throws DAOException;

    public void insertDish(Dish dish, int idRestaurant) throws DAOException;

    public void deleteDish(int id) throws DAOException;

    public HashSet<String> selectRestaurantDishCategories(int idRestaurant) throws DAOException;

    public List<Dish> selectMenuByCategory(int idRestaurant, String categories) throws DAOException;
}
