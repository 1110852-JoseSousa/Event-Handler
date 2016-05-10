/*
 This class will later be used for the database operations
 */
package DL;

import arrowhead.generated.EventType;
import arrowhead.generated.Events;
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
import java.sql.Timestamp;
import java.text.ParseException;

public class DB {

    private final String username;
    private final String passwd;
    private final String db_url;
    private final String db_driver;
    private Connection con;

    public DB() {

        DBProperties dbProp = new DBProperties();
        this.username = dbProp.getUsername();
        this.passwd = dbProp.getPassword();
        this.db_url = dbProp.getDb_url();
        this.db_driver = dbProp.getDb_driver();
        System.out.println(username);
        System.out.println(passwd);
        System.out.println(db_url);
        System.out.println(db_driver);
        
    }

    public boolean openConnection() throws ClassNotFoundException, SQLException {

        Class.forName(this.db_driver);

        try {
            this.con = DriverManager.getConnection(this.db_url, this.username, this.passwd);
            System.out.println("Connected to db " + this.db_url);
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            this.con.close();
            System.out.println("Closed connection to db " + this.db_url);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertEventDb(EventType event) {
        try {
            // the mysql insert statement
            String query = "insert into events (date, producer_id, event_type, meta_id, payload)"
                    + " values (?, ?, ?, ?, ?)";
            
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            
            Timestamp timestamp = new Timestamp(date.getTime());
            
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = this.con.prepareStatement(query);

            preparedStmt.setTimestamp(1, timestamp);
            preparedStmt.setString(2, event.getFrom());
            preparedStmt.setString(3, event.getType());
            preparedStmt.setInt(4, event.getDescription().getSeverity());
            preparedStmt.setString(5, event.getPayload());

            System.out.println("SQL Statement " + preparedStmt.toString());
            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Events getEventDB(FilterType f) {

        Events events = new Events();
        
        EventType event;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            Timestamp timestamp = new Timestamp(date.getTime());

            /*String query = "SELECT * FROM events WHERE event_type='" + f.getType() + "' "
                    + "AND meta_id='" + f.getDescription().getSeverity() + "' "
                    + "AND producer_id='" + f.getFrom() + "' "
                    + "AND date BETWEEN " + f.getStartDateTime() + " AND " + f.getEndDateTime() + ";";
*/
            String query = "SELECT * FROM events;";
            System.out.println("HISTOCAL QUERY = " + query);
            
            Statement st = this.con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                event = new EventType();
                
                String producerID = rs.getString("producer_id");
                String eventType = rs.getString("event_type");
                int severity = rs.getInt("meta_id");
                String payload = rs.getString("payload");

                Meta m = new Meta();
                m.setSeverity(severity);

                event.setDescription(m);
                event.setFrom(producerID);
                event.setType(eventType);
                event.setPayload(payload);

                events.getEvent().add(event);
            }

            return events;

        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
