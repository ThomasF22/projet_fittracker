package src.controller;
import src.model.*;
import src.view.WorkoutView;
import java.util.List;

public class WorkoutController {
    public static void addRoutine() {
        String name = WorkoutView.getRoutineName();
        RoutineDAO.addRoutine(name);
    }

    public static void removeRoutine(){
        viewRoutines();
        int routineId = WorkoutView.getRoutineId();
        RoutineDAO.removeRoutine(routineId);
    }

    public static void viewRoutines() {
        List<Routine> routines = RoutineDAO.getAllRoutines();
        for (Routine routine : routines) {
            System.out.println("ID: " + routine.getId() + ", Name: " + routine.getName() + ", Created: " + routine.getDateCreated());
        }
    }

    public static void viewRoutineExercises() {
        System.out.println("\n=== View Exercises in Routine ===");
        viewRoutines(); // Show available routines
    
        // Get the routine ID from the user
        int routineId = WorkoutView.getRoutineId(); 
    
        // Fetch exercises for the selected routine
        List<Exercise> exercises = ExerciseDAO.getExercisesByRoutine(routineId);
    
        // Display exercises, if available
        if (exercises.isEmpty()) {
            System.out.println("No exercises found for this routine.");
        } else {
            System.out.println("\nExercises in Routine ID " + routineId + ":");
            for (Exercise exercise : exercises) {
                // Display exercise details
                System.out.println("- " + exercise.getName() + 
                                   ", Weight: " + exercise.getWeight() + 
                                   "kg, Duration: " + exercise.getDuration() + 
                                   "s, Reps: " + exercise.getReps());
            }
        }
    }
    
    

    public static void addExerciseToRoutine() {
        viewRoutines();
        int routineId = WorkoutView.getRoutineId();
        
        List<Exercise> exercises = ExerciseDAO.getAllExercises();
        for (Exercise exercise : exercises) {
            System.out.println("ID: " + exercise.getId() + ", Name: " + exercise.getName());
        }

        int exerciseId = WorkoutView.getExerciseId();
        ExerciseDAO.addExerciseToRoutine(routineId, exerciseId);
    }

    public static void removeExerciseFromRoutine(){
        viewRoutines();
        int routineId = WorkoutView.getRoutineId();

        List<Exercise> exercises = ExerciseDAO.getExercisesByRoutine(routineId);
        for (Exercise exercise : exercises) {
            System.out.println("ID: " + exercise.getId() + ", Name: " + exercise.getName());
        }
        
        int exerciseId = WorkoutView.getExerciseId();
        ExerciseDAO.removeExerciseFromRoutine(routineId, exerciseId);
    }

    public static void doWorkoutSession() {
        System.out.println("\n=== Start Workout Session ===");
        viewRoutines(); // Display available routines
    
        // Get the routine ID from the user
        int routineId = WorkoutView.getRoutineId(); 
    
        // Fetch exercises for the selected routine
        List<Exercise> exercises = ExerciseDAO.getExercisesByRoutine(routineId);
    
        if (exercises.isEmpty()) {
            System.out.println("No exercises found for this routine.");
        } else {
            System.out.println("\nStarting workout session for Routine ID: " + routineId);
    
            // Loop through each exercise and ask the user for input
            for (Exercise exercise : exercises) {
                System.out.println("Exercise: " + exercise.getName());
    
                // Ask user for input for weight, duration, and reps (can be skipped)
                Double weight = WorkoutView.getWeightInput();
                Integer duration = WorkoutView.getDurationInput();
                Integer reps = WorkoutView.getRepsInput();
    
                // Update the exercise details for the routine in the database
                ExerciseDAO.updateExerciseDetails(routineId, exercise.getId(), weight, duration, reps);
                System.out.println("Exercise completed: " + exercise.getName());
            }
    
            System.out.println("Workout session completed!");
    
            // Display the completed workout session
            System.out.println("\nYour completed workout session:");
            List<Exercise> completedExercises = ExerciseDAO.getExercisesByRoutine(routineId);
    
            for (Exercise exercise : completedExercises) {
                System.out.println("- " + exercise.getName() + 
                                   ", Weight: " + exercise.getWeight() + 
                                   "kg, Duration: " + exercise.getDuration() + 
                                   "s, Reps: " + exercise.getReps());
            }
        }
    }
    
}
