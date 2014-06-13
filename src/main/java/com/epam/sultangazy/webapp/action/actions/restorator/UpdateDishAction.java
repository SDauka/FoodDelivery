package com.epam.sultangazy.webapp.action.actions.restorator;

import com.epam.sultangazy.webapp.action.Action;
import com.epam.sultangazy.webapp.action.ActionResult;
import com.epam.sultangazy.webapp.dao.DAOFactory;
import com.epam.sultangazy.webapp.dao.exception.DAOException;
import com.epam.sultangazy.webapp.dao.mysql.MySQLDishDAO;
import com.epam.sultangazy.webapp.db_pool.ConnectionPool;
import com.epam.sultangazy.webapp.entity.Dish;
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

public class UpdateDishAction implements Action {
    private static final Logger LOG = Logger.getLogger(UpdateDishAction.class);
    private PropertyReader propertyReader = new PropertyReader(PropertyReader.PAGES_PROPERTIES);
    private static final String ATTR_NAME_RESTAURANT = "restaurant";
    private static final String ATTR_NAME_RESTAURANT_DISHES = "restaurant_dishes";
    private static final String ATTR_NAME_ERROR = "updateDishErrorMessage";
    private static final String PARAM_NAME_DISH_ID = "idDish";
    private static final String PARAM_NAME_DISH_NAME = "dname";
    private static final String PARAM_NAME_DISH_INGREDIENTS = "dingredients";
    private static final String PARAM_NAME_DISH_WEIGHT = "dweight";
    private static final String PARAM_NAME_DISH_COST = "dcost";
    private String name;
    private String ingredients;
    private String image;
    private int cost;
    private int weight;
    private int idDish;
    private final String RESTORATOR_PAGE = propertyReader.getProperties("restoratorPage.page");
    private final String EDIT_DISH_PAGE = propertyReader.getProperties("editDishPage.page");
    private DAOFactory factory = new DAOFactory(ConnectionPool.getInstance());
    private MySQLDishDAO mySQLDishDAO = (MySQLDishDAO) factory.getDishDAO();
    private Dish editDish;

    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws DAOException {
        HttpSession session = req.getSession();
        Restaurant sessionRestaurant = (Restaurant) session.getAttribute(ATTR_NAME_RESTAURANT);
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
                        return new ActionResult(EDIT_DISH_PAGE, false);
                    }
                }
            }
            if (!updateDish(req)) {
                return new ActionResult(EDIT_DISH_PAGE, false);
            } else {
                List<Dish> dishs;
                dishs = mySQLDishDAO.findDishesByRestaurantID(sessionRestaurant.getId());
                req.setAttribute(ATTR_NAME_RESTAURANT_DISHES, dishs);
                return new ActionResult(RESTORATOR_PAGE, false);
            }

        } catch (FileUploadException fue) {
            fue.printStackTrace();
            req.setAttribute(ATTR_NAME_ERROR, "error.image");
            return new ActionResult(EDIT_DISH_PAGE, false);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(ATTR_NAME_ERROR, "error.image");
            return new ActionResult(EDIT_DISH_PAGE, false);
        }
    }

    private void checkAddForm(FileItem item, HttpServletRequest req) throws UnsupportedEncodingException, DAOException {
        if (item.getFieldName().equals(PARAM_NAME_DISH_ID)) {
            idDish = Integer.parseInt(item.getString());
            editDish = mySQLDishDAO.selectDishByID(idDish);
        }
        if (item.getFieldName().equals(PARAM_NAME_DISH_NAME)) {
            if (item.getString().isEmpty()) {
                name = editDish.getName();
            } else {
                name = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_DISH_INGREDIENTS)) {
            if (item.getString().isEmpty()) {
                ingredients = editDish.getIngredients();
            } else {
                ingredients = new String(item.getString().getBytes("iso-8859-1"), "UTF-8");
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_DISH_WEIGHT)) {
            if (item.getString().isEmpty()) {
                weight = editDish.getWeight();
            } else {
                weight = Integer.parseInt(item.getString());
            }
        }
        if (item.getFieldName().equals(PARAM_NAME_DISH_COST)) {
            if (item.getString().isEmpty()) {
                cost = editDish.getCost();
            } else {
                cost = Integer.parseInt(item.getString());
            }
        }
    }

    private boolean checkAddImageForm(FileItem item, HttpServletRequest req) throws Exception {
        PropertyReader imagePathPropertyReader = new PropertyReader(PropertyReader.IMAGE_PATH);
        String imagePath = imagePathPropertyReader.getProperties("dishesView");
        Random rand = new Random();
        int randomNumber = rand.nextInt(1000) + 1;
        if (item.getName().isEmpty()) {
            image = editDish.getImage().substring(7);
            return true;
        } else {
            String oldImage = editDish.getImage().substring(7);
            new File(imagePath + File.separator + oldImage).delete();
            image = new String(item.getName().getBytes("iso-8859-1"), "UTF-8");
            image = randomNumber + "_" + image;
            Path path1;
            path1 = Paths.get(image);
            String type = Files.probeContentType(path1).split("/")[0];
            File disk;
            if (type.equals("image")) {
                String path = imagePath + File.separator + image;
                disk = new File(path);
                item.write(disk);
                LOG.debug("Upload: image uploaded");
                ImageResizer.ImageResize(180, 120, path);
                return true;
            } else {
                req.setAttribute(ATTR_NAME_ERROR, "error.imageType");
                LOG.debug("Upload: image type error");
                return false;
            }
        }
    }

    private boolean updateDish(HttpServletRequest req) {
        Dish dish = new Dish();
        dish.setId(idDish);
        dish.setName(name);
        dish.setIngredients(ingredients);
        dish.setWeight(weight);
        dish.setCost(cost);
        dish.setImage("dishes/" + image);
        try {
            LOG.debug("Dish update");
            return mySQLDishDAO.updateDish(dish);
        } catch (DAOException e) {
            e.printStackTrace();
            LOG.debug("Dish update failed");
            req.setAttribute(ATTR_NAME_ERROR, "error.add");
            return false;
        }
    }
}
