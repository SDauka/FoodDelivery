package com.epam.sultangazy.webapp.action.actions.user;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.dao.exception.CannotTakeConnectionException;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class CheckOrderPageAction implements Action {
    private static final String ATTR_NAME_PREPARED_DISHES = "prepared_dishes";
    private static final String ATTR_NAME_ERROR = "checkOrderErrorMessage";
    private static final String ATTR_NAME_RESTAURANT_INFO = "restaurantInfo";
    private static final String ATTR_NAME_RESTAURANT_MENU = "restaurantMenu";
    private static final String ATTR_NAME_RESTAURANT_CATEGORY = "restaurantCategories";
    private static final String ATTR_NAME_DISH_CATEGORY = "dcategory";
    private static final String PARAM_NAME_RESTAURANT_ID = "restaurantId";
    private static final String PARAM_NAME_CATEGORY = "category";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String MENU_PAGE = propertyReader.getProperties("menuPage.page");
    private final String ORDER_CHECK_PAGE = propertyReader.getProperties("orderCheck.page");
    private DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
    private MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();
    private MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws CannotTakeConnectionException, DAOException, SQLException {
        HttpSession session = req.getSession();
        int idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        Restaurant restaurant = mySQLRestaurantDAO.selectRestaurantByID(idRestaurant);
        List<Dish> preparedDishes = (List<Dish>) session.getAttribute("prepared_dishes");
        if (null == session.getAttribute(ATTR_NAME_PREPARED_DISHES) || preparedDishes.isEmpty()) {
            req.setAttribute(ATTR_NAME_ERROR, "o.cartEmpty");
            showMenu(req);
            return new ActionResult(MENU_PAGE, false);
        } else {
            req.setAttribute(ATTR_NAME_RESTAURANT_INFO, restaurant);
            return new ActionResult(ORDER_CHECK_PAGE, false);
        }
    }

    public void showMenu(HttpServletRequest req) throws DAOException, SQLException {
        List<Dish> dishes;
        int idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        String category = null;
        try {
            category = new String(new String(req.getParameter(PARAM_NAME_CATEGORY).getBytes("iso-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Restaurant restaurant = mySQLRestaurantDAO.selectRestaurantByID(idRestaurant);
        HashSet<String> categories = mySQLDishDAO.selectRestaurantDishCategories(idRestaurant);
        if (category.equals("all")) {
            dishes = mySQLDishDAO.findDishesByRestaurantID(idRestaurant);
        } else dishes = mySQLDishDAO.selectMenuByCategory(idRestaurant, category);
        req.setAttribute(ATTR_NAME_RESTAURANT_CATEGORY, categories);
        req.setAttribute(ATTR_NAME_RESTAURANT_MENU, dishes);
        req.setAttribute(ATTR_NAME_RESTAURANT_INFO, restaurant);
        req.setAttribute(ATTR_NAME_DISH_CATEGORY, category);
    }
}
