package com.epam.sultangazy.webapp.action.actions.user;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CreateOrderAction implements Action {
    private static final Logger LOG = Logger.getLogger(CreateOrderAction.class);
    private static final String PARAM_NAME_ADDRESS = "address";
    private static final String PARAM_NAME_PHONE = "phone";
    private static final String ATTR_NAME_PREPARED_DISHES = "prepared_dishes";
    private static final String ATTR_NAME_USER = "user";
    private static final String ATTR_NAME_AMOUNT = "oAmount";
    private static final String ATTR_NAME_RESTAURANT_INFO = "restaurantInfo";
    private static final String FIELD_VALUE = "queue";
    private static final String PARAM_NAME_RESTAURANT_ID = "restaurantId";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String ORDER_REQUEST_PAGE = propertyReader.getProperties("orderCheck.page");
    private final String ORDER_CONFIRM_PAGE = propertyReader.getProperties("orderConfirm");
    private Order order;
    private int idRestaurant;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLOrderDAO mySQLOrderDAO = (MySQLOrderDAO) factory.getOrderDAO();
        MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();
        idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        Restaurant restaurant = mySQLRestaurantDAO.selectRestaurantByID(idRestaurant);
        setOrder(req);
        LOG.debug("Order request is started");
        if (!mySQLOrderDAO.insertOrder(order)) {
            LOG.debug("Order request is failed");
            req.setAttribute("requestOrderErrorMessage", "error.requesOrder");
            req.setAttribute(ATTR_NAME_RESTAURANT_INFO, restaurant);
            return new ActionResult(ORDER_REQUEST_PAGE, false);
        } else {
            LOG.debug("Order request is finished");
            CleanCartAction.cleanCart(req);
            return new ActionResult(ORDER_CONFIRM_PAGE, true);
        }
    }

    public void setOrder(HttpServletRequest req) {
        order = new Order();
        List<Dish> food = (List<Dish>) req.getSession().getAttribute(ATTR_NAME_PREPARED_DISHES);
        User user = (User) req.getSession().getAttribute(ATTR_NAME_USER);
        int userId = user.getId();
        String uAddress = req.getParameter(PARAM_NAME_ADDRESS);
        String uPhone = req.getParameter(PARAM_NAME_PHONE);
        int amount = (Integer) req.getSession().getAttribute(ATTR_NAME_AMOUNT);
        order.setRestaurant(idRestaurant);
        order.setAddress(uAddress);
        order.setUser(userId);
        order.setPhone(uPhone);
        order.setAmount(amount);
        order.setDatetime(new Timestamp(new Date().getTime()));
        order.setFoods(food);
        order.setStatus(FIELD_VALUE);
    }
}
