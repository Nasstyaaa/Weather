package org.nastya.service;

import jakarta.servlet.http.Cookie;
import org.mindrot.jbcrypt.BCrypt;
import org.nastya.dao.SessionDAO;
import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.IncorrectLoginInformationException;
import org.nastya.exception.UserAlreadyExistsException;
import org.nastya.exception.UserNotFoundException;
import org.nastya.model.Session;
import org.nastya.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class AuthenticationService {
    private final UserDAO userDAO = new UserDAO();
    private final SessionDAO sessionDAO = new SessionDAO();

    public Cookie login(UserDTORequest userDTORequest){
        User user = userDAO.findByLogin(userDTORequest.getLogin())
                .orElseThrow(IncorrectLoginInformationException::new);

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
        throw new IncorrectLoginInformationException();
    }


    public void register(UserDTORequest userDTORequest){
        if (userDAO.findByLogin(userDTORequest.getLogin()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        String password = BCrypt.hashpw(userDTORequest.getPassword(), BCrypt.gensalt(12));
        User user = new User(userDTORequest.getLogin(), password);
        userDAO.save(user);
    }

    public void logout(Session session){
        sessionDAO.delete(session);
    }

    public Session checkLogin(Cookie[] cookies){
        SessionDAO sessionDAO = new SessionDAO();

        if(cookies == null){
            throw new UserNotFoundException();
        }

        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("SessionId"))
                .findAny()
                .orElseThrow(UserNotFoundException::new);

        return sessionDAO.findById(UUID.fromString(cookie.getValue())).orElseThrow(UserNotFoundException::new);
    }
}
