package org.nastya.util;

import jakarta.servlet.http.Cookie;
import org.nastya.dao.SessionDAO;
import org.nastya.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.UUID;

public class CookieUtil {

    public static void check(Cookie[] cookies){
        SessionDAO sessionDAO = new SessionDAO();

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("SessionId"))
                .findAny()
                .orElseThrow(UserNotFoundException::new);

        sessionDAO.findById(UUID.fromString(cookie.getValue())).orElseThrow(UserNotFoundException::new);
    }
}
