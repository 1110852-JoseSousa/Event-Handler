/*
 This class will later be used for the database operations
 */
package DL;

import arrowhead.generated.EventType;
import arrowhead.generated.FilterType;
import arrowhead.generated.Meta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

    private final String username;
    private final String passwd;
    private final String db_url;
    private final String db_driver;
    private Connection con;

    public DB() {

        DBProperties dbProp = new DBProperties();
        System.out.println("U : " + dbProp.getUsername());
        System.out.println("P: " + dbProp.getPassword());
        System.out.println("URL: " + dbProp.getDb_url());
        System.out.println("DRIVER: " + dbProp.getDb_driver());
        this.username = dbProp.getUsername();
        this.passwd = dbProp.getPassword();
        this.db_url = dbProp.getDb_url();
        this.db_driver = dbProp.getDb_driver();
    }

    public boolean openConnection() throws ClassNotFoundException, SQLException {

        Class.forName(this.db_driver);

        try {
            Connection connection = DriverManager.getConnection(this.db_url, this.username, this.passwd);
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            this.con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertEventDb(EventType event) {
        try {
            // the mysql insert statement
            String query = "insert into events (date, producerid, event_type, severity, payload)"
                    + " values (?, ?, ?, ?, ?)";

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = this.con.prepareStatement(query);
            preparedStmt.setDate(1, (java.sql.Date) date);
            preparedStmt.setString(1, event.getFrom());
            preparedStmt.setString(2, event.getType());
            preparedStmt.setInt(4, event.getDescription().getSeverity());
            preparedStmt.setString(5, event.getPayload());

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<EventType> getEventDB(FilterType f) {

        ArrayList<EventType> events = new ArrayList<>();
        EventType event;
        try {

            String query = "SELECT * FROM events";

            Statement st = this.con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                event = new EventType();
                Date date = rs.getDate("date");
                String producerID = rs.getString("producerid");
                String eventType = rs.getString("event_type");
                int severity = rs.getInt("metaid");
                String payload = rs.getString("payload");

                Meta m = new Meta();
                m.setSeverity(severity);

                event.setDescription(m);
                event.setFrom(producerID);
                event.setType(eventType);
                event.setPayload(payload);

                events.add(event);
            }

            return events;

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
