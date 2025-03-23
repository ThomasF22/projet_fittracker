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
}
