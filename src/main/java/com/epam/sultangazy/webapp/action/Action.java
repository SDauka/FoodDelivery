package com.epam.sultangazy.webapp.action;

import com.epam.sultangazy.webapp.dao.exception.DAOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException;
}
