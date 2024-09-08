package org.nastya.service;

import org.mindrot.jbcrypt.BCrypt;
import org.nastya.dao.UserDAO;
import org.nastya.dto.UserDTORequest;
import org.nastya.exception.UserAlreadyExistsException;
import org.nastya.model.User;

public class RegistrationService {

    public void register(UserDTORequest userDTORequest){
        UserDAO userDAO = new UserDAO();

        if (userDAO.find(userDTORequest.getLogin()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        String password = BCrypt.hashpw(userDTORequest.getPassword(), BCrypt.gensalt(12));
        User user = new User(userDTORequest.getLogin(), password);
        userDAO.save(user);
    }
}
