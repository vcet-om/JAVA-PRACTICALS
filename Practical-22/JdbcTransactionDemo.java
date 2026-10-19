import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTransactionDemo {
    private static final String URL = "jdbc:h2:mem:bankdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE accounts (acc_no INT PRIMARY KEY, holder VARCHAR(50), balance DOUBLE)");
                stmt.execute("INSERT INTO accounts VALUES (101, 'Alice', 1000.0)");
                stmt.execute("INSERT INTO accounts VALUES (102, 'Bob', 500.0)");

                stmt.execute("CREATE ALIAS GET_BALANCE FOR \"JdbcTransactionDemo.getBalanceProcedure\"");
            }

            System.out.println("Executing Transaction: Transferring 300 from Alice (101) to Bob (102)");
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement debit = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE acc_no = ?")) {
                    debit.setDouble(1, 300.0);
                    debit.setInt(2, 101);
                    debit.executeUpdate();
                }

                try (PreparedStatement credit = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE acc_no = ?")) {
                    credit.setDouble(1, 300.0);
                    credit.setInt(2, 102);
                    credit.executeUpdate();
                }

                conn.commit();
                System.out.println("Transaction committed successfully.");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaction rolled back due to error: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true);
            }

            try (CallableStatement cstmt = conn.prepareCall("{CALL GET_BALANCE(?)}")) {
                cstmt.setInt(1, 101);
                ResultSet rs = cstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Procedure Result - Alice New Balance: " + rs.getDouble("balance"));
                }
            }

            try (CallableStatement cstmt = conn.prepareCall("{CALL GET_BALANCE(?)}")) {
                cstmt.setInt(1, 102);
                ResultSet rs = cstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Procedure Result - Bob New Balance: " + rs.getDouble("balance"));
                }
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public static ResultSet getBalanceProcedure(Connection conn, int accNo) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM accounts WHERE acc_no = ?");
        stmt.setInt(1, accNo);
        return stmt.executeQuery();
    }
}
