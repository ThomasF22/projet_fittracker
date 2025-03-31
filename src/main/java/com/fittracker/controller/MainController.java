package com.fittracker.controller;



import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button goToShowRoutines;
    @FXML
    private Button goToWorkoutSession;
    @FXML
    private Button exitApp;


    @FXML
    private void goToShowRoutines() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fittracker/view/RoutineListView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Liste des Routines");
            stage.setScene(new Scene(root, 400, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 
    @FXML
    private void exitApp() {
        // Logique pour quitter l'application
        showAlert("Quitter", "L'application va se fermer.");
        System.exit(0);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}