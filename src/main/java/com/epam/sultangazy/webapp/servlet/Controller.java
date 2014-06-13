package com.epam.sultangazy.webapp.servlet;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionFactory;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class Controller extends HttpServlet {
    private static Logger LOG = Logger.getLogger(Controller.class);
    ActionFactory actionFactory = new ActionFactory();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        Action action = actionFactory.getAction(req);
        ActionResult result = null;
        try {
            result = action.execute(req, resp);
        } catch (CannotTakeConnectionException e) {
            e.printStackTrace();
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        returnResult(req, resp, result);
    }

    private void returnResult(HttpServletRequest req, HttpServletResponse resp, ActionResult result) {
        LOG.debug("Path: " + req.getPathInfo());
        if (result.isRedirected()) {
            try {
                LOG.debug("sendRedirect: " + result.getResult());
                resp.sendRedirect(result.getResult());
            } catch (IOException e) {
                LOG.error("exception in sendRedirect: " + e);
            }
        } else {
            try {
                LOG.debug("forward : " + result.getResult());
                req.getRequestDispatcher("/WEB-INF/jsp/" + result.getResult() + ".jsp").forward(req, resp);
            } catch (IOException e) {
                LOG.error("IO exception in forward: " + e);
            } catch (ServletException e) {
                LOG.error("Servlet exception in forward: " + e);
            }
        }
    }

}
