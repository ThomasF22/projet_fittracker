package src;

import src.controller.WorkoutController;
import src.view.WorkoutView;

public class Main {
    public static void main(String[] args) {
        while (true) {
            int choice = WorkoutView.showMenu();
            switch (choice) {
                case 1:
                    WorkoutController.addRoutine();
                    break;
                case 2:
                    WorkoutController.viewRoutines();
                    break;
                case 3:
                    WorkoutController.viewRoutineExercises();
                    break;
            
                case 4:
                    WorkoutController.addExerciseToRoutine();
                    break;

                case 5:
                    WorkoutController.doWorkoutSession();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
