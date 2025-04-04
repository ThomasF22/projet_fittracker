package com.fittracker.controller;

import com.fittracker.model.*;
import com.fittracker.service.ExerciseService;
import com.fittracker.service.RoutineExerciseService;
import com.fittracker.service.RoutineService;
import com.fittracker.util.SessionManager;
import com.fittracker.controller.WorkoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceDialog;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RoutineListController {

    private int userId = SessionManager.getLoggedInUserId();

    @FXML
    private ListView<Routine> routineListView;
    @FXML
    private Label routineIdLabel;
    @FXML
    private Label routineNameLabel;
    @FXML
    private Label routineDateLabel;
    @FXML
    private Label exerciseIdLabel;
    @FXML
    private Label exerciseNameLabel;
    @FXML
    private ListView<RoutineExercise> exerciseListView;

    private ObservableList<Routine> routineList;
    private ObservableList<RoutineExercise> exerciseList; 

    private ExerciseService exerciseService = new ExerciseService();
    private RoutineService routineService = new RoutineService();
    private RoutineExerciseService routineExerciseService = new RoutineExerciseService();



    @FXML
    public void initialize() {
        loadRoutines();

        // routine selection event handler
        routineListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handleRoutineSelection());
    }

    private void loadRoutines() {
        List<Routine> routines = routineService.getUserRoutines(userId);
        routineList = FXCollections.observableArrayList(routines);
        routineListView.setItems(routineList);
    }

    @FXML
    private void handleRoutineSelection() {
        Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
        if (selectedRoutine != null) {
            routineIdLabel.setText(String.valueOf(selectedRoutine.getId()));
            routineNameLabel.setText(String.valueOf(selectedRoutine.getName()));
            routineDateLabel.setText(selectedRoutine.getDateCreated().toString());
        
            loadExercises(selectedRoutine);
        }
    }

    // Load exercises for the selected routine
    private void loadExercises(Routine selectedRoutine) {
        exerciseIdLabel.setText("");
        exerciseNameLabel.setText("");
        // Saving the exercise before updating the list 
        RoutineExercise selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();
        // Fetching exercise for user's selected routine
        List<RoutineExercise> exercises = routineExerciseService.getRoutineExercisesForUser(selectedRoutine.getId(), userId);
        exerciseList = FXCollections.observableArrayList(exercises);
        exerciseListView.setItems(exerciseList);

        // Reselects the exercise if it's already in the list
        if (selectedExercise != null && exercises.contains(selectedExercise)) {
            exerciseListView.getSelectionModel().select(selectedExercise);
        }
    }
    @FXML
    private void handleExerciseSelection() {
        Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
        RoutineExercise selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();
        if (selectedExercise != null) {
            exerciseIdLabel.setText(String.valueOf(selectedExercise.getId()));
            exerciseNameLabel.setText(selectedExercise.toString());
            
            // loadExercises(selectedRoutine);
        }
    }

    @FXML
    private void deleteRoutine() {
        Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
        if (selectedRoutine == null) {
            showAlert("Erreur", "Veuillez sélectionner une routine à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        // Confirm before deleting
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Supprimer la routine ?");
        confirmAlert.setContentText("Voulez-vous vraiment supprimer cette routine ?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            RoutineDAO.removeRoutine(selectedRoutine.getId());
            loadRoutines(); // Refresh list after deletion
        }
    }

    @FXML
    private void createRoutine() {
        // Create a TextInputDialog to ask for routine name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer une Nouvelle Routine");
        dialog.setHeaderText("Entrez le nom de la nouvelle routine");
        dialog.setContentText("Nom de la routine:");

        // Get user input for the routine name
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            // Add the new routine to the database
            routineService.addRoutine(name, userId);
            loadRoutines(); // Refresh the list after adding the new routine
        });
    }

    @FXML
    private void createExercise() {
        // Create a TextInputDialog to ask for exercise name
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer un Nouvel Exercice");
        dialog.setHeaderText("Entrez le nom du nouvel exercice");
        dialog.setContentText("Nom de l'exercise:");

        // Get user input for the exercise name
        Optional<String> nameResult = dialog.showAndWait();
        String name = "";
        if (nameResult.isPresent()) {
            name = nameResult.get(); 
        }

        TextInputDialog descriptionDialog = new TextInputDialog();
        descriptionDialog.setTitle("Créer un Nouvel Exercice");
        descriptionDialog.setHeaderText("Entrez la description de l'exercice");
        descriptionDialog.setContentText("Description :");

        // Get user input for the exercise description
        Optional<String> descriptionResult = dialog.showAndWait();
        String description = "";
        if (descriptionResult.isPresent()) {
            description = descriptionResult.get(); 
        }
            exerciseService.createExercise(name, description, userId);
            loadRoutines();
    }

    @FXML
    private void deleteExercise() {
        Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
        RoutineExercise selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();
        if (selectedExercise == null) {
            showAlert("Erreur", "Veuillez sélectionner un exercice à supprimer.", Alert.AlertType.ERROR);
            return;
        }

        // Confirm before deleting
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Supprimer l'exercice ?");
        confirmAlert.setContentText("Voulez-vous vraiment supprimer cet exercice ?");
        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            routineExerciseService.removeExerciseFromRoutine(selectedRoutine.getId(),selectedExercise.getId());
            loadExercises(routineListView.getSelectionModel().getSelectedItem()); // Refresh exercise list after deletion
        }
    }

    @FXML
    private void addExerciseToRoutine() {
        List<Exercise> allExercises = exerciseService.getAllExercises();
        
        ChoiceDialog<Exercise> dialog = new ChoiceDialog<>(null, allExercises);
        dialog.setTitle("Ajouter un Exercice");
        dialog.setHeaderText("Sélectionnez un exercice à ajouter à la routine");
        dialog.setContentText("Exercice:");

        Optional<Exercise> result = dialog.showAndWait();
        result.ifPresent(exercise -> {
            // Add the selected exercise to the current routine
            Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
            if (selectedRoutine != null) {
                routineExerciseService.addExerciseToRoutine(selectedRoutine.getId(), exercise.getId());
                loadExercises(selectedRoutine); // Refresh the exercise list after adding the new exercise
            }
        });
    }

    @FXML
    private void handleStartWorkout() {
        Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
        if (selectedRoutine != null) {
            openSessionWindow(selectedRoutine);
        } else {
            System.out.println("Veuillez sélectionner une routine.");
        }
    }


    private void openSessionWindow(Routine routine) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fittracker/view/WorkoutSessionView.fxml"));
        Parent root = loader.load();

        // Pass the routine to the workout session controller
        WorkoutSessionController controller = loader.getController();
        controller.setRoutine(routine);

        Stage stage = new Stage();
        stage.setTitle("Séance - " + routine.getName());
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
