package com.epam.sultangazy.webapp.action.actions.admin;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLUserDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UserListAction implements Action {
    private static final String ATTR_NAME_USERS_LIST = "userstList";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String ADMIN_PROFILE = propertyReader.getProperties("adminProfile.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        MySQLUserDAO userDAO = (MySQLUserDAO) factory.getUserDAO();
        List<User> users = userDAO.findUsers();
        req.setAttribute(ATTR_NAME_USERS_LIST, users);
        return new ActionResult(ADMIN_PROFILE, false);
    }
}
