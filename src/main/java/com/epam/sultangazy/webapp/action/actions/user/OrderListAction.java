package com.epam.sultangazy.webapp.action.actions.user;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;

public class OrderListAction implements Action {
    private static final String ATTR_NAME_USER = "user";
    private static final String ATTR_NAME_USER_ORDERS = "userOrders";
    private static final String ATTR_NAME_USER_REST_NAME = "restaurantsName";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String USER_PAGE = propertyReader.getProperties("userProfile.page");
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorPage.page");
    private final String ADMIN_PAGE = propertyReader.getProperties("adminProfile.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        ActionResult actionResult = null;
        LinkedList<Order> orders;
        HashMap<Integer, String> restaurants;
        User user = (User) req.getSession().getAttribute(ATTR_NAME_USER);
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLOrderDAO mySQLOrderDAO = (MySQLOrderDAO) factory.getOrderDAO();
        MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();
        switch (user.getRole()) {
            case USER:
                orders = mySQLOrderDAO.findOrders(user);
                restaurants = mySQLRestaurantDAO.selectRestaurantsName();
                req.setAttribute(ATTR_NAME_USER_ORDERS, orders);
                req.setAttribute(ATTR_NAME_USER_REST_NAME, restaurants);
                actionResult = new ActionResult(USER_PAGE, false);
                break;
            case RESTORATOR:
                orders = mySQLOrderDAO.findOrders(user);
                restaurants = mySQLRestaurantDAO.selectRestaurantsName();
                req.setAttribute(ATTR_NAME_USER_ORDERS, orders);
                req.setAttribute(ATTR_NAME_USER_REST_NAME, restaurants);
                actionResult = new ActionResult(RESTORATOR_PAGE, false);
                break;
            case ADMIN:
                orders = mySQLOrderDAO.findOrders(user);
                restaurants = mySQLRestaurantDAO.selectRestaurantsName();
                req.setAttribute(ATTR_NAME_USER_ORDERS, orders);
                req.setAttribute(ATTR_NAME_USER_REST_NAME, restaurants);
                actionResult = new ActionResult(ADMIN_PAGE, false);
        }
        return actionResult;
    }
}
