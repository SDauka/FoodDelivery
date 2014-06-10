package com.epam.sultangazy.webapp.dao.mysql;


import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.dao.DishDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.DaoUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MySQLDishDAO implements DishDAO {
    private static final String SELECT_DISHES_BY_RESTORATOR_ID = "Select dish.idDish, dish.name, dish.ingredients, dish.weight, dish.cost, dish.image, " +
            "categories.name as 'categories'  from fooddelivery.categories, fooddelivery.dish where id_categories = categories.idcategories and dish.id_restoran =? and dish.deleted not like 'true'";
    private static final String SELECT_CATEGORIES = "Select *from fooddelivery.categories";
    private static final String INSERT_DISH = "insert into fooddelivery.dish (name, ingredients, weight, cost, image, id_categories, id_restoran, deleted) " +
            "values (?,?,?,?,?,(select idcategories from fooddelivery.categories where categories.name = ?),?, 'false')";
    private static final String SELECT_DISH_BY_ID = "Select dish.idDish, dish.name, dish.ingredients, dish.weight, dish.cost, dish.image, " +
            "categories.name as 'categories'  from fooddelivery.categories, fooddelivery.dish where categories.idcategories = id_categories  and dish.idDish = ? and dish.deleted not like 'true'";
    private static final String UPDATE_DISH = "update fooddelivery.dish set dish.name = ? , dish.ingredients = ? , dish.weight = ?, dish.cost = ?, dish.image = ?  where dish.idDish = ?";
    private static final String DELETE_DISH_BY_ID = "update fooddelivery.dish set dish.deleted = 'true', dish.image = 'none' WHERE dish.idDish =?";
    private static final String SELECT_RESTAURANT_CATEGORIES = "select categories.name from fooddelivery.categories, fooddelivery.dish where fooddelivery.categories.idcategories = fooddelivery.dish.id_categories and fooddelivery.dish.id_restoran = ? and dish.deleted not like 'true' ";
    private static final String SELECT_DISHES_BY_CATEGORIES = "Select dish.idDish, dish.name, dish.ingredients, dish.weight, " +
            "dish.cost, dish.image, categories.name as 'categories'  from fooddelivery.categories, " +
            "fooddelivery.dish where id_categories = categories.idcategories and categories.name = ? and dish.id_restoran =? and dish.deleted not like 'true'";
    private DAOFactory factory;

    public MySQLDishDAO(DAOFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Dish> selectMenuByCategory(int idRestaurant, String categories) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Dish> dishes;
        try {
            Object[] values = {categories, idRestaurant};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_DISHES_BY_CATEGORIES, false, values);
            resultSet = preparedStatement.executeQuery();
            dishes = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setId(resultSet.getInt("idDish"));
                    dish.setName(resultSet.getString("name"));
                    dish.setImage(resultSet.getString("image"));
                    dish.setCategories(resultSet.getString("categories"));
                    dish.setCost(resultSet.getInt("cost"));
                    dish.setWeight(resultSet.getInt("weight"));
                    dish.setIngredients(resultSet.getString("ingredients"));
                    dishes.add(dish);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return dishes;
    }

    @Override
    public HashSet<String> selectRestaurantDishCategories(int idRestaurant) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HashSet<String> categories;
        try {
            Object[] values = {idRestaurant};
            connection = factory.createConnection();
            categories = new HashSet<>();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_RESTAURANT_CATEGORIES, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    categories.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return categories;
    }

    @Override
    public void deleteDish(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, DELETE_DISH_BY_ID, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public boolean updateDish(Dish dish) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {dish.getName(), dish.getIngredients(), dish.getWeight(), dish.getCost(), dish.getImage(), dish.getId()};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, UPDATE_DISH, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
            return true;
        }
    }

    @Override
    public Dish selectDishByID(int id) throws DAOException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Dish dish;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            dish = new Dish();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_DISH_BY_ID, false, values);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                dish.setId(resultSet.getInt("idDish"));
                dish.setName(resultSet.getString("name"));
                dish.setIngredients(resultSet.getString("ingredients"));
                dish.setWeight(resultSet.getInt("weight"));
                dish.setCost(resultSet.getInt("cost"));
                dish.setImage(resultSet.getString("image"));
                dish.setCategories(resultSet.getString("categories"));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return dish;
    }

    @Override
    public void insertDish(Dish dish, int idRestaurant) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Object[] values = {dish.getName(), dish.getIngredients(), dish.getWeight(), dish.getCost(), dish.getImage(), dish.getCategories(), idRestaurant};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, INSERT_DISH, false, values);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement);
        }
    }

    @Override
    public List<String> selectCategories() throws DAOException, SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> categories;
        try {
            connection = factory.createConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_CATEGORIES);
            categories = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    categories.add(resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionPool.getInstance().setFreeConnection(connection);
            statement.close();
            resultSet.close();
        }
        return categories;
    }

    @Override
    public List<Dish> findDishesByRestaurantID(int id) throws DAOException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Dish> dishes;
        try {
            Object[] values = {id};
            connection = factory.createConnection();
            preparedStatement = DaoUtils.prepareStatement(connection, SELECT_DISHES_BY_RESTORATOR_ID, false, values);
            resultSet = preparedStatement.executeQuery();
            dishes = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Dish dish = new Dish();
                    dish.setId(resultSet.getInt("idDish"));
                    dish.setName(resultSet.getString("name"));
                    dish.setImage(resultSet.getString("image"));
                    dish.setCategories(resultSet.getString("categories"));
                    dish.setCost(resultSet.getInt("cost"));
                    dish.setWeight(resultSet.getInt("weight"));
                    dish.setIngredients(resultSet.getString("ingredients"));
                    dishes.add(dish);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            DaoUtils.close(connection, preparedStatement, resultSet);
        }
        return dishes;
    }
}
