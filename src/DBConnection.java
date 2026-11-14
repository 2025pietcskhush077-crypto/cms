import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/csm",
                "root",
                "Manu@5986" // Yahan apna actual MySQL root password daalna
        );
        return con;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Connection successful!");
        getConnection();
    }
}
