package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLOrderDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeOrderStatusAction implements Action {
    private static final String ATTR_NAME_ORDER_ID = "order";
    private static final String PARAM_NAME_STATUS_ID = "status";
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorProfile");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        int orderId = Integer.parseInt(req.getParameter(ATTR_NAME_ORDER_ID));
        int statusId = Integer.parseInt(req.getParameter(PARAM_NAME_STATUS_ID));
        DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
        MySQLOrderDAO mySQLOrderDAO = (MySQLOrderDAO) factory.getOrderDAO();
        mySQLOrderDAO.updateState(orderId, statusId);
        return new ActionResult(RESTORATOR_PAGE, true);
    }
}
