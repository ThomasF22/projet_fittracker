package com.fittracker.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fittracker.config.Database;

public class RoutineDAO {
    public static List<Routine> getAllRoutines() {
        List<Routine> routines = new ArrayList<>();
        String sql = "SELECT * FROM routine";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                routines.add(new Routine(rs.getInt("id"), rs.getString("name"), rs.getDate("dateCreated")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routines;
    }

    public static boolean addRoutine(String name) {
        String sql = "INSERT INTO routine (name, dateCreated) VALUES (?, NOW())";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeRoutine(int routineId) {
        String sql = "DELETE FROM routine WHERE routine.id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routineId);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
                return false;
    }

    public static boolean addExerciseToRoutine(int routineId, int exerciseId) {
        String sql = "INSERT INTO routine_ex (idRoutine, idExercise) VALUES (?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routineId);
            stmt.setInt(2, exerciseId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeExerciseFromRoutine(int routineId, int exerciseId){
        String sql = "DELETE FROM routine_ex WHERE routine_ex.idRoutine = ? AND routine_ex.idExercise = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, routineId);
            stmt.setInt(2, exerciseId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateExerciseDetails(int routineId, int exerciseId, Double weight, Integer duration, Integer reps) {
        String sql = "UPDATE routine_ex SET weight = ?, duration = ?, reps = ? WHERE idRoutine = ? AND idExercise = ?";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setObject(1, weight);
            stmt.setObject(2, duration);
            stmt.setObject(3, reps);
            stmt.setInt(4, routineId);
            stmt.setInt(5, exerciseId);
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
