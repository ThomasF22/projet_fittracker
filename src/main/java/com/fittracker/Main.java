package com.fittracker;

import java.util.Scanner;

import com.fittracker.controller.WorkoutController;
import com.fittracker.view.WorkoutCLI;
import com.fittracker.view.WorkoutGUI;

public class Main {
    private static final AppMode mode = AppMode.GUI; // Can be changed to CLI or GUI
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WorkoutCLI workoutCLI = new WorkoutCLI(scanner);

        if (mode == AppMode.CLI) {
            workoutCLI.run();
        }
        else {
            javafx.application.Application.launch(WorkoutGUI.class);
        }
        scanner.close();
        // while (true) {
        //     int choice = WorkoutCLI.showMenu();
        //     switch (choice) {
        //         case 1:
        //             WorkoutController.addRoutine();
        //             break;
        //         case 2:
        //             WorkoutController.removeRoutine();
        //             break;
        //         case 3:
        //             WorkoutController.viewRoutines();
        //             break;
        //         case 4:
        //             WorkoutController.viewRoutineExercises();
        //             break;
            
        //         case 5:
        //             WorkoutController.addExerciseToRoutine();
        //             break;
        //         case 6:
        //             WorkoutController.removeExerciseFromRoutine();
        //             break;
        //         case 7:
        //             WorkoutController.doWorkoutSession();
        //             break;
        //         case 8:
        //             System.out.println("Goodbye!");
        //             return;
        //         default:
        //             System.out.println("Invalid choice!");
        //     }
        // }
    }
}
