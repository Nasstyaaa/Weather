package org.nastya.service;

import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.InvalidPasswordException;
import org.nastya.exception.UserNotFoundException;
import org.nastya.model.User;
import org.nastya.util.PasswordUtil;

public class LogInService {

    public void login(UserDTORequest userDTORequest){
        UserDAO userDAO = new UserDAO();

        User user = userDAO.find(userDTORequest.getLogin()).orElseThrow(UserNotFoundException::new);
        if (PasswordUtil.compare(userDTORequest.getPassword(), user.getPassword())){
            //TODO Sessions
            return;
        }
        throw new InvalidPasswordException();
    }
}
