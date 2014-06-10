package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
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
import java.sql.SQLException;
import java.util.List;

public class ShowDishesAction implements Action {
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorPage.page");
    private static final String ATTR_NAME_RESTAURANT_DISHES = "restaurant_dishes";
    private static final String ATTR_NAME_RESTAURANT = "restaurant";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws CannotTakeConnectionException, DAOException, SQLException {
        HttpSession session = req.getSession();
        Restaurant sessionRestaurant = (Restaurant) session.getAttribute(ATTR_NAME_RESTAURANT);
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
        List<Dish> dishes;
        dishes = mySQLDishDAO.findDishesByRestaurantID(sessionRestaurant.getId());
        req.setAttribute(ATTR_NAME_RESTAURANT_DISHES, dishes);
        return new ActionResult(RESTORATOR_PAGE, false);
    }
}
