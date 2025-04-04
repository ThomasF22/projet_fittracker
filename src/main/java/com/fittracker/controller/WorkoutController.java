package com.fittracker.controller;
import java.util.List;
import java.util.Scanner;

import com.fittracker.model.*;
import com.fittracker.service.ExerciseService;
import com.fittracker.view.WorkoutCLI;

public class WorkoutController {
    private ExerciseService exerciseService = new ExerciseService();

    private RoutineDAO routineDAO = new RoutineDAO();

    public List<Exercise> getAllExercises(){
        return exerciseService.getAllExercises();
    }
    public boolean addRoutine(String name, int userId) {
       return routineDAO.addRoutine(name, userId);
    }

    public boolean removeRoutine(int routineId){
        return routineDAO.removeRoutine(routineId);
    }

    public List<Routine> getRoutines() {
        return routineDAO.getAllRoutines();
        
    }


    // public static List<RoutineExercise> getExercisesInRoutine(int routineId) {
    //     return RoutineExerciseDAO.getRoutineExercisesById(routineId);
    // }

    // public static boolean addExerciseToRoutine(int routineId, int exerciseId){
    //     return RoutineExerciseDAO.addExerciseToRoutine(routineId, exerciseId);
    // }
    
    // public static boolean removeExerciseFromRoutine(int routineId, int exerciseId){
    //     return RoutineExerciseDAO.removeExerciseFromRoutine(routineId, exerciseId);
    // }

    // public static boolean updateExerciseDetails(int routineId, int exerciseId, double weight, int duration, int reps){
    //     return RoutineExerciseDAO.updateExerciseDetails(routineId, exerciseId, weight, duration, reps);
    // }




    

    // public static void doWorkoutSession(Scanner scanner) {
    //     System.out.println("\n=== Start Workout Session ===");
    //     viewRoutines(scanner); // Display available routines
    
    //     // Get the routine ID from the user
    //     int routineId = WorkoutCLI.getRoutineId(scanner); 
    
    //     // Fetch exercises for the selected routine
    //     List<Exercise> exercises = ExerciseDAO.getExercisesByRoutine(routineId);
    
    //     if (exercises.isEmpty()) {
    //         System.out.println("No exercises found for this routine.");
    //     } else {
    //         System.out.println("\nStarting workout session for Routine ID: " + routineId);
    
    //         // Loop through each exercise and ask the user for input
    //         for (Exercise exercise : exercises) {
    //             System.out.println("Exercise: " + exercise.getName());
    
    //             // Ask user for input for weight, duration, and reps (can be skipped)
    //             Double weight = WorkoutCLI.getWeightInput(scanner);
    //             Integer duration = WorkoutCLI.getDurationInput(scanner);
    //             Integer reps = WorkoutCLI.getRepsInput(scanner);
    
    //             // Update the exercise details for the routine in the database
    //             ExerciseDAO.updateExerciseDetails(routineId, exercise.getId(), weight, duration, reps);
    //             System.out.println("Exercise completed: " + exercise.getName());
    //         }
    
    //         System.out.println("Workout session completed!");
    
    //         // Display the completed workout session
    //         System.out.println("\nYour completed workout session:");
    //         List<Exercise> completedExercises = ExerciseDAO.getExercisesByRoutine(routineId);
    
    //         for (Exercise exercise : completedExercises) {
    //             System.out.println("- " + exercise.getName() + 
    //                                ", Weight: " + exercise.getWeight() + 
    //                                "kg, Duration: " + exercise.getDuration() + 
    //                                "s, Reps: " + exercise.getReps());
    //         }
    //     }
    }

