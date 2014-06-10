package com.epam.sultangazy.webapp.action.actions;

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
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

public class PicksByCategoryAction implements Action {
    private static final String ATTR_NAME_RESTAURANT_MENU = "restaurantMenu";
    private static final String ATTR_NAME_RESTAURANT_INFO = "restaurantInfo";
    private static final String ATTR_NAME_RESTAURANT_CATEGORY = "restaurantCategories";
    private static final String PARAM_NAME_CATEGORY = "category";
    private static final String ATTR_NAME_DISH_CATEGORY = "dcategory";
    private static final String PARAM_NAME_REST = "rest";
    private static final String ALL = "all";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String MENU_PAGE = propertyReader.getProperties("menuPage.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws CannotTakeConnectionException, DAOException, SQLException {
        if (req.getParameter(PARAM_NAME_REST) != null && req.getParameter(PARAM_NAME_CATEGORY) != null) {
            int idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_REST));
            String category;
            try {
                category = new String(new String(req.getParameter(PARAM_NAME_CATEGORY).getBytes("iso-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new ActionResult(MENU_PAGE, false);
            }
            DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
            MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
            MySQLRestaurantDAO mySQLRestaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();
            List<Dish> dishes;
            if (category.equals(ALL)) {
                dishes = mySQLDishDAO.findDishesByRestaurantID(idRestaurant);
            } else dishes = mySQLDishDAO.selectMenuByCategory(idRestaurant, category);
            Restaurant restaurant = mySQLRestaurantDAO.selectRestaurantByID(idRestaurant);
            HashSet<String> categories = mySQLDishDAO.selectRestaurantDishCategories(idRestaurant);
            req.setAttribute(ATTR_NAME_RESTAURANT_MENU, dishes);
            req.setAttribute(ATTR_NAME_DISH_CATEGORY, category);
            req.setAttribute(ATTR_NAME_RESTAURANT_CATEGORY, categories);
            req.setAttribute(ATTR_NAME_RESTAURANT_INFO, restaurant);
            return new ActionResult(MENU_PAGE, false);
        } else return new ActionResult(MENU_PAGE, false);
    }

}
