package com.fittracker.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;

import com.fittracker.model.Routine;
import com.fittracker.model.RoutineDAO;
import com.fittracker.model.RoutineExercise;
import com.fittracker.model.RoutineExerciseDAO;
import com.fittracker.service.RoutineExerciseService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class WorkoutSessionController {
    private RoutineExerciseDAO routineExerciseDAO = new RoutineExerciseDAO();

    @FXML 
    private TableView<RoutineExercise> exerciseTableView;
    @FXML 
    private TableColumn<RoutineExercise, String> exerciseColumn;
    @FXML 
    private TableColumn<RoutineExercise, TextField> weightColumn;
    @FXML 
    private TableColumn<RoutineExercise, TextField> repsColumn;
    @FXML 
    private TableColumn<RoutineExercise, TextField> durationColumn;

    private Routine routine;
    private List<RoutineExercise> routineExercises = new ArrayList<>();
    private RoutineExerciseService routineExerciseService = new RoutineExerciseService();

    // Loads a routine and it's exercises
    // (takes the routine that was passed)
    public void setRoutine(Routine routine) {
        this.routine = routine;
        loadExercises();
    }


    private void loadExercises() {
        // Fetches exercises, populates a corresponding observablelist
        routineExercises = routineExerciseDAO.getRoutineExercisesById(routine.getId());
        ObservableList<RoutineExercise> exerciseList = FXCollections.observableArrayList(routineExercises);
    
        exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseName"));
    
        // Mise à jour de la colonne "weight"
        // sets how column cell is rendered
        // RoutineExercise is the TableView's type, TextField is the cell's type
        weightColumn.setCellFactory(col -> new TableCell<RoutineExercise, TextField>() {
            // text where editable value will be displayed
            private final TextField textField = new TextField();
            
            @Override
            protected void updateItem(TextField item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Afficher la valeur actuelle du poids
                    textField.setText(String.valueOf(getTableRow().getItem().getWeight()));
                    setGraphic(textField);
                    
                    // Mettre à jour la valeur du poids lorsque l'utilisateur modifie le TextField
                    textField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && !newValue.isEmpty()) {
                            // Mettre à jour l'objet RoutineExercise avec la nouvelle valeur
                            getTableRow().getItem().setWeight(Double.parseDouble(newValue));
                        }
                    });
                }
            }
        });
    
        // Mise à jour de la colonne "reps"
        repsColumn.setCellFactory(col -> new TableCell<RoutineExercise, TextField>() {
            private final TextField textField = new TextField();
    
            @Override
            protected void updateItem(TextField item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Afficher la valeur actuelle des répétitions
                    textField.setText(String.valueOf(getTableRow().getItem().getReps()));
                    setGraphic(textField);
    
                    // Mettre à jour la valeur des répétitions lorsque l'utilisateur modifie le TextField
                    textField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && !newValue.isEmpty()) {
                            // Mettre à jour l'objet RoutineExercise avec la nouvelle valeur
                            getTableRow().getItem().setReps(Integer.parseInt(newValue));
                        }
                    });
                }
            }
        });
    
        // Mise à jour de la colonne "duration"
        durationColumn.setCellFactory(col -> new TableCell<RoutineExercise, TextField>() {
            private final TextField textField = new TextField();
    
            @Override
            protected void updateItem(TextField item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Afficher la valeur actuelle de la durée
                    textField.setText(String.valueOf(getTableRow().getItem().getDuration()));
                    setGraphic(textField);
    
                    // Mettre à jour la valeur de la durée lorsque l'utilisateur modifie le TextField
                    textField.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && !newValue.isEmpty()) {
                            // Mettre à jour l'objet RoutineExercise avec la nouvelle valeur
                            getTableRow().getItem().setDuration(Integer.parseInt(newValue));
                        }
                    });
                }
            }
        });
    
        // Lier la liste des exercices à la TableView
        exerciseTableView.setItems(exerciseList);
    }
    

    @FXML
    private void handleEndSession() {
        for (RoutineExercise routineExercise : routineExercises) {
            System.out.println("\nAN EXERCISE IS BEING UPDATED...");
            System.out.println("\nEXERCISE : " + routine.getId() + "\n" + routineExercise.getExerciseId() + "\n" + routineExercise.getWeight());
            routineExerciseService.updateExerciseDetails(
                routine.getId(),
                routineExercise.getExerciseId(),
                routineExercise.getWeight(),
                routineExercise.getReps(),
                routineExercise.getDuration());
        }
        System.out.println("Séance enregistrée !");
        ((Stage) exerciseTableView.getScene().getWindow()).close();
    }
}

