package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.dao.exception.CannotTakeConnectionException;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    private final Logger LOG = Logger.getLogger(LogoutAction.class);
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String LOGIN_PAGE = propertyReader.getProperties("welcome");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws CannotTakeConnectionException, DAOException {
        User user = (User) req.getSession().getAttribute("user");
        LOG.debug(user.getEmail() + " - " + user.getRole() + ", signed out");
        req.getSession().invalidate();
        return new ActionResult(LOGIN_PAGE, true);
    }
}
