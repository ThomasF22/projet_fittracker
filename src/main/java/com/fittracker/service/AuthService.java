// package com.fittracker.service;
// import com.fittracker.model.UserDAO;

// import org.mindrot.jbcrypt.BCrypt;

// import com.fittracker.model.User;


// public class AuthService {

//     private UserDAO userDAO;
//     private UserService userService = new UserService();
//     private BCrypt passwordEncoder;

//     public AuthService(UserDAO userDAO) {
//         this.userDAO = userDAO;
//     }

//     // returns user if username and password matches
//     public User authenticate(String username, String password) {
//         User user = userDAO.getUserByUsername(username);
//         // System.out.println("\n user dans authenticate : " + username + " password : " + password);
//         // System.out.println("\n User récupéré dans authenticate : " + userDAO.getUserByUsername(username).getUserUsername());
//         // System.out.println("\n pswd hashé : " + userService.hashPassword(password));
//         // System.out.println("\n on compare avec : " + user.getUserPassword());

//         if (user != null && BCrypt.checkpw(password, user.getUserPassword())) {
//             return user;
//         }

//         if (user == null) { // avoids a nurupo below with user.getUserPassword
//             System.out.println("\n user is null, returning null");
//             return null;  // User not found, return null safely
//         }
//         if (userService.checkPassword(password, userService.hashPassword(password)))

//         if (BCrypt.checkpw(password, userService.hashPassword(user.getUserPassword()))) {
//             System.out.println("\nBREAKING NEWS : " + password + userService.hashPassword(user.getUserPassword()));
//             // Compares the password hashed password in the DB with the one entered
//             return user;
//         }

//         return null;  // user not found
//     }
// }
