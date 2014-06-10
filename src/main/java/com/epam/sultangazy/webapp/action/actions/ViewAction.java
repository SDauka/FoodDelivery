package com.epam.sultangazy.webapp.action.actions;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAction implements Action {
    private static final Logger LOG = Logger.getLogger(ViewAction.class);
    public String viewAction;

    public ViewAction(String action) {
        LOG.debug("Action: " + action);
        PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
        viewAction = propertyReader.getProperties(action);
        LOG.debug("View action: " + viewAction);
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        return new ActionResult(viewAction, false);
    }
}
