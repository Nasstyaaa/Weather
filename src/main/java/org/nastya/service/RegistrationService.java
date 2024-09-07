package org.nastya.service;

import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.UserAlreadyExistsException;
import org.nastya.model.User;
import org.nastya.util.PasswordUtil;

public class RegistrationService {

    public void register(UserDTORequest userDTORequest){
        UserDAO userDAO = new UserDAO();

        if (userDAO.find(userDTORequest.getLogin()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        String password = PasswordUtil.encode(userDTORequest.getPassword());
        User user = new User(userDTORequest.getLogin(), password);
        userDAO.save(user);
    }
}
