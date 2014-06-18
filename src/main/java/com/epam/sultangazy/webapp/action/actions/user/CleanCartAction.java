package com.epam.sultangazy.webapp.action.actions.user;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;

public class CleanCartAction implements Action {
    private static final String ATTR_NAME_DISH_COUNT = "dcount";
    private static final String ATTR_NAME_PREPARED_DISHES = "prepared_dishes";
    private static final String ATTR_NAME_DISH_COST = "dcost";
    private static final String ATTR_NAME_AMOUNT = "oAmount";
    private static final String ATTR_NAME_RESTAURANT_INFO = "restaurantInfo";
    private static final String ATTR_NAME_RESTAURANT_MENU = "restaurantMenu";
    private static final String ATTR_NAME_RESTAURANT_CATEGORY = "restaurantCategories";
    private static final String ATTR_NAME_DISH_CATEGORY = "dcategory";
    private static final String PARAM_NAME_RESTAURANT_ID = "restaurantId";
    private static final String PARAM_NAME_CATEGORY = "category";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String MENU_PAGE = propertyReader.getProperties("menuPage.page");
    private MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
    private MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
    private MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        List<Dish> dishes;
        String category;
        int idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        try {
            category = new String(new String(req.getParameter(PARAM_NAME_CATEGORY).getBytes("iso-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ActionResult(MENU_PAGE, false);
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
        cleanCart(req);
        return new ActionResult(MENU_PAGE, false);
    }

    public static void cleanCart(HttpServletRequest req) {
        req.getSession().removeAttribute(ATTR_NAME_DISH_COST);
        req.getSession().removeAttribute(ATTR_NAME_DISH_COUNT);
        req.getSession().removeAttribute(ATTR_NAME_AMOUNT);
        req.getSession().removeAttribute(ATTR_NAME_PREPARED_DISHES);
    }
}
