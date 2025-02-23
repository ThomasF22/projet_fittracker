package src;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Workout Manager");
            System.out.println("1. Add Workout");
            System.out.println("2. View Workouts");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addWorkout(scanner);
                    break;
                case 2:
                    viewRoutines();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addWorkout(Scanner scanner) {
        System.out.print("Enter workout name: ");
        String name = scanner.nextLine();
        System.out.print("Enter workout type (Cardio, Strength, Flexibility, Other): ");
        String type = scanner.nextLine();
        System.out.print("Enter duration (minutes): ");
        int duration = scanner.nextInt();

        String sql = "INSERT INTO workouts (name, type, duration) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setInt(3, duration);
            stmt.executeUpdate();
            System.out.println("Workout added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewRoutines() {
        String sql = "SELECT * FROM routine";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Nom: " + rs.getString("name") +
                        ", Date de création: " + rs.getDate("dateCreated"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
