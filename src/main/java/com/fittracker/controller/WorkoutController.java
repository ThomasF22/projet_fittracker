package com.fittracker.controller;
import java.util.List;
import java.util.Scanner;

import com.fittracker.model.*;
import com.fittracker.view.WorkoutCLI;

public class WorkoutController {
    public static void addRoutine(String name) {
       return RoutineDAO.addRoutine(name);
    }

    public static boolean removeRoutine(int routineId){
        return RoutineDAO.removeRoutine(routineId);
    }

    public static List<Routine> getRoutines() {
        return RoutineDAO.getAllRoutines();
        // List<Routine> routines = RoutineDAO.getAllRoutines();
        // for (Routine routine : routines) {
        //     System.out.println("ID: " + routine.getId() + ", Name: " + routine.getName() + ", Created: " + routine.getDateCreated());
        // }

        
    }


    public static List<Exercise> getExercisesInRoutine(int routineId) {
        return ExerciseDAO.getExercisesByRoutine(routineId);
    }
    


    public static void viewRoutineExercises(Scanner scanner) {
        System.out.println("\n=== View Exercises in Routine ===");
        viewRoutines(scanner); // Show available routines
    
        // Get the routine ID from the user
        int routineId = WorkoutCLI.getRoutineId(scanner); 
    
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
    
    

    public static void addExerciseToRoutine(Scanner scanner) {
        viewRoutines(scanner);
        int routineId = WorkoutCLI.getRoutineId(scanner);
        
        List<Exercise> exercises = ExerciseDAO.getAllExercises();
        for (Exercise exercise : exercises) {
            System.out.println("ID: " + exercise.getId() + ", Name: " + exercise.getName());
        }

        int exerciseId = WorkoutCLI.getExerciseId(scanner);
        ExerciseDAO.addExerciseToRoutine(routineId, exerciseId);
    }

    public static void removeExerciseFromRoutine(Scanner scanner){
        viewRoutines(scanner);
        int routineId = WorkoutCLI.getRoutineId(scanner);

        List<Exercise> exercises = ExerciseDAO.getExercisesByRoutine(routineId);
        for (Exercise exercise : exercises) {
            System.out.println("ID: " + exercise.getId() + ", Name: " + exercise.getName());
        }
        
        int exerciseId = WorkoutCLI.getExerciseId(scanner);
        ExerciseDAO.removeExerciseFromRoutine(routineId, exerciseId);
    }

    public static void doWorkoutSession(Scanner scanner) {
        System.out.println("\n=== Start Workout Session ===");
        viewRoutines(scanner); // Display available routines
    
        // Get the routine ID from the user
        int routineId = WorkoutCLI.getRoutineId(scanner); 
    
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
                Double weight = WorkoutCLI.getWeightInput(scanner);
                Integer duration = WorkoutCLI.getDurationInput(scanner);
                Integer reps = WorkoutCLI.getRepsInput(scanner);
    
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
