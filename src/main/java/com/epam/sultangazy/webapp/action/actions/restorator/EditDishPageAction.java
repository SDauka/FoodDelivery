package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditDishPageAction implements Action {
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String EDIT_DISH_PAGE = propertyReader.getProperties("editDishPage.page");
    private static final String ATTR_NAME_EDIT_DISH_ID = "editDishId";
    private static final String PARAM_NAME_DISH_ID = "idDish";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        int idDish = Integer.parseInt(req.getParameter(PARAM_NAME_DISH_ID));
        req.setAttribute(ATTR_NAME_EDIT_DISH_ID, idDish);
        return new ActionResult(EDIT_DISH_PAGE, false);
    }
}
