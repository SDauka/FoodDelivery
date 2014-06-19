package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DishDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddDishPageAction implements Action {
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String ADD_DISH_PAGE = propertyReader.getProperties("addDish.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        DishDAO mySQLDishDAO = factory.getDishDAO();
        List<String> categories = mySQLDishDAO.selectCategories();
        req.setAttribute("categories", categories);
        return new ActionResult(ADD_DISH_PAGE, false);
    }
}
