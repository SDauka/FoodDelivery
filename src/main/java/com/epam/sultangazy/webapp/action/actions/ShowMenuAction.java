package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;

public class ShowMenuAction implements Action {
    private static final String ATTR_NAME_RESTAURANT_INFO = "restaurantInfo";
    private static final String ATTR_NAME_RESTAURANT_MENU = "restaurantMenu";
    private static final String ATTR_NAME_RESTAURANT_CATEGORY = "restaurantCategories";
    private static final String PARAM_NAME_RESTAURANT_ID = "restaurantId";
    private static final String ATTR_NAME_DISH_CATEGORY = "dcategory";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String MENU_PAGE = propertyReader.getProperties("menuPage.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        int idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();
        MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
        Restaurant restaurant = mySQLRestaurantDAO.selectRestaurantByID(idRestaurant);
        List<Dish> dishes = mySQLDishDAO.findDishesByRestaurantID(idRestaurant);
        HashSet<String> categories = mySQLDishDAO.selectRestaurantDishCategories(idRestaurant);
        req.setAttribute(ATTR_NAME_RESTAURANT_CATEGORY, categories);
        req.setAttribute(ATTR_NAME_RESTAURANT_MENU, dishes);
        req.setAttribute(ATTR_NAME_RESTAURANT_INFO, restaurant);
        req.setAttribute(ATTR_NAME_DISH_CATEGORY, "all");
        return new ActionResult(MENU_PAGE, false);
    }
}
