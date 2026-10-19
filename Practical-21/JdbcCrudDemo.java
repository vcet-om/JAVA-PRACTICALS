import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCrudDemo {
    private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Database connected successfully.");

            try (Statement stmt = conn.createStatement()) {
                String createTable = "CREATE TABLE students (id INT PRIMARY KEY, name VARCHAR(50), marks DOUBLE)";
                stmt.execute(createTable);
                System.out.println("Table 'students' created.");
            }

            String insertSQL = "INSERT INTO students (id, name, marks) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, 1);
                pstmt.setString(2, "Rahul");
                pstmt.setDouble(3, 88.5);
                pstmt.executeUpdate();

                pstmt.setInt(1, 2);
                pstmt.setString(2, "Priya");
                pstmt.setDouble(3, 92.0);
                pstmt.executeUpdate();
                System.out.println("Records inserted.");
            }

            System.out.println("Reading Records:");
            String selectSQL = "SELECT * FROM students";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSQL)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Marks: " + rs.getDouble("marks"));
                }
            }

            String updateSQL = "UPDATE students SET marks = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                pstmt.setDouble(1, 95.0);
                pstmt.setInt(2, 1);
                pstmt.executeUpdate();
                System.out.println("Updated Rahul's marks to 95.0");
            }

            String deleteSQL = "DELETE FROM students WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, 2);
                pstmt.executeUpdate();
                System.out.println("Deleted record with ID 2.");
            }

            System.out.println("Final Records in DB:");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSQL)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Marks: " + rs.getDouble("marks"));
                }
            }

        } catch (SQLException e) {
            System.out.println("JDBC Error: " + e.getMessage());
        }
    }
}
