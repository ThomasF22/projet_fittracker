package com.fittracker.view;
import java.util.List;
import java.util.Scanner;
import com.fittracker.controller.WorkoutController;
import com.fittracker.model.Exercise;
import com.fittracker.model.ExerciseDAO;
import com.fittracker.model.Routine;

public class WorkoutCLI {
    private Scanner scanner;

    public WorkoutCLI(Scanner scanner){
        this.scanner = scanner;
    }
    public void run() {
        
        while (true) {
            int choice = showMenu();
            switch (choice) {
                case 1 -> addRoutine();
                case 2 -> deleteRoutine(); 
                case 3 -> showRoutines();
                case 4 -> showExercisesInRoutine(getRoutineId());
                case 5 -> addExerciseToRoutine(); // idem
                case 6 -> removeExerciseFromRoutine();
                // case 7 -> WorkoutController.doWorkoutSession(scanner);
                case 8 -> {
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public int showMenu() {
        System.out.println("\nWorkout Routine Manager");
        System.out.println("1. Add Workout Routine");
        System.out.println("2. Remove Workout Routine");
        System.out.println("3. View Routines");
        System.out.println("4. View Exercises in a Routine");
        System.out.println("5. Add Exercise to a Routine");
        System.out.println("6. Remove Exercise from a Routine");
        System.out.println("7. Start a Routine");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
        return scanner.nextInt();
    }

    public void addRoutine(){
        System.out.println("Entrez le nom de la Routine:");
        String routineName = getRoutineName();
        if(WorkoutController.addRoutine(routineName)){
            System.out.println("Routine ajoutée!");
        }
        else{
            System.out.println("Erreur, routine pas ajoutée!");
        }; 

    }

    public void showRoutines() {
    List<Routine> routines = WorkoutController.getRoutines();  // Fetch from controller
    if (routines.isEmpty()) {
        System.out.println("No routines found.");
    } else {
        System.out.println("\n=== Workout Routines ===");
        for (Routine routine : routines) {
            System.out.println("ID: " + routine.getId() + ", Name: " + routine.getName() + ", Created: " + routine.getDateCreated());
        }
    }
    }

    public void showExercisesInRoutine(int routineId) {
        showRoutines(); // Show available routines first
        List<Exercise> exercises = WorkoutController.getExercisesInRoutine(routineId);

        if (exercises.isEmpty()) {
            System.out.println("No exercises found for this routine.");
        } else {
            System.out.println("\nExercises in Routine ID " + routineId + ":");
            for (Exercise exercise : exercises) {
                System.out.println("- " + exercise.getName() + 
                                ", Weight: " + exercise.getWeight() + 
                                "kg, Duration: " + exercise.getDuration() + 
                                "s, Reps: " + exercise.getReps());
            }
        }
    }

    public void addExerciseToRoutine(){
        showRoutines();
        int routineId = getRoutineId();
        List<Exercise> exercises = ExerciseDAO.getAllExercises();
        if (exercises.isEmpty()){
            System.out.println("Pas d'exercises dans la base de données!");
        }

        else {
            System.out.println("\n Exercice : \n");
            for (Exercise exercise : exercises){
              System.out.println("\nID : " + exercise.getId() + ", Nom : " + exercise.getName());
            }
            System.out.println("\n Veuillez choisir l'ID de l'exercise à rajouter.");
            }
        int exerciseId = getExerciseId();
        
        if (WorkoutController.addExerciseToRoutine(routineId, exerciseId)){
            System.out.println("Exercice ajouté!");
        }
        else {
            System.out.println("Erreur bro!!");
        }
        }


    public void removeExerciseFromRoutine(){
        showRoutines();
        int routineId = getRoutineId();
        showExercisesInRoutine(routineId);
        System.out.println("\nEntrez l'ID de la routine");
        int exerciseId = getExerciseId();
        if (WorkoutController.removeExerciseFromRoutine(routineId, exerciseId)){
            System.out.println("\n Exercice supprimé de la routine!");
        }
        else{
            System.out.println("Erreur!");
        }
    }

    public void deleteRoutine() {
        showRoutines();
        int routineId = getRoutineId();
        
        boolean success = WorkoutController.removeRoutine(routineId);
        if (success) {
            System.out.println("Routine removed successfully!");
        } else {
            System.out.println("Failed to remove routine.");
        }
    }
    



    public String getRoutineName() {
        System.out.print("Enter routine name: ");
        scanner.nextLine();  // Consume newline
        return scanner.nextLine();
    }

    public int getRoutineId() {
        System.out.print("Enter routine ID: ");
        return scanner.nextInt();
    }

    public int getExerciseId() {
        System.out.print("Enter exercise ID: ");
        return scanner.nextInt();
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public Double getWeightInput() {
        System.out.print("Enter weight (kg) or press Enter to skip: ");
        String input = scanner.nextLine().trim(); // Assure que seule la ligne entière est lue
        return input.isEmpty() ? null : Double.parseDouble(input);
    }
    
    public Integer getDurationInput() {
        System.out.print("Enter duration (seconds) or press Enter to skip: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : Integer.parseInt(input);
    }
    
    public Integer getRepsInput() {
        System.out.print("Enter repetitions or press Enter to skip: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : Integer.parseInt(input);
    }
    
    
}
