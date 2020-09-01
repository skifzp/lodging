package il.elpisor.lodging.tests;

import org.testng.annotations.Test;

import java.sql.*;

public class DBConnectionTest {

    @Test
    public void testDBConnection() {
        // db parameters
        String url = "jdbc:mysql://localhost:3306/addressbook";
        String user = "root";
        String password = "";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from group_list");
            while (rs.next()){
                System.out.println(rs.getInt("group_id"));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
