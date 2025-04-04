package com.fittracker.model;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fittracker.config.Database;

public class ExerciseDAO {
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT id,name FROM exercise";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                exercises.add(new Exercise(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching exercises!" + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
        return exercises;
    }
    public List<Exercise> getUserExercises(int userId) {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT id,name FROM exercise WHERE creatorId = 0 OR creatorId = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
             stmt.setInt(1, userId);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                exercises.add(new Exercise(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching exercises!" + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
        return exercises;
    }

    // TODO : getExerciseById

    // public static List<Exercise> getExerciseById() {
    //     Exercise exercise = new Exercise();
    //     String sql = "SELECT id,name FROM exercise";

    //     try (Connection conn = Database.getConnection();
    //          PreparedStatement stmt = conn.prepareStatement(sql);
    //          ResultSet rs = stmt.executeQuery()) {

    //         while (rs.next()) {
    //             exercises.add(new Exercise(rs.getInt("id"), rs.getString("name")));
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Error while fetching exercises!" + e.getMessage());
    //         e.printStackTrace();
    //         return Collections.emptyList();
    //     }
    //     return exercises;
    // }
    
    
}
