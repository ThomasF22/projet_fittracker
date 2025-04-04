package com.fittracker.controller;

import java.util.Optional;

import com.fittracker.model.User;
import com.fittracker.model.UserDAO;
// import com.fittracker.service.AuthService;
import com.fittracker.service.UserService;
import com.fittracker.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private UserDAO userDAO = new UserDAO();
    private UserService userService = new UserService(userDAO);

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userService.authenticate(username, password); // Vérification dans la base de données

        if (user != null) {
            SessionManager.setUserId(user.getUserId()); // Stores the logged user's ID
            closeWindow();
        } else {
            System.out.println("\n\n\nuser EST null");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Utilisateur non trouvé");
            alert.setHeaderText("Aucun compte trouvé avec ces identifiants.");
            alert.setContentText("Voulez-vous créer un compte avec ces identifiants ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Create new account if user accepts to
                boolean success = userService.createUser(usernameField.getText(),passwordField.getText());
                if (success) {
                    showAlert("Compte créé", "Votre compte a été créé avec succès !", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Erreur", "Impossible de créer le compte.", Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
