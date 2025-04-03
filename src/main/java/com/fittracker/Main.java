package com.fittracker;

import java.util.Scanner;

import com.fittracker.controller.WorkoutController;
import com.fittracker.view.WorkoutCLI;
import com.fittracker.view.WorkoutGUI;

public class Main {
    private static final AppMode mode = AppMode.GUI; // Can be changed to CLI or GUI
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        if (mode == AppMode.CLI) {
            WorkoutCLI workoutCLI = new WorkoutCLI(scanner);
            workoutCLI.run();
        }
        else {
            javafx.application.Application.launch(WorkoutGUI.class);
        }
        scanner.close();
    }
}
