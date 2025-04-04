package com.fittracker.service;

import org.mindrot.jbcrypt.BCrypt;

import com.fittracker.model.User;
import com.fittracker.model.UserDAO;

public class UserService {
    private BCrypt passwordEncoder;
    private UserDAO userDAO;
    
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String hashPassword(String rawPassword){
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean checkPassword(String rawPassword, String hashedPassword){
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

    public User getUserById(int userId){
        return userDAO.getUserById(userId);
    }

    public boolean createUser(String username, String password){
        String hashed = hashPassword(password);
        return userDAO.createUser(username, hashed);
    }

    // returns user if username and password matches
    public User authenticate(String username, String password) {
        User user = userDAO.getUserByUsername(username);

        if (user == null) { // avoids a nurupo below with user.getUserPassword
            return null;  // User not found, return null safely
        }

        if (user != null && BCrypt.checkpw(password, user.getUserPassword())) {
            return user;
        }
        if (BCrypt.checkpw(password, user.getUserPassword())) {
            return user;
        }
        return null;  // user not found
    }
}


