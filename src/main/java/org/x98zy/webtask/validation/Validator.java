package org.x98zy.webtask.validation;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class Validator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern TITLE_PATTERN =
            Pattern.compile("^[\\p{L}0-9\\s.,!?-]{5,200}$");

    public static ValidationResult validateUserRegistration(String username, String email,
                                                            String password, String phone, String city) {
        ValidationResult result = new ValidationResult();

        if (!isValidUsername(username)) {
            result.addError("username", "Имя пользователя должно быть 3-20 символов (буквы, цифры, подчёркивание)");
        }

        if (!isValidEmail(email)) {
            result.addError("email", "Некорректный email");
        }

        if (!isValidPassword(password)) {
            result.addError("password", "Пароль должен быть не менее 6 символов");
        }

        if (phone != null && !phone.isEmpty() && !isValidPhone(phone)) {
            result.addError("phone", "Некорректный номер телефона");
        }

        if (!isValidCity(city)) {
            result.addError("city", "Город обязателен для заполнения");
        }

        return result;
    }

    public static ValidationResult validateAdvertisement(String title, String description,
                                                         String priceStr, String city) {
        ValidationResult result = new ValidationResult();

        if (!isValidTitle(title)) {
            result.addError("title", "Заголовок должен быть 5-200 символов");
        }

        if (!isValidDescription(description)) {
            result.addError("description", "Описание должно быть не менее 10 символов");
        }

        if (priceStr != null && !priceStr.trim().isEmpty()) {
            try {
                BigDecimal price = new BigDecimal(priceStr);
                if (price.compareTo(BigDecimal.ZERO) < 0) {
                    result.addError("price", "Цена не может быть отрицательной");
                }
            } catch (NumberFormatException e) {
                result.addError("price", "Некорректный формат цены");
            }
        }

        if (!isValidCity(city)) {
            result.addError("city", "Город обязателен для заполнения");
        }

        return result;
    }

    public static ValidationResult validateLogin(String username, String password) {
        ValidationResult result = new ValidationResult();

        if (username == null || username.trim().isEmpty()) {
            result.addError("username", "Имя пользователя обязательно");
        }

        if (password == null || password.trim().isEmpty()) {
            result.addError("password", "Пароль обязателен");
        }

        return result;
    }

    private static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    private static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    private static boolean isValidCity(String city) {
        return city != null && city.trim().length() >= 2;
    }

    private static boolean isValidTitle(String title) {
        return title != null && title.trim().length() >= 5 && title.trim().length() <= 200;
    }

    private static boolean isValidDescription(String description) {
        return description != null && description.trim().length() >= 10;
    }
}