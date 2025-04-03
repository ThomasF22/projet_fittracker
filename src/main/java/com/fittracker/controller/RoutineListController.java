package com.fittracker.controller;

import com.fittracker.model.*;
import com.service.ExerciseService;
import com.service.RoutineExerciseService;
import com.service.RoutineService;
import com.fittracker.controller.WorkoutController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Optional;

public class RoutineListController {

    @FXML
    private ListView<Routine> routineListView;
    @FXML
    private Label routineIdLabel;
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
        List<Routine> routines = routineService.getAllRoutines();
        routineList = FXCollections.observableArrayList(routines);
        routineListView.setItems(routineList);
    }

    @FXML
    private void handleRoutineSelection() {
        Routine selectedRoutine = routineListView.getSelectionModel().getSelectedItem();
        if (selectedRoutine != null) {
            routineIdLabel.setText(String.valueOf(selectedRoutine.getId()));
            routineDateLabel.setText(selectedRoutine.getDateCreated().toString());
        
            loadExercises(selectedRoutine);
        }
    }

    // Load exercises for the selected routine
    private void loadExercises(Routine selectedRoutine) {
        exerciseIdLabel.setText("");
        exerciseNameLabel.setText("");
        // Sauvegarde de l'exercise avant MAJ de la liste
        RoutineExercise selectedExercise = exerciseListView.getSelectionModel().getSelectedItem();
        List<RoutineExercise> exercises = routineExerciseService.getRoutineExercisesById(selectedRoutine.getId());
        exerciseList = FXCollections.observableArrayList(exercises);
        exerciseListView.setItems(exerciseList);

        // Restauration de l'exercise après MAJ de la liste
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
            RoutineDAO.addRoutine(name);
            loadRoutines(); // Refresh the list after adding the new routine
        });
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

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
