package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.DAOFactory;
import com.epam.sultangazy.webapp.dao.mysql.MySQLRestaurantDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
import com.epam.sultangazy.webapp.entity.User;
import com.epam.sultangazy.webapp.helper.ImageResizer;
import com.epam.sultangazy.webapp.helper.PropertyReader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AddRestaurantAction implements Action {
    private static final Logger LOG = Logger.getLogger(AddRestaurantAction.class);
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private static final String ATTR_NAME_RESTAURANT = "restaurant";
    private static final String ATTR_NAME_USER = "user";
    private static final String ATTR_NAME_ERROR = "addRestErrorMessage";
    private static final String PARAM_NAME_RESTAURANT_NAME = "nameRest";
    private static final String PARAM_NAME_RESTAURANT_DT = "deliveryTimeRest";
    private static final String PARAM_NAME_RESTAURANT_NOTE = "noteRest";
    private String name;
    private String note;
    private int deliveryTime;
    private int restoratorID;
    private String image;
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorPage.page");
    private final String RESTORATOR_PAGEr = propertyReader.getProperties("restoratorProfile");
    private DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
    private MySQLRestaurantDAO restaurantDAO = (MySQLRestaurantDAO) factory.getRestaurantDAO();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        HttpSession session = req.getSession();
        FileItemFactory ffactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(ffactory);
        upload.setSizeMax(1024 * 1024 * 5);
        try {
            List items = upload.parseRequest(req);
            Iterator iter = items.iterator();
            resp.setContentType("text/html");
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    checkAddForm(item, req);
                } else {
                    if (!checkAddImageForm(item, req)) {
                        return new ActionResult(RESTORATOR_PAGE, false);
                    }
                }
            }
            if (!insertRestaurant(req)) {
                return new ActionResult(RESTORATOR_PAGE, false);
            }
            if (restaurantDAO.checkRestorator(restoratorID)) {
                Restaurant restaurt;
                restaurt = restaurantDAO.selectRestaurantByRestoratorID(restoratorID);
                session.setAttribute(ATTR_NAME_RESTAURANT, restaurt);
            }
            return new ActionResult(RESTORATOR_PAGEr, true);
        } catch (FileUploadException fue) {
            fue.printStackTrace();
            req.setAttribute(ATTR_NAME_ERROR, "error.image");
            return new ActionResult(RESTORATOR_PAGE, false);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(ATTR_NAME_ERROR, "error.image");
            return new ActionResult(RESTORATOR_PAGE, false);
        }
    }

    public boolean insertRestaurant(HttpServletRequest req) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setDeliveryTime(deliveryTime);
        restaurant.setNote(note);
        restaurant.setImage("restaurants/" + image);
        try {
            restaurantDAO.insertRestaurant(restaurant, restoratorID);
            LOG.debug("Restaurant added");
            return true;
        } catch (DAOException e) {
            e.printStackTrace();
            LOG.debug("Restaurant add failed");
            req.setAttribute(ATTR_NAME_ERROR, "error.add");
            return false;
        }
    }

    public void checkAddForm(FileItem item, HttpServletRequest req) throws UnsupportedEncodingException {
        User restorator = (User) req.getSession().getAttribute(ATTR_NAME_USER);
        if (item.getFieldName().equals(PARAM_NAME_RESTAURANT_NAME)) {
            if (item.getString().isEmpty()) {
                req.setAttribute(ATTR_NAME_ERROR, "error.empty.fields");
            } else {
                name = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_RESTAURANT_DT)) {
            if (item.getString().isEmpty()) {
                req.setAttribute(ATTR_NAME_ERROR, "error.empty.fields");
            } else {
                deliveryTime = Integer.parseInt(item.getString());
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_RESTAURANT_NOTE)) {
            if (item.getString().isEmpty()) {
                req.setAttribute(ATTR_NAME_ERROR, "error.empty.fields");
            } else {
                note = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
                restoratorID = restorator.getId();
            }
        }
    }

    public boolean checkAddImageForm(FileItem item, HttpServletRequest req) throws Exception {
        PropertyReader imagePathPropertyReader = new PropertyReader(PropertyReader.IMAGE_PATH);
        String imagePath = imagePathPropertyReader.getProperties("restaurantsLogo");
        Random rand = new Random();
        int randomNumber = rand.nextInt(50) + 1;
        if (item.getName().isEmpty()) {
            req.setAttribute(ATTR_NAME_ERROR, "error.empty.fields");
            return false;
        }
        image = new String(item.getName().getBytes("iso-8859-1"), "UTF-8");
        image = name.replaceAll(" ", "") + "_" + randomNumber + "_" + image;
        Path path1;
        path1 = Paths.get(image);
        String type = Files.probeContentType(path1).split("/")[0];
        File disk;
        if (type.equals("image")) {
            String path = imagePath + File.separator + image;
            disk = new File(path);
            item.write(disk);
            LOG.debug("Upload: image uploaded");
            ImageResizer.ImageResize(255, 170, path);
            return true;
        } else {
            req.setAttribute(ATTR_NAME_ERROR, "error.imageType");
            LOG.debug("Upload: image type error");
            return false;
        }
    }

}
