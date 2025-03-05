package com.fittracker;

import com.fittracker.controller.WorkoutController;
import com.fittracker.view.WorkoutView;

public class Main {
    public static void main(String[] args) {
        while (true) {
            int choice = WorkoutView.showMenu();
            switch (choice) {
                case 1:
                    WorkoutController.addRoutine();
                    break;
                case 2:
                    WorkoutController.removeRoutine();
                    break;
                case 3:
                    WorkoutController.viewRoutines();
                    break;
                case 4:
                    WorkoutController.viewRoutineExercises();
                    break;
            
                case 5:
                    WorkoutController.addExerciseToRoutine();
                    break;
                case 6:
                    WorkoutController.removeExerciseFromRoutine();
                    break;
                case 7:
                    WorkoutController.doWorkoutSession();
                    break;
                case 8:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
