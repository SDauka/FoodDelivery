package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRestaurants implements Action {
    private static final String ATTR_NAME_RESTAURANTS = "restaurants";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String RESSTAURANTS_PAGE = propertyReader.getProperties("restaurants.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        RestaurantDAO mySQLRestaurantDAO = factory.getRestaurantDAO();
        List<Restaurant> restaurants;
        restaurants = mySQLRestaurantDAO.findRestaurants();
        req.setAttribute(ATTR_NAME_RESTAURANTS, restaurants);
        return new ActionResult(RESSTAURANTS_PAGE, false);
    }
}
