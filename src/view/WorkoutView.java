package src.view;
import java.util.Scanner;

public class WorkoutView {
    private static Scanner scanner = new Scanner(System.in);

    public static int showMenu() {
        System.out.println("\nWorkout Routine Manager");
        System.out.println("1. Add Workout Routine");
        System.out.println("2. View Routines");
        System.out.println("3. View Exercises in a Routine");
        System.out.println("4. Add Exercise to a Routine");
        System.out.println("5. Start a Routine");
        System.out.println("6. Exit");
        System.out.print("Enter choice: ");
        return scanner.nextInt();
    }

    public static String getRoutineName() {
        System.out.print("Enter routine name: ");
        scanner.nextLine();  // Consume newline
        return scanner.nextLine();
    }

    public static int getRoutineId() {
        System.out.print("Enter routine ID: ");
        return scanner.nextInt();
    }

    public static int getExerciseId() {
        System.out.print("Enter exercise ID: ");
        return scanner.nextInt();
    }

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static Double getWeightInput() {
        System.out.print("Enter weight (kg) or press Enter to skip: ");
        String input = scanner.nextLine().trim(); // Assure que seule la ligne entière est lue
        return input.isEmpty() ? null : Double.parseDouble(input);
    }
    
    public static Integer getDurationInput() {
        System.out.print("Enter duration (seconds) or press Enter to skip: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : Integer.parseInt(input);
    }
    
    public static Integer getRepsInput() {
        System.out.print("Enter repetitions or press Enter to skip: ");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? null : Integer.parseInt(input);
    }
    
    
}
