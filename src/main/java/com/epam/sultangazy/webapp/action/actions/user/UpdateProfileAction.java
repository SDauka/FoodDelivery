package com.epam.sultangazy.webapp.action.actions.user;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.mysql.MySQLUserDAO;
import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import org.jasypt.util.password.BasicPasswordEncryptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateProfileAction implements Action {
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String REST_PROFILE_PAGE = propertyReader.getProperties("restoratorPage.page");
    private final String REST_PROFILE_PAGEr = propertyReader.getProperties("restoratorProfile");
    private final String U_PROFILE_PAGE = propertyReader.getProperties("userProfile.page");
    private final String U_PROFILE_PAGEr = propertyReader.getProperties("userProfile");
    private static final String ATTR_NAME_ERROR_U = "updateUserRestErrorMessage";
    private static final String ATTR_NAME_ERROR_R = "updateUserErrorMessage";
    private static final String ATTR_NAME_USER = "user";
    private static final String PARAM_NAME_UNAME = "nameE";
    private static final String PARAM_NAME_OLD_PASS = "oldPassword";
    private static final String PARAM_NAME_NEW_PASS = "newPasswordU";
    private static final String PARAM_NAME_CONF_PASS = "confPasswordU";
    private static final String PARAM_NAME_U_ADDREES = "addressE";
    private static final String PARAM_NAME_U_PHONE = "phoneE";
    private BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
    private int id;
    private String oldPassword;
    private String newPassword;
    private String password;
    private String name;
    private String address;
    private String phone;
    private String confPassword;
    private User sessionUser;


    public boolean checkUpdateForm(HttpServletRequest req) {
        HttpSession session = req.getSession();
        sessionUser = (User) session.getAttribute("user");
        id = sessionUser.getId();
        oldPassword = req.getParameter(PARAM_NAME_OLD_PASS).trim();
        newPassword = req.getParameter(PARAM_NAME_NEW_PASS).trim();
        name = req.getParameter(PARAM_NAME_UNAME).trim();
        address = req.getParameter(PARAM_NAME_U_ADDREES).trim();
        phone = req.getParameter(PARAM_NAME_U_PHONE).trim();
        confPassword = req.getParameter(PARAM_NAME_CONF_PASS).trim();
        if (oldPassword == null || oldPassword.isEmpty()) {
            if (sessionUser.getRole().equals(User.Role.RESTORATOR)) {
                req.setAttribute(ATTR_NAME_ERROR_R, "error.invalid.password");
                return false;
            } else {
                req.setAttribute(ATTR_NAME_ERROR_U, "error.invalid.password");
                return false;
            }
        } else {
            return true;
        }
    }

    public boolean checkUpdateField(HttpServletRequest req) {
        if (name.isEmpty()) {
            name = sessionUser.getName();
        }
        if (address.isEmpty()) {
            address = sessionUser.getAddress();
        }
        if (phone.isEmpty()) {
            phone = sessionUser.getPhone();
        }
        if (newPassword.isEmpty() & confPassword.isEmpty()) {
            password = passwordEncryptor.encryptPassword(oldPassword);
            return true;
        } else {
            if (newPassword.equals(confPassword)) {
                password = passwordEncryptor.encryptPassword(newPassword);
                return true;
            } else {
                if (sessionUser.getRole().equals(User.Role.RESTORATOR)) {
                    req.setAttribute(ATTR_NAME_ERROR_R, "password.doesnt.match");
                    return false;
                } else {
                    req.setAttribute(ATTR_NAME_ERROR_U, "password.doesnt.match");
                    return false;
                }
            }

        }
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        HttpSession session = req.getSession();
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLUserDAO userDAO = (MySQLUserDAO) factory.getUserDAO();
        if (!checkUpdateForm(req)) {
            if (sessionUser.getRole().equals(User.Role.RESTORATOR)) {
                return new ActionResult(REST_PROFILE_PAGE, false);
            } else return new ActionResult(U_PROFILE_PAGE, false);
        } else {
            if (!checkUpdateField(req)) {
                if (sessionUser.getRole().equals(User.Role.RESTORATOR)) {
                    return new ActionResult(REST_PROFILE_PAGE, false);
                } else return new ActionResult(U_PROFILE_PAGE, false);
            } else {
                User checkuser;
                checkuser = userDAO.findUser(sessionUser.getEmail());
                if (checkuser == null || !passwordEncryptor.checkPassword(oldPassword, checkuser.getPassword())) {
                    if (sessionUser.getRole().equals(User.Role.RESTORATOR)) {
                        req.setAttribute(ATTR_NAME_ERROR_R, "error.invalid.password");
                        return new ActionResult(REST_PROFILE_PAGE, false);
                    } else {
                        req.setAttribute(ATTR_NAME_ERROR_U, "error.invalid.password");
                        return new ActionResult(U_PROFILE_PAGE, false);
                    }
                } else {
                    updateUser(id, name, address, password, phone);
                    User user = userDAO.findUser(sessionUser.getEmail());
                    session.setAttribute(ATTR_NAME_USER, user);
                    if (sessionUser.getRole().equals(User.Role.RESTORATOR)) {
                        return new ActionResult(REST_PROFILE_PAGEr, true);
                    } else {
                        return new ActionResult(U_PROFILE_PAGEr, true);
                    }
                }
            }
        }
    }

    private void updateUser(int id, String name, String address, String newPassword, String phone) {
        User user = new User(name, address, newPassword, phone);
        user.setId(id);
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLUserDAO userDAO = (MySQLUserDAO) factory.getUserDAO();
        try {
            userDAO.updateUser(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

}


