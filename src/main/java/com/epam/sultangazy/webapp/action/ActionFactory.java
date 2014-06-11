package com.epam.sultangazy.webapp.action;


import com.epam.sultangazy.webapp.action.actions.*;
import com.epam.sultangazy.webapp.action.actions.admin.*;
import com.epam.sultangazy.webapp.action.actions.restorator.*;
import com.epam.sultangazy.webapp.action.actions.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ActionFactory {
    private HashMap<String, Action> actions = new HashMap<>();

    public ActionFactory() {
        actions.put("GET/welcomePage", new ViewAction("welcome.page"));
        actions.put("GET/jsDisabledPage", new ViewAction("jsdisabled.page"));
        actions.put("GET/adminPage", new ViewAction("adminProfile.page"));
        actions.put("GET/requestReceivedPage", new ViewAction("requestReceived.page"));
        actions.put("GET/restaurantsPage", new ViewAction("restaurants.page"));
        actions.put("GET/cooperationPage", new ViewAction("cooperation.page"));
        actions.put("GET/profilePage", new ViewAction("userProfile.page"));
        actions.put("GET/restoratorPage", new ViewAction("restoratorPage.page"));
        actions.put("GET/editDishPage", new ViewAction("editDishPage.page"));
        actions.put("GET/addDishPage", new ViewAction("addDish.page"));
        actions.put("GET/menuPage", new ViewAction("menuPage.page"));
        actions.put("GET/orderCheck", new ViewAction("orderCheck.page"));
        actions.put("GET/orderConfirm", new ViewAction("orderConfirm.page"));
        actions.put("GET/requestOrderDishes", new ViewAction("requestOrderDishes.page"));
        actions.put("GET/addDishP", new AddDishPageAction());
        actions.put("GET/checkOrder", new CheckOrderPageAction());
        actions.put("GET/locale", new ChangeLanguageAction());
        actions.put("GET/restaurants", new ShowRestaurants());
        actions.put("GET/showDishes", new ShowDishesAction());
        actions.put("GET/pickCategory", new PicksByCategoryAction());
        actions.put("GET/cleanCart", new CleanCartAction());
        actions.put("GET/userOrders", new OrderListAction());
        actions.put("GET/getOrders", new RestaurantOrderListAction());
        actions.put("GET/usersList", new UserListAction());
        actions.put("GET/cooperationRequests", new CooperationRequestsListAction());
        actions.put("GET/changeOrderStatus", new ChangeOrderStatusAction());
        actions.put("GET/deliveryServices", new RestaurantListAction());
        actions.put("POST/showOrderDishes", new ShowOrderDishAction());
        actions.put("POST/deleteUser", new DeleteUserAction());
        actions.put("POST/deleteRestaurant", new DeleteRestaurantAction());
        actions.put("POST/acceptRequest", new AcceptRequestAction());
        actions.put("POST/rejectRequest", new RejectRequestAction());
        actions.put("POST/requestOrder", new CreateOrderAction());
        actions.put("POST/editDish", new EditDishPageAction());
        actions.put("POST/login", new LoginAction());
        actions.put("POST/registration", new RegistrationAction());
        actions.put("POST/logout", new LogoutAction());
        actions.put("POST/updateProfile", new UpdateProfileAction());
        actions.put("POST/addRestaurant", new AddRestaurantAction());
        actions.put("POST/updateRestaurantProfile", new ChangeRestaurantProfileAction());
        actions.put("POST/addDish", new AddDishAction());
        actions.put("POST/updateDish", new UpdateDishAction());
        actions.put("POST/removeDish", new RemoveDishAction());
        actions.put("POST/showMenu", new ShowMenuAction());
        actions.put("POST/addToOrder", new AddToOrderAction());
        actions.put("POST/removeFromOrder", new RemoveFromOrderAction());
    }

    public Action getAction(HttpServletRequest req) {
        String actionKey = req.getMethod() + req.getPathInfo();
        Action action = actions.get(actionKey);
        return action;
    }
}
