package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

public class RestaurantOrderListAction implements Action {
    private static final String PARAM_NAME_STATUS = "status";
    private static final String ATTR_NAME_NEW_RESTAURANT_ORDERS = "RestaurantNewOrders";
    private static final String ATTR_NAME_RESTAURANT_ORDERS = "RestaurantOrders";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String RESTAURANT_PAGE = propertyReader.getProperties("restoratorPage.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        ActionResult actionResult = null;
        LinkedList<Order> orders;
        Restaurant restaurant = (Restaurant) req.getSession().getAttribute("restaurant");
        int status = Integer.parseInt(req.getParameter(PARAM_NAME_STATUS));
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLOrderDAO mySQLOrderDAO = (MySQLOrderDAO) factory.getOrderDAO();
        switch (status) {
            case 1:
                orders = mySQLOrderDAO.findOrders(status, restaurant.getId());
                req.setAttribute(ATTR_NAME_NEW_RESTAURANT_ORDERS, orders);
                actionResult = new ActionResult(RESTAURANT_PAGE, false);
                break;
            case 2:
                orders = mySQLOrderDAO.findOrders(status, restaurant.getId());
                req.setAttribute(ATTR_NAME_RESTAURANT_ORDERS, orders);
                actionResult = new ActionResult(RESTAURANT_PAGE, false);
                break;
            case 3:
                orders = mySQLOrderDAO.findOrders(status, restaurant.getId());
                req.setAttribute(ATTR_NAME_RESTAURANT_ORDERS, orders);
                actionResult = new ActionResult(RESTAURANT_PAGE, false);
        }
        return actionResult;
    }

}
