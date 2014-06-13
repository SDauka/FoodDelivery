package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
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
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
        List<String> categories = mySQLDishDAO.selectCategories();
        req.setAttribute("categories", categories);
        return new ActionResult(ADD_DISH_PAGE, false);
    }
}
