package com.epam.sultangazy.webapp.action.actions.admin;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.UserDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AcceptRequestAction implements Action {
    private static final String PARAM_NAME_REQUEST_ID = "requestId";
    private static final String PARAM_NAME_LOGIN = "email";
    private static final String PARAM_NAME_PASSWORD = "password";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_ADDRESS = "address";
    private static final String PARAM_NAME_PHONE = "phone";
    private static final String ATTR_NAME_REQUESTS_LIST = "CooperationRequestList";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String ADMIN_PROFILE = propertyReader.getProperties("adminProfile.page");
    private MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
    private UserDAO userDAO;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        userDAO = factory.getUserDAO();
        int requestId = Integer.parseInt(req.getParameter(PARAM_NAME_REQUEST_ID));
        register(req);
        userDAO.deleteCooperationRequest(requestId);
        List<User> users = userDAO.findCooperationRequests();
        req.setAttribute(ATTR_NAME_REQUESTS_LIST, users);
        return new ActionResult(ADMIN_PROFILE, false);
    }

    private void register(HttpServletRequest req) throws DAOException {
        String email = req.getParameter(PARAM_NAME_LOGIN);
        String password = req.getParameter(PARAM_NAME_PASSWORD);
        String name = req.getParameter(PARAM_NAME_NAME);
        String address = req.getParameter(PARAM_NAME_ADDRESS);
        String phone = req.getParameter(PARAM_NAME_PHONE);
        User user = new User(email, name, password, address, phone, User.Role.RESTORATOR);
        userDAO.insertUser(user);
    }
}
