package com.epam.sultangazy.webapp.action.actions.user;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DishDAO;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class RemoveFromOrderAction implements Action {
    private static final String ATTR_NAME_DISH_COUNT = "dcount";
    private static final String PARAM_NAME_DISH_NAME = "dishName";
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
    private DishDAO mySQLDishDAO = factory.getDishDAO();
    private RestaurantDAO mySQLRestaurantDAO = factory.getRestaurantDAO();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        HttpSession session = req.getSession();
        String name = req.getParameter(PARAM_NAME_DISH_NAME);
        LinkedHashMap<String, Integer> dcount = (LinkedHashMap<String, Integer>) session.getAttribute(ATTR_NAME_DISH_COUNT);
        LinkedHashMap<String, Integer> dcost = (LinkedHashMap<String, Integer>) session.getAttribute(ATTR_NAME_DISH_COST);
        List<Dish> preparedDishes = (List<Dish>) session.getAttribute(ATTR_NAME_PREPARED_DISHES);
        int count = dcount.get(name);
        int cost = dcost.get(name) / count;
        remove(req.getParameter(PARAM_NAME_DISH_NAME), preparedDishes);
        if (count == 1) {
            dcount.remove(name);
            dcost.remove(name);
        } else {
            dcount.put(name, --count);
            dcost.put(name, dcost.get(name) - cost);
        }
        int amount = AddToOrderAction.getAmount(preparedDishes);
        showMenu(req);
        session.setAttribute(ATTR_NAME_PREPARED_DISHES, preparedDishes);
        session.setAttribute(ATTR_NAME_DISH_COST, dcost);
        session.setAttribute(ATTR_NAME_DISH_COUNT, dcount);
        session.setAttribute(ATTR_NAME_AMOUNT, amount);
        return new ActionResult(MENU_PAGE, false);
    }

    private void showMenu(HttpServletRequest req) throws DAOException {
        List<Dish> dishes;
        int idRestaurant = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        String category = req.getParameter(PARAM_NAME_CATEGORY);
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

    private static void remove(String name, List<Dish> dishes) {
        for (Dish dish : dishes) {
            if (dish.getName().equals(name)) {
                dishes.remove(dish);
                break;
            }
        }
    }
}
