package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.exception.CannotTakeConnectionException;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguageAction implements Action {
    private static final Logger LOG = Logger.getLogger(ChangeLanguageAction.class);
    private static final String LOCALE = "locale";
    private static final String LANGUAGE = "language";
    private String referrer;

    @Override
    public ActionResult execute(HttpServletRequest request, HttpServletResponse response) throws CannotTakeConnectionException {
        if (request.getParameter(LOCALE) != null) {
            LOG.debug("Locale: " + request.getParameter(LOCALE));
            HttpSession session = request.getSession();
            session.setAttribute(LANGUAGE, request.getParameter(LOCALE));
            Cookie cookie = new Cookie(LOCALE, request.getParameter(LOCALE));
            response.addCookie(cookie);
        }
        referrer = request.getHeader("referer").substring(38);
        return new ActionResult(referrer, true);
    }
}
