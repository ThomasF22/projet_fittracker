package src.model;
import src.database.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDAO {
    public static List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT * FROM exercise";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                exercises.add(new Exercise(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public static void addExerciseToRoutine(int routineId, int exerciseId) {
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

    public static List<Exercise> getExercisesByRoutine(int routineId) {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT e.id, e.name, re.weight, re.duration, re.reps " +
                     "FROM exercise e " +
                     "JOIN routine_ex re ON e.id = re.idExercise " +
                     "WHERE re.idRoutine = ?";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Exercise exercise = new Exercise(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("weight"),
                    rs.getInt("duration"),
                    rs.getInt("reps")
                );
                exercises.add(exercise);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public static void updateExerciseDetails(int routineId, int exerciseId, Double weight, Integer duration, Integer reps) {
        String sql = "UPDATE routine_ex SET weight = ?, duration = ?, reps = ? WHERE idRoutine = ? AND idExercise = ?";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setObject(1, weight);
            stmt.setObject(2, duration);
            stmt.setObject(3, reps);
            stmt.setInt(4, routineId);
            stmt.setInt(5, exerciseId);
    
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
