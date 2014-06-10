package com.epam.sultangazy.webapp.servlet;

import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable)
                req.getAttribute("javax.servlet.error.exception");
        LOG.debug("Throwable: " + throwable);
        Integer statusCode = (Integer)
                req.getAttribute("javax.servlet.error.status_code");
        LOG.debug("Status Code: " + statusCode);
        String servletName = (String)
                req.getAttribute("javax.servlet.error.servlet_name");
        LOG.debug("Servlet Name: " + servletName);
        String requestUri = (String)
                req.getAttribute("javax.servlet.error.request_uri");
        LOG.debug("Request Uri: " + requestUri);
        req.setAttribute("errorCode", statusCode);
        req.getRequestDispatcher("/WEB-INF/jsp/error/error.jsp").forward(req, resp);
    }
}
