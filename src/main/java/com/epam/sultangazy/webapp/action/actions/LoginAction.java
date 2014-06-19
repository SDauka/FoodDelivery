package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.dao.UserDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import org.apache.log4j.Logger;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction implements Action {
    private static final Logger LOG = Logger.getLogger(LoginAction.class);
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String ATTR_NAME_ERROR_MESSAGE = "errorMessage";
    private static final String ATTR_NAME_RESTAURANT = "restaurant";
    private static final String ATTR_NAME_USER = "user";
    private final String LOGIN_PAGE = propertyReader.getProperties("welcome.page");
    private final String RESTAURANTS_ACTION = propertyReader.getProperties("restaurants.action");

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String login = request.getParameter(PARAM_NAME_EMAIL);
        String password = request.getParameter(PARAM_NAME_PASSWORD);
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        UserDAO userDAO = factory.getUserDAO();
        User user;
        user = userDAO.findUser(login);
        if (user == null || !passwordEncryptor.checkPassword(password, user.getPassword())) {
            request.setAttribute(ATTR_NAME_ERROR_MESSAGE, "error.invalid.login.password");
            LOG.debug(login + ": not authorized, invalid login or password.");
            return new ActionResult(LOGIN_PAGE, false);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(ATTR_NAME_USER, user);
            User sessionUser = (User) session.getAttribute(ATTR_NAME_USER);
            int restoratorID = sessionUser.getId();
            RestaurantDAO restaurantDAO = factory.getRestaurantDAO();
            if (restaurantDAO.checkRestorator(restoratorID)) {
                Restaurant restaurant;
                restaurant = restaurantDAO.selectRestaurantByRestoratorID(restoratorID);
                session.setAttribute(ATTR_NAME_RESTAURANT, restaurant);
            }
            LOG.debug(login + ": authorized.");
            return new ActionResult(RESTAURANTS_ACTION, true);
        }
    }

}
