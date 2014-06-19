package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.UserDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import com.epam.sultangazy.webapp.helper.ValidationUtils;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationAction implements Action {
    private static final String PARAM_NAME_LOGIN = "email";
    private static final String PARAM_NAME_PASSWORD = "passwordR";
    private static final String PARAM_NAME_NAME = "name";
    private static final String PARAM_NAME_ADDRESS = "address";
    private static final String PARAM_NAME_PHONE = "phone";
    private static final String PARAM_NAME_USER_ROLE = "roleR";
    private static final String ERROR_MESSAGE = "errorMessageR";
    private static final String ERROR_MESSAGE2 = "errorMessageR2";
    private static final String ATTR_NAME_USER = "user";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String REGISTRATION_PAGE = propertyReader.getProperties("welcome.page");
    private final String RESTAURANTS_ACTION = propertyReader.getProperties("restaurants.action");
    private final String COOPERATION_PAGE = propertyReader.getProperties("cooperation.page");
    private final String REQUEST_RECEIVED_PAGE = propertyReader.getProperties("requestReceivedPage");
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private int role;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        ActionResult actionResult;
        email = req.getParameter(PARAM_NAME_LOGIN);
        password = req.getParameter(PARAM_NAME_PASSWORD);
        name = req.getParameter(PARAM_NAME_NAME);
        address = req.getParameter(PARAM_NAME_ADDRESS);
        phone = req.getParameter(PARAM_NAME_PHONE);
        role = Integer.parseInt(req.getParameter(PARAM_NAME_USER_ROLE));
        if (isFormValid(req)) {
            if (!register()) {
                if (role == 2) {
                    req.setAttribute(ERROR_MESSAGE, "error.occupied");
                    reloadForm(req);
                    actionResult = new ActionResult(REGISTRATION_PAGE, false);
                } else {
                    req.setAttribute(ERROR_MESSAGE2, "error.occupied");
                    reloadForm(req);
                    actionResult = new ActionResult(COOPERATION_PAGE, false);
                }
            } else {
                if (role == 2) {
                    MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
                    UserDAO userDAO = factory.getUserDAO();
                    HttpSession session = req.getSession();
                    User user = userDAO.findUser(email);
                    session.setAttribute(ATTR_NAME_USER, user);
                    actionResult = new ActionResult(RESTAURANTS_ACTION, true);
                } else actionResult = new ActionResult(REQUEST_RECEIVED_PAGE, true);
            }
        } else {
            if (role == 2) {
                actionResult = new ActionResult(REGISTRATION_PAGE, false);
            } else {
                actionResult = new ActionResult(COOPERATION_PAGE, false);
            }
        }
        return actionResult;
    }

    private void reloadForm(HttpServletRequest request) {
        request.setAttribute(PARAM_NAME_LOGIN, email);
        request.setAttribute(PARAM_NAME_NAME, name);
        request.setAttribute(PARAM_NAME_PHONE, phone);
        request.setAttribute(PARAM_NAME_ADDRESS, address);
    }

    private boolean isFormValid(HttpServletRequest request) {
        return ValidationUtils.isUserNameValid(request)
                & ValidationUtils.isAddressPhoneValid(request)
                & ValidationUtils.isPasswordValid(request)
                & ValidationUtils.isEmailValid(request);
    }

    private boolean register() throws DAOException {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        UserDAO userDAO = factory.getUserDAO();
        User user;
        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        if (!userDAO.checkUser(email)) {
            user = new User();
            user.setEmail(email);
            user.setPassword(encryptedPassword);
            user.setName(name);
            user.setAddress(address);
            user.setPhone(phone);
            if (role == 2) {
                user.setRole(User.Role.USER);
                userDAO.insertUser(user);
            } else if (role == 3) {
                user.setRole(User.Role.RESTORATOR);
                userDAO.insertUserCooperation(user);
            }
            return true;
        } else {
            return false;
        }
    }

}
