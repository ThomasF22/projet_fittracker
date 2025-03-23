package com.fittracker.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import com.fittracker.controller.WorkoutController;

public class WorkoutGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FitTracker");

        // GUI Components
        Button viewRoutinesButton = new Button("Voir les routines");
        ListView<String> routinesListView = new ListView();


        // viewRoutinesButton.setOnAction(e -> {
        //     List<String> routines = WorkoutController.viewRoutines();
        //     routinesListView.getItems().setAll(routines);
        // });

        VBox layout = new VBox(10, viewRoutinesButton);
        Scene scene = new Scene(layout, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
