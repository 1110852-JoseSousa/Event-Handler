/*
 This class will later be used for the database operations
 */
package eventhandler.datalayer;

import eventhandler.model.Consumer;
import eventhandler.model.Event;
import eventhandler.model.Events;
import eventhandler.model.Filter;
import eventhandler.model.Metadata;
import eventhandler.model.Producer;
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
import java.util.List;

public class Database  implements EventDAO, ProducerDAO, ConsumerDAO{

    private final String username;
    private final String passwd;
    private final String db_url;
    private final String db_driver;
    private Connection con;

    /*private static DatabaseSingleton instance = null;
    
    private LazyInitializedSingleton(){}
    
    
    public static LazyInitializedSingleton getInstance(){
        if(instance == null){
            instance = new LazyInitializedSingleton();
        }
        return instance;
    }
    */
    
    public Database() {

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
        } catch (Exception e) {
            System.out.println("Could not connect to database!");
            return false;
        }
    }

    public void closeConnection() {
        try {
            this.con.close();
            System.out.println("Closed connection to db " + this.db_url);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertEventDb(Event event) {
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

            // execute the preparedstatement
            preparedStmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Events getEventDB(Filter f) {

        Events events = new Events();

        Event event;
        try {

            Timestamp timestampEnd = new Timestamp(f.getEndDateTime().getMillisecond());

            /*
            String query = "SELECT * FROM events WHERE event_type='" + f.getType() + "' "
                    + "AND meta_id='" + f.getDescription().getSeverity() + "' "
                    + "AND producer_id='" + f.getFrom() + "' "
                    + "AND date BETWEEN " + timestampBegin + " AND " + timestampEnd;
             */
            String query = "SELECT * FROM events;";

            Statement st = this.con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                event = new Event();

                String producerID = rs.getString("producer_id");
                String eventType = rs.getString("event_type");
                int severity = rs.getInt("meta_id");
                String payload = rs.getString("payload");

                Metadata m = new Metadata();
                m.setSeverity(severity);

                event.setDescription(m);
                event.setFrom(producerID);
                event.setType(eventType);
                event.setPayload(payload);

                events.getEvent().add(event);
            }

            return events;

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public void insertEvent(Event e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Event> getEventsThroughFilter(Filter f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertProducer(Producer c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeProducer(String uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producer getProducer(String uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertConsumer(Consumer c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeConsumer(String uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Consumer getConsumer(String uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
