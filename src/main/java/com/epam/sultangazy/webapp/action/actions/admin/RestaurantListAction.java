package com.epam.sultangazy.webapp.action.actions.admin;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLUserDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class RestaurantListAction implements Action {
    private static final String ATTR_NAME_DS_LIST = "deliveryServicesList";
    private static final String ATTR_NAME_RESTAURANTS_RESTORATOR = "restaurantRestorators";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String ADMIN_PROFILE = propertyReader.getProperties("adminProfile.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLUserDAO userDAO = (MySQLUserDAO) factory.getUserDAO();
        MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();
        Map<Integer, User> users = userDAO.findRestorators();
        List<Restaurant> restaurants = mySQLRestaurantDAO.findRestaurants();
        req.setAttribute(ATTR_NAME_DS_LIST, restaurants);
        req.setAttribute(ATTR_NAME_RESTAURANTS_RESTORATOR, users);
        return new ActionResult(ADMIN_PROFILE, false);
    }
}
