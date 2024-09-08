package org.nastya.service;

import jakarta.servlet.http.Cookie;
import org.mindrot.jbcrypt.BCrypt;
import org.nastya.dao.SessionDAO;
import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.InvalidPasswordException;
import org.nastya.exception.UserNotFoundException;
import org.nastya.model.Session;
import org.nastya.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogInService {

    public Cookie login(UserDTORequest userDTORequest){
        UserDAO userDAO = new UserDAO();
        SessionDAO sessionDAO = new SessionDAO();

        User user = userDAO.find(userDTORequest.getLogin()).orElseThrow(UserNotFoundException::new);

        if (BCrypt.checkpw(userDTORequest.getPassword(), user.getPassword())){
            UUID id = UUID.randomUUID();
            LocalDateTime time = LocalDateTime.now().plusMinutes(30);

            Session session = new Session(id, user, time);
            sessionDAO.save(session);

            Cookie cookie = new Cookie("SessionId", id.toString());
            cookie.setMaxAge(30 * 60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);

            return cookie;
        }
        throw new InvalidPasswordException();
    }
}
