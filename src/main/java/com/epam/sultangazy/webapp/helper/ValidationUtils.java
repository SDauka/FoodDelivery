package com.epam.sultangazy.webapp.helper;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isUserNameValid(HttpServletRequest request) {
        String username = request.getParameter("name");
        int role = Integer.parseInt(request.getParameter("roleR"));
        boolean isUserNameValid;
        if (username == null || username.isEmpty()) {
            isUserNameValid = false;
            if (role == 2) {
                request.setAttribute("errorMessageR", "error.empty.fields");
            } else {
                request.setAttribute("errorMessageR2", "error.empty.fields");
            }
        } else if (username.length() > 25) {
            request.setAttribute("errorMessageR", "error.name.length");
            isUserNameValid = false;
        } else {
            isUserNameValid = true;
        }
        return isUserNameValid;
    }

    public static boolean isPasswordValid(HttpServletRequest request) {
        boolean isPasswordValid = true;
        String password = request.getParameter("passwordR");
        String confirmPassword = request.getParameter("confPasswordR");
        int role = Integer.parseInt(request.getParameter("roleR"));
        if (password == null || password.isEmpty() || password.length() > 50) {
            isPasswordValid = false;
            if (role == 2) {
                request.setAttribute("errorMessageR", "error.invalid.password");
            } else {
                request.setAttribute("errorMessageR2", "error.invalid.password");
            }
        } else {
            if (password.length() < 4) {
                isPasswordValid = false;
                if (role == 2) {
                    request.setAttribute("errorMessageR", "error.invalid.length");
                } else {
                    request.setAttribute("errorMessageR2", "error.invalid.length");
                }
            }
            if (isPasswordValid && !password.equals(confirmPassword)) {
                if (role == 2) {
                    request.setAttribute("errorMessageR", "error.invalid.password");
                } else {
                    request.setAttribute("errorMessageR2", "error.invalid.password");
                }
                isPasswordValid = false;
            }
        }
        return isPasswordValid;
    }

    public static boolean isAddressPhoneValid(HttpServletRequest request) {
        boolean isAddressPhoneValid = true;
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        int role = Integer.parseInt(request.getParameter("roleR"));
        if (address == null || address.isEmpty() || phone == null || phone.isEmpty()) {
            isAddressPhoneValid = false;
            if (role == 2) {
                request.setAttribute("errorMessageR", "error.empty.fields");
            } else {
                request.setAttribute("errorMessageR2", "error.empty.fields");
            }
        }
        return isAddressPhoneValid;
    }

    public static boolean isEmailValid(HttpServletRequest request) {
        boolean isEmailValid = true;
        String email = request.getParameter("email");
        int role = Integer.parseInt(request.getParameter("roleR"));
        Matcher emailMatcher = null;
        if (email == null || email.isEmpty()) {
            isEmailValid = false;
            if (role == 2) {
                request.setAttribute("errorMessageR", "error.invalid.email");
            } else {
                request.setAttribute("errorMessageR2", "error.invalid.email");
            }
        } else {
            String emailRegex = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
            Pattern emailPattern = Pattern.compile(emailRegex,
                    Pattern.UNICODE_CHARACTER_CLASS | Pattern.CASE_INSENSITIVE);
            emailMatcher = emailPattern.matcher(email);
            if (!(isEmailValid = emailMatcher.matches()) || email.length() > 128) {

                if (role == 2) {
                    request.setAttribute("errorMessageR", "error.invalid.email");
                } else {
                    request.setAttribute("errorMessageR2", "error.invalid.email");
                }
            }
        }
        return isEmailValid;
    }

}
