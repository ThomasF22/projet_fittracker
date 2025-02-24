package src;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Workout Manager");
            System.out.println("1. Add Workout Routine");
            System.out.println("2. View Workouts");
            System.out.println("3. View Exercises in a Routine");
            System.out.println("4. Add Exercise to a Routine");
            System.out.println("5. Do a Workout Session");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addRoutine(scanner);
                    break;
                case 2:
                    viewRoutines();
                    break;
                case 3:
                    viewRoutineExercises(scanner);
                    break;
                case 4:
                    addExerciseToRoutine(scanner);
                    break;
                case 5:
                    doWorkoutSession(scanner);
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addRoutine(Scanner scanner) {
        System.out.print("Enter routine name: ");
        String name = scanner.nextLine();

        // On insère d'abord la routine
        String sql = "INSERT INTO routine (name, dateCreated) VALUES (?, NOW())";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

            // Récupérer l'ID de la nouvelle routine
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int routineId = 0;
            if (generatedKeys.next()) {
                routineId = generatedKeys.getInt(1);
            }

            System.out.println("Workout routine added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addExerciseToRoutine(Scanner scanner) {
        System.out.println("Available routines:");
        viewRoutines(); // Affiche toutes les routines disponibles

        System.out.print("Enter the ID of the routine you want to add an exercise to: ");
        int routineId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Affiche tous les exercices disponibles
        System.out.println("Available exercises:");
        viewExercises();

        System.out.print("Enter the ID of the exercise you want to add: ");
        int exerciseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Ajouter l'exercice à la routine
        String sql = "INSERT INTO routine_ex (idRoutine, idExercise) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routineId);
            stmt.setInt(2, exerciseId);
            stmt.executeUpdate();
            System.out.println("Exercise added to routine successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewRoutines() {
        String sql = "SELECT * FROM routine";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Available routines:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Date Created: " + rs.getDate("dateCreated"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewExercises() {
        String sql = "SELECT * FROM exercise";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Available exercises:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Description: " + rs.getString("description"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewRoutineExercises(Scanner scanner) {
        System.out.println("Available routines:");
        viewRoutines(); // Affiche toutes les routines disponibles

        System.out.print("Enter the ID of the routine you want to view exercises for: ");
        int routineId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Afficher les exercices associés à la routine sélectionnée avec poids, durée et répétitions
        String sql = "SELECT e.name, re.weight, re.duration, re.reps " +
                     "FROM exercise e " +
                     "JOIN routine_ex re ON e.id = re.idExercise " +
                     "WHERE re.idRoutine = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Exercises for this routine:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("name") +
                        ", Weight: " + rs.getDouble("weight") +
                        ", Duration: " + rs.getInt("duration") +
                        " seconds, Reps: " + rs.getInt("reps"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Nouvelle méthode pour faire une séance
    private static void doWorkoutSession(Scanner scanner) {
        System.out.println("Available routines:");
        viewRoutines(); // Affiche toutes les routines disponibles

        System.out.print("Enter the ID of the routine you want to do: ");
        int routineId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Afficher les exercices associés à la routine sélectionnée
        String sql = "SELECT e.id, e.name, re.weight, re.duration, re.reps " +
                     "FROM exercise e " +
                     "JOIN routine_ex re ON e.id = re.idExercise " +
                     "WHERE re.idRoutine = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Start your workout session:");

            // Parcourir chaque exercice et demander à l'utilisateur de saisir les données
            while (rs.next()) {
                System.out.println("Exercise: " + rs.getString("name"));

                // Demander à l'utilisateur de saisir les informations de la séance
                System.out.print("Enter weight (kg) or press Enter to skip: ");
                String weightInput = scanner.nextLine();
                Double weight = weightInput.isEmpty() ? null : Double.parseDouble(weightInput);

                System.out.print("Enter duration (seconds) or press Enter to skip: ");
                String durationInput = scanner.nextLine();
                Integer duration = durationInput.isEmpty() ? null : Integer.parseInt(durationInput);

                System.out.print("Enter repetitions or press Enter to skip: ");
                String repsInput = scanner.nextLine();
                Integer reps = repsInput.isEmpty() ? null : Integer.parseInt(repsInput);

                // Mettre à jour la table `routine_ex` avec les données de la séance
                String updateSql = "UPDATE routine_ex SET weight = ?, duration = ?, reps = ? " +
                                   "WHERE idRoutine = ? AND idExercise = ?";

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setObject(1, weight);
                    updateStmt.setObject(2, duration);
                    updateStmt.setObject(3, reps);
                    updateStmt.setInt(4, routineId);
                    updateStmt.setInt(5, rs.getInt("id"));

                    updateStmt.executeUpdate();
                    System.out.println("Exercise completed!");
                }
            }

            System.out.println("Workout session completed!");

            // Afficher les exercices réalisés avec les données renseignées
            System.out.println("\nYour completed workout session:");
            String completedSql = "SELECT e.name, re.weight, re.duration, re.reps " +
                                  "FROM exercise e " +
                                  "JOIN routine_ex re ON e.id = re.idExercise " +
                                  "WHERE re.idRoutine = ?";

            try (PreparedStatement completedStmt = conn.prepareStatement(completedSql)) {
                completedStmt.setInt(1, routineId);
                ResultSet completedRs = completedStmt.executeQuery();

                while (completedRs.next()) {
                    System.out.println("- " + completedRs.getString("name") +
                            ", Weight: " + completedRs.getDouble("weight") +
                            ", Duration: " + completedRs.getInt("duration") +
                            " seconds, Reps: " + completedRs.getInt("reps"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/l3_projet_fittracker";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL Driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
