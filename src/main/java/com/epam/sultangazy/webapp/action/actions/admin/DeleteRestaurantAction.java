package com.epam.sultangazy.webapp.action.actions.admin;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.dao.UserDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.PropertyReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;

public class DeleteRestaurantAction implements Action {
    private static final String PARAM_NAME_RESTAURANT_ID = "restaurantId";
    private static final String PARAM_NAME_RESTORATOR_ID = "restoratorId";
    private static final String ATTR_NAME_DS_LIST = "deliveryServicesList";
    private static final String ATTR_NAME_RESTAURANTS_RESTORATOR = "restaurantRestorators";
    private PropertyReader propertyReader = new PropertyReader(com.epam.sultangazy.webapp.helper.PropertyReader.PAGES_PROPERTIES);
    private final String ADMIN_PROFILE = propertyReader.getProperties("adminProfile.page");

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        PropertyReader imagePathPropertyReader = new PropertyReader(PropertyReader.IMAGE_PATH);
        PropertyReader substring = new PropertyReader(PropertyReader.SUBSTRING);
        int subs = Integer.parseInt(substring.getProperties("rest_image"));
        String imagePath = imagePathPropertyReader.getProperties("restaurantsLogo");
        MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
        UserDAO userDAO = factory.getUserDAO();
        RestaurantDAO mySQLRestaurantDAO = factory.getRestaurantDAO();
        int restaurantId = Integer.parseInt(req.getParameter(PARAM_NAME_RESTAURANT_ID));
        int restoratorId = Integer.parseInt(req.getParameter(PARAM_NAME_RESTORATOR_ID));
        Restaurant restaurant = mySQLRestaurantDAO.selectRestaurantByID(restaurantId);
        String oldImage = restaurant.getImage().substring(subs);
        new File(imagePath + File.separator + oldImage).delete();
        mySQLRestaurantDAO.deleteRestaurantById(restaurantId, restoratorId);
        Map<Integer, User> users = userDAO.findRestorators();
        List<Restaurant> restaurants = mySQLRestaurantDAO.findRestaurants();
        req.setAttribute(ATTR_NAME_DS_LIST, restaurants);
        req.setAttribute(ATTR_NAME_RESTAURANTS_RESTORATOR, users);
        return new ActionResult(ADMIN_PROFILE, false);
    }
}
