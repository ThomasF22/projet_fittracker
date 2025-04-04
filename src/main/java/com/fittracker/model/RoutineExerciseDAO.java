package com.fittracker.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fittracker.config.Database;
public class RoutineExerciseDAO {

    public List<RoutineExercise> getRoutineExercisesById(int routineId) {
        List<RoutineExercise> exercises = new ArrayList<>();
        String sql = "SELECT e.name AS nom_exercise , e.id, re.weight, re.duration, re.reps " +
                     "FROM exercise e " +
                     "JOIN routine_ex re ON e.id = re.idExercise " +
                     "WHERE re.idRoutine = ?";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, routineId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                RoutineExercise routineExercise = new RoutineExercise(
                    rs.getInt("id"),
                    new Exercise(rs.getInt("id"), rs.getString("nom_exercise")),
                    rs.getDouble("weight"),
                    rs.getInt("duration"),
                    rs.getInt("reps")
                );
                exercises.add(routineExercise);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public List<RoutineExercise> getRoutineExercisesForUser(int routineId, int userId) {
        List<RoutineExercise> exercises = new ArrayList<>();
        String sql = "SELECT e.name AS nom_exercise , e.id, re.weight, re.duration, re.reps " +
                     "FROM exercise e " +
                     "JOIN routine_ex re ON e.id = re.idExercise " +
                     "WHERE re.idRoutine = ? AND creatorId = 0 OR creatorId = ?";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, routineId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                RoutineExercise routineExercise = new RoutineExercise(
                    rs.getInt("id"),
                    new Exercise(rs.getInt("id"), rs.getString("nom_exercise")),
                    rs.getDouble("weight"),
                    rs.getInt("duration"),
                    rs.getInt("reps")
                );
                exercises.add(routineExercise);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    public boolean updateExerciseDetails(int routineId, int exerciseId, Double weight, Integer duration, Integer reps) {
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

    public boolean addExerciseToRoutine(int routineId, int exerciseId) {
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

    public boolean removeExerciseFromRoutine(int routineId, int exerciseId){
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
    
}
