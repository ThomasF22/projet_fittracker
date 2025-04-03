package com.fittracker.service;

import org.mindrot.jbcrypt.BCrypt;

import com.fittracker.model.User;
import com.fittracker.model.UserDAO;

public class UserService {
    private UserDAO userDAO = new UserDAO(null);

    public String hashPassword(String rawPassword){
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String rawPassword, String hashedPassword){
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public User getUserById(int userId){
        return getU
    }
}

