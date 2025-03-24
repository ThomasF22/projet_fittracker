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
    private Button goToShowExercises;
    @FXML
    private Button goToAddExercise;
    @FXML
    private Button goToRemoveExercise;
    @FXML
    private Button goToWorkoutSession;
    @FXML
    private Button exitApp;

@FXML
private void goToAddRoutine() {
    // Create a dialog for user input
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Ajouter une routine");
    dialog.setHeaderText("Ajouter une nouvelle routine");
    dialog.setContentText("Nom de la routine:");

    // Show the dialog and wait for user input
    Optional<String> result = dialog.showAndWait();
    
    result.ifPresent(routineName -> {
        if (!routineName.trim().isEmpty()) {
            boolean success = WorkoutController.addRoutine(routineName);
            if (success) {
                showAlert("Succès", "Routine ajoutée avec succès !");
            } else {
                showAlert("Erreur", "Impossible d'ajouter la routine.");
            }
        } else {
            showAlert("Erreur", "Le nom de la routine ne peut pas être vide.");
        }
    });
}

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
    private void goToShowExercises() {
        // Logique pour afficher les exercices d'une routine
        showAlert("Voir les exercices d'une routine", "C'est l'option pour voir les exercices d'une routine.");
    }

    @FXML
    private void goToAddExercise() {
        // Logique pour ajouter un exercice
        showAlert("Ajouter un exercice", "C'est l'option pour ajouter un exercice.");
    }

    @FXML
    private void goToRemoveExercise() {
        // Logique pour retirer un exercice
        showAlert("Retirer un exercice", "C'est l'option pour retirer un exercice.");
    }

    @FXML
    private void goToWorkoutSession() {
        // Logique pour démarrer une séance
        showAlert("Démarrer une séance", "C'est l'option pour démarrer une séance.");
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