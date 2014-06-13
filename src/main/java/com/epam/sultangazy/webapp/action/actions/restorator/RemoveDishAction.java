package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

public class RemoveDishAction implements Action {
    private static final String ATTR_NAME_RESTAURANT_DISHES = "restaurant_dishes";
    private static final String ATTR_NAME_RESTAURANT = "restaurant";
    private static final String PARAM_NAME_DISH_ID = "idDish";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorPage.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        PropertyReader imagePathPropertyReader = new PropertyReader(PropertyReader.IMAGE_PATH);
        String imagePath = imagePathPropertyReader.getProperties("dishesView");
        HttpSession session = req.getSession();
        Restaurant sessionRestaurant = (Restaurant) session.getAttribute(ATTR_NAME_RESTAURANT);
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
        Dish dish = mySQLDishDAO.selectDishByID(Integer.parseInt(req.getParameter(PARAM_NAME_DISH_ID)));
        String oldImage = dish.getImage().substring(7);
        new File(imagePath + File.separator + oldImage).delete();
        mySQLDishDAO.deleteDish(Integer.parseInt(req.getParameter(PARAM_NAME_DISH_ID)));
        List<Dish> dishes;
        dishes = mySQLDishDAO.findDishesByRestaurantID(sessionRestaurant.getId());
        req.setAttribute(ATTR_NAME_RESTAURANT_DISHES, dishes);
        return new ActionResult(RESTORATOR_PAGE, false);
    }
}
