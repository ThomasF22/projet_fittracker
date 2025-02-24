package src.model;
import src.database.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static void addRoutine(String name) {
        String sql = "INSERT INTO routine (name, dateCreated) VALUES (?, NOW())";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

            System.out.println("Workout routine added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
