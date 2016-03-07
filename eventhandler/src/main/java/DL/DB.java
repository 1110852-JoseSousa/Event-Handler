/*
 This class will later be used for the database operations
 */
package DL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private String userid;
    private String passwd;
    private String dbid;
    private Connection con;

    public DB(String userID, String passwd, String dbID) {
        this.userid = userID;
        this.passwd = passwd;
        this.dbid = dbID;
    }

    public DB() {
    }

    public boolean connectionBD() throws ClassNotFoundException, SQLException {

        String dbUrl = "jdbc:mysql://localhost:3306/eventhandler";
        String dbClass = "com.mysql.jdbc.Driver";
        Class.forName("com.mysql.jdbc.Driver");
        String username = "root";
        String password = "cister";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public void testConnect() throws ClassNotFoundException {

        String dbUrl = "jdbc:mysql://localhost:3306/eventhandler";
        String dbClass = "com.mysql.jdbc.Driver";
        Class.forName("com.mysql.jdbc.Driver");
        String username = "root";
        String password = "cister";

        try {
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {

            e.printStackTrace();

        }

    }
}
