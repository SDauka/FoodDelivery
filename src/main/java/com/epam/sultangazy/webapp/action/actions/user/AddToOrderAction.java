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
import java.util.*;

public class AddToOrderAction implements Action {
    private static final String ATTR_NAME_DISH_COUNT = "dcount";
    private static final String PARAM_NAME_DISH_ID = "dishId";
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
        LinkedHashMap<String, Integer> dcount = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> dcost = new LinkedHashMap<>();
        Dish dish;
        dish = mySQLDishDAO.selectDishByID(Integer.parseInt(req.getParameter(PARAM_NAME_DISH_ID)));
        String name = dish.getName();
        int cost = dish.getCost();
        List<Dish> preparedDishes = new ArrayList<>();
        if (null == session.getAttribute(ATTR_NAME_DISH_COUNT) && null == session.getAttribute(ATTR_NAME_PREPARED_DISHES)) {
            dcount.put(name, 1);
            dcost.put(name, cost);
        } else {
            dcount.putAll((Map<String, Integer>) session.getAttribute(ATTR_NAME_DISH_COUNT));
            preparedDishes.addAll((List<Dish>) session.getAttribute(ATTR_NAME_PREPARED_DISHES));
            dcost.putAll((Map<String, Integer>) session.getAttribute(ATTR_NAME_DISH_COST));
            if (dcount.containsKey(name) && dcost.containsKey(name)) {
                int count = dcount.get(name);
                dcount.put(name, ++count);
                dcost.put(name, dcost.get(name) + cost);
            } else {
                dcount.put(name, 1);
                dcost.put(name, cost);
            }
        }
        preparedDishes.add(dish);
        int amount = getAmount(preparedDishes);
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

    public static int getAmount(List<Dish> preparedDishes) {
        int amount = 0;
        for (Dish dish : preparedDishes) {
            amount += dish.getCost();
        }
        return amount;
    }
}

