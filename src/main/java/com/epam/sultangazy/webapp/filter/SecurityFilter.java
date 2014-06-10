package com.epam.sultangazy.webapp.filter;

import com.epam.sultangazy.webapp.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SecurityFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(SecurityFilter.class);
    private Map<String, EnumSet<User.Role>> groups = new HashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String action = request.getMethod() + request.getPathInfo();
        EnumSet<User.Role> group = groups.get(action);
        User user = (User) session.getAttribute("user");
        User.Role role = User.Role.UNREGISTERED;

        if (user != null) role = user.getRole();
        if (group == null || group.contains(role)) {
            LOG.debug("Security filter: passed!");
            filterChain.doFilter(request, response);
            return;
        }

        LOG.debug("Security filter: forward to welcome page!");
        if (role.equals(User.Role.UNREGISTERED)) {
            request.getRequestDispatcher("/WEB-INF/jsp/welcomePage.jsp").forward(request, response);
        } else request.getRequestDispatcher("/WEB-INF/jsp/restaurantsPage.jsp").forward(request, response);

    }

    @Override
    public void destroy() {
    }

    public void init(FilterConfig config) throws ServletException {
        EnumSet<User.Role> admin = EnumSet.of(User.Role.ADMIN);
        EnumSet<User.Role> restorator = EnumSet.of(User.Role.RESTORATOR);
        EnumSet<User.Role> user = EnumSet.of(User.Role.USER);
        EnumSet<User.Role> authorized = EnumSet.of(User.Role.USER, User.Role.ADMIN, User.Role.RESTORATOR);
        EnumSet<User.Role> all = EnumSet.of(User.Role.USER, User.Role.ADMIN, User.Role.RESTORATOR, User.Role.UNREGISTERED);
        EnumSet<User.Role> unregistered = EnumSet.of(User.Role.UNREGISTERED);
        //all
        groups.put("GET/locale", all);
        groups.put("GET/jsDisabledPage", all);
        //unregistered
        groups.put("GET/welcomePage", unregistered);
        groups.put("GET/requestReceivedPage", unregistered);
        groups.put("GET/cooperationPage", unregistered);
        groups.put("POST/login", unregistered);
        groups.put("POST/registration", unregistered);
        //all
        groups.put("GET/restaurantsPage", authorized);
        groups.put("GET/menuPage", authorized);
        groups.put("GET/orderConfirm", authorized);
        groups.put("GET/requestOrderDishes", authorized);
        groups.put("GET/orderCheck", authorized);
        groups.put("GET/checkOrder", authorized);
        groups.put("GET/restaurants", authorized);
        groups.put("GET/pickCategory", authorized);
        groups.put("GET/cleanCart", authorized);
        groups.put("GET/userOrders", authorized);
        groups.put("POST/requestOrder", authorized);
        groups.put("POST/logout", authorized);
        groups.put("POST/updateProfile", authorized);
        groups.put("POST/addToOrder", authorized);
        groups.put("POST/removeFromOrder", authorized);
        groups.put("POST/showMenu", authorized);
        //restorator
        groups.put("GET/restoratorPage", restorator);
        groups.put("GET/editDishPage", restorator);
        groups.put("GET/showDishes", restorator);
        groups.put("GET/getOrders", restorator);
        groups.put("GET/changeOrderStatus", restorator);
        groups.put("POST/showOrderDishes", restorator);
        groups.put("POST/acceptRequest", restorator);
        groups.put("POST/rejectRequest", restorator);
        groups.put("POST/editDish", restorator);
        groups.put("POST/addRestaurant", restorator);
        groups.put("POST/addRestaurant", restorator);
        groups.put("POST/updateRestaurantProfile", restorator);
        groups.put("POST/addDish", restorator);
        groups.put("POST/updateDish", restorator);
        groups.put("POST/removeDish", restorator);
        //admin
        groups.put("GET/adminPage", admin);
        groups.put("GET/usersList", admin);
        groups.put("GET/deliveryServices", admin);
        groups.put("GET/cooperationRequests", admin);
        groups.put("POST/deleteRestaurant", admin);
        groups.put("POST/deleteUser", admin);
        groups.put("POST/acceptRequest", admin);
        groups.put("POST/rejectRequest", admin);
        //user
        groups.put("GET/profilePage", user);

    }

}
