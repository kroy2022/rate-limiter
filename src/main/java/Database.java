import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dto.DatabaseResponse;
import dto.UserDTO;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:ratelimiter.db";

    public static void main(String[] args) {
        initializeDatabase();
    }

    /**
     * Creates the connection and sets up the initial table.
     */
    private static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "user_id TEXT NOT NULL, "
                + "api_key TEXT NOT NULL" 
                + "plane TEXT NOT NULL"
                + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createTableSQL);
            System.out.println("Database initialized and table is ready.");
            
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public DatabaseResponse createUser(String userId, String plan, String apiKey) {
        UserDTO user = new UserDTO(userId, plan);
        DatabaseResponse dr = new DatabaseResponse(true, "Successfully created User: " + userId, user, apiKey);
        String insertQuery = "INSERT Into users (user_id, api_key) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            
            pstmt.setString(1, userId);
            pstmt.setString(2, plan);
            pstmt.setString(3, apiKey);
            pstmt.executeUpdate();
            System.out.println("Recorded new request for user: " + userId);            
        } catch (SQLException e) {
            dr.setSuccess(false);
            dr.setMessage("Database Error: " + e.getMessage());
            System.err.println("Error inserting record: " + e.getMessage());
        } 

        return dr;
    }

    public String getUserApiKey(String userId) {
        String selectQuery = "SELECT plan, api_key FROM users WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL); 
            PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
                pstmt.setString(1, userId);
    
                try (ResultSet rs = pstmt.executeQuery()) {

                    if (rs.next()) {
                        String apiKey = rs.getString("api_key");
                        System.out.println("API Key: " + apiKey);
                        return apiKey;
                    } else {
                        System.out.println("No user found with ID: " + userId);
                        return "";
                    }
                }
                
        } catch (SQLException e) { 
            System.err.println("Error selecting api key: " + e.getMessage());
            return "";
        } 
    }
}