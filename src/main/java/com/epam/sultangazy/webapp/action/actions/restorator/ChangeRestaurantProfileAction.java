package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.RestaurantDAO;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.factory.MySQLDAOFactory;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Restaurant;
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

public class ChangeRestaurantProfileAction implements Action {
    private static final Logger LOG = Logger.getLogger(ChangeRestaurantProfileAction.class);
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private static final String ATTR_NAME_ERROR = "upRestErrorMessage";
    private static final String ATTR_NAME_RESTAURANT = "restaurant";
    private static final String PARAM_NAME_RESTAURANT_NAME = "nameRest";
    private static final String PARAM_NAME_RESTAURANT_DT = "deliveryTimeRest";
    private static final String PARAM_NAME_RESTAURANT_NOTE = "noteRest";
    private String name;
    private String note;
    private int deliveryTime;
    private int restaurantID;
    private String image;
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorPage.page");
    private final String RESTORATOR_PAGEr = propertyReader.getProperties("restoratorProfile");
    private MySQLDAOFactory factory = new MySQLDAOFactory(ConnectionPool.getInstance());
    private RestaurantDAO restaurantDAO = factory.getRestaurantDAO();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        LOG.debug("Update restaurant info");
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
                    checkUpdateForm(item, req);
                } else {
                    if (!checkImageForm(item, req)) {
                        return new ActionResult(RESTORATOR_PAGE, false);
                    }
                }
            }
            if (!updateRestaurant(req)) {
                return new ActionResult(RESTORATOR_PAGE, false);
            }
            Restaurant restaurant;
            restaurant = restaurantDAO.selectRestaurantByID(restaurantID);
            session.setAttribute(ATTR_NAME_RESTAURANT, restaurant);
            return new ActionResult(RESTORATOR_PAGEr, true);
        } catch (FileUploadException fue) {
            fue.printStackTrace();
            LOG.debug("Upload: image upload failed");
            req.setAttribute(ATTR_NAME_ERROR, "error.image");
            return new ActionResult(RESTORATOR_PAGE, false);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug("Upload: image upload failed");
            req.setAttribute(ATTR_NAME_ERROR, "error.image");
            return new ActionResult(RESTORATOR_PAGE, false);
        }
    }


    private boolean updateRestaurant(HttpServletRequest req) {
        boolean check = false;
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantID);
        restaurant.setName(name);
        restaurant.setDeliveryTime(deliveryTime);
        restaurant.setNote(note);
        restaurant.setImage("restaurants/" + image);
        try {
            check = restaurantDAO.updateRestaurant(restaurant);
            LOG.debug("Restaurant updated");
        } catch (DAOException e) {
            e.printStackTrace();
            LOG.debug("Restaurant update failed");
            req.setAttribute("upRestErrorMessage", "error.add");
        }
        return check;
    }

    private void checkUpdateForm(FileItem item, HttpServletRequest req) throws UnsupportedEncodingException {
        HttpSession session = req.getSession();
        Restaurant sessionRestaurant = (Restaurant) session.getAttribute(ATTR_NAME_RESTAURANT);
        if (item.getFieldName().equals(PARAM_NAME_RESTAURANT_NAME)) {
            if (item.getString().isEmpty()) {
                name = sessionRestaurant.getName();
            } else {
                name = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_RESTAURANT_DT)) {
            if (item.getString().isEmpty()) {
                deliveryTime = sessionRestaurant.getDeliveryTime();
            } else {
                deliveryTime = Integer.parseInt(item.getString());
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_RESTAURANT_NOTE)) {
            if (item.getString().isEmpty()) {
                note = sessionRestaurant.getNote();
            } else {
                note = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
            }
        }
        restaurantID = sessionRestaurant.getId();
    }

    private boolean checkImageForm(FileItem item, HttpServletRequest req) throws Exception {
        PropertyReader substring = new PropertyReader(PropertyReader.SUBSTRING);
        PropertyReader imagePathPropertyReader = new PropertyReader(PropertyReader.IMAGE_PATH);
        int subs = Integer.parseInt(substring.getProperties("rest_image"));
        String imagePath = imagePathPropertyReader.getProperties("restaurantsLogo");
        Random rand = new Random();
        int randomNumber = rand.nextInt(50) + 1;
        HttpSession session = req.getSession();
        Restaurant sessionRestaurant = (Restaurant) session.getAttribute(ATTR_NAME_RESTAURANT);
        if (item.getName().isEmpty()) {
            image = sessionRestaurant.getImage().substring(subs);
            return true;
        }
        String oldImage = sessionRestaurant.getImage().substring(subs);
        new File(imagePath + File.separator + oldImage).delete();
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
            ImageResizer.ImageResize(255, 170, path);
            LOG.debug("Upload: image uploaded");
            return true;
        } else {
            LOG.debug("Upload: image type error");
            req.setAttribute(ATTR_NAME_ERROR, "error.imageType");
            return false;
        }
    }

}
