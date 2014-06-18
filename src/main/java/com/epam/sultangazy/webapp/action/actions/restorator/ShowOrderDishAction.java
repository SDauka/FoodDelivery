package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Order;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class ShowOrderDishAction implements Action {
    private static final String PARAM_NAME_ORDER_ID = "idOrder";
    private static final String ATR_NAME_ORDER_DISHES = "orderDishes";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String ORDER_DISHES_PAGE = propertyReader.getProperties("requestOrderDishes.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        HashMap<String, Integer> orderDishes;
        Order order;
        int orderId = Integer.parseInt(req.getParameter(PARAM_NAME_ORDER_ID));
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        MySQLOrderDAO mySQLOrderDAO = (MySQLOrderDAO) factory.getOrderDAO();
        order = mySQLOrderDAO.findOrder(orderId);
        orderDishes = evaluateOrderDishes(order);
        req.setAttribute(ATR_NAME_ORDER_DISHES, orderDishes);
        req.setAttribute(PARAM_NAME_ORDER_ID, order.getId());
        return new ActionResult(ORDER_DISHES_PAGE, false);
    }

    private HashMap<String, Integer> evaluateOrderDishes(Order order) {
        HashMap<String, Integer> evaluatedDishes = new HashMap<>();
        for (Dish dish : order.getFoods()) {
            Integer count = evaluatedDishes.get(dish.getName());
            int value = (count == null) ? 0 : count;
            evaluatedDishes.put(dish.getName(), ++value);
        }
        return evaluatedDishes;
    }
}
