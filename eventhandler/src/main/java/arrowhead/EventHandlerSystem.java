/**
 * todo: the query for publishers/consumers could return XML
 *
 */
package arrowhead;

import DL.DB;
import arrowhead.generated.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.logging.Level;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

//import com.rits.cloning.Cloner;
public class EventHandlerSystem {

    final static Logger logger = Logger.getLogger(EventHandlerSystem.class);
    private static final EventHandlerSystem instance = new EventHandlerSystem();
    private static final DB db = new DB();

    private static boolean isDatabaseConnected = false;

    public static EventHandlerSystem getInstance() {
        return instance;
    }

    public static DB getDataBase() {
        return db;
    }

    public static void openConnection() throws ClassNotFoundException, SQLException {
        isDatabaseConnected = db.openConnection();
    }

    public boolean isConnected() {
        return isDatabaseConnected;
    }

    //	private static final Cloner cloner = new Cloner();
    public Registered m_registered;
    public Events events;

    public void addEventMemory(EventType e) {
        this.events.getEvent().add(e);
    }

    public void storeEventDB(EventType e) {

        db.insertEventDb(e);

    }

    public void storeEventFile(EventType event) {

        List<ConsumerType> subs = applyFilter(event);
        LogData data = new LogData();
        data.setEvent(event);
        for (ConsumerType c : subs) {
            data.addConsumer(c.getUid());
        }
        logger.debug(data.writeObject());

    }

    private EventHandlerSystem() {
        m_registered = new Registered();
        events = new Events();
    }

    public Registered QueryProducer(String q_name, String q_type) {
        Registered r = new Registered();
        Iterator<ProducerType> ip = m_registered.getProducer().iterator();
        while (ip.hasNext()) {
            ProducerType p = ip.next();
            if ((q_name.equals("") || q_name.equals(p.getName()))
                    && (q_type.equals("") || q_type.equals(p.getType()))) {
                r.getProducer().add(p);
            }
        }
        return r;
    }

    public Registered QueryConsumer(String q_name, String q_type, String q_from) {
        Registered r = new Registered();
        Iterator<ConsumerType> ip = m_registered.getConsumer().iterator();
        while (ip.hasNext()) {
            ConsumerType c = ip.next();
            if ((q_name.equals("") || q_name.equals(c.getName()))
                    && (q_type.equals("") || q_type.equals(c.getFilter().getType()))
                    && (q_from.equals("") || q_from.equals(c.getFilter().getFrom()))) {
                r.getConsumer().add(c);
            }
        }
        return r;
    }

    public ProducerType GetProducer(String uid) {
        Iterator<ProducerType> ip = m_registered.getProducer().iterator();
        while (ip.hasNext()) {
            ProducerType p = ip.next();
            if (p.getUid().equals(uid)) {
                return p;
            }
        }
        return null;
    }

    public ConsumerType GetConsumer(String uid) {
        Iterator<ConsumerType> ip = m_registered.getConsumer().iterator();
        while (ip.hasNext()) {
            ConsumerType p = ip.next();
            if (p.getUid().equals(uid)) {
                return p;
            }
        }
        return null;
    }

    public boolean DeleteProducer(String uid) {
        ProducerType p1 = GetProducer(uid);
        if (null == p1) {
            return false;
        }
        return m_registered.getProducer().remove(p1);
    }

    public boolean DeleteConsumer(String uid) {
        ConsumerType p1 = GetConsumer(uid);
        if (null == p1) {
            return false;
        }
        return m_registered.getConsumer().remove(p1);
    }

    /*public boolean SubstituteProducer(String uid, Registered r) {
     if (DeleteProducer(uid)) {
     return ImportOne(uid, r);
     } else {
     return false;
     }
     }

     /* public boolean SubstituteConsumer(String uid, Registered r) {
     if (DeleteConsumer(uid)) {
     return ImportOne(uid, r);
     } else {
     return false;
     }
     }*/
    public Registered ResolveQueryProducer(String uid) {
        ProducerType p = GetProducer(uid);
        if (null == p) {
            return null;
        }

//		ProducerType newp = cloner.deepClone(p);
        ProducerType newp = DeepCopyProducer(p);

        Registered ret = new Registered();
        ret.getProducer().add(newp);
        return ret;
    }

    private ProducerType DeepCopyProducer(ProducerType p) {
        ProducerType newp = new ProducerType();
        newp.setUid(p.getUid());
        newp.setType(p.getType());
        newp.setName(p.getName());

        return newp;
    }

    private Meta DeepCopyMeta(Meta m) {
        Meta newm = new Meta();
        newm.setSeverity(m.getSeverity());
        return newm;
    }

    private FilterType DeepCopyFilter(FilterType f) {

        FilterType newf = new FilterType();
        newf.setDescription(DeepCopyMeta(f.getDescription()));
        newf.setEndDateTime(f.getEndDateTime());
        newf.setStartDateTime(f.getStartDateTime());
        newf.setType(f.getType());
        newf.setFrom(f.getFrom());
        return newf;
    }

    private ConsumerType DeepCopyConsumer(ConsumerType p) {
        ConsumerType newp = new ConsumerType();
        newp.setFilter(DeepCopyFilter(p.getFilter()));
        newp.setUid(p.getUid());
//		newp.setType(p.getType());
        newp.setName(p.getName());
        return newp;
    }

    public Registered ResolveQueryConsumer(String uid) {
        ConsumerType p = GetConsumer(uid);
        if (null == p) {
            return null;
        }

//		ConsumerType newp = cloner.deepClone(p);
        ConsumerType newp = DeepCopyConsumer(p);

        Registered ret = new Registered();
        ret.getConsumer().add(newp);
        return ret;
    }

    /*public boolean alreadyExists(String uid) {
     Iterator<ProducerType> ip = m_registered.getProducer().iterator();
     while (ip.hasNext()) {
     ProducerType p = ip.next();
     if (p.getUid().equals(uid)) {
     return true;
     }
     }
     Iterator<ConsumerType> ic = m_registered.getConsumer().iterator();
     while (ic.hasNext()) {
     ConsumerType c = ic.next();
     if (c.getUid().equals(uid)) {
     return true;
     }
     }
     return false;
     }*/
    public void ImportProducer(String uid, ProducerType p) {
        m_registered.getProducer().add(p);
        //logger.debug("");
    }

    public void ImportConsumer(String uid, ConsumerType c) {
        m_registered.getConsumer().add(c);
    }

    public Events GetHistoricalDataDB(FilterType filter) {
        return db.getEventDB(filter);
    }

    /*Instead of a Database server, a file will be used to store data*/
    public String GetHistoricalDataFile(FilterType filter) {

        LogData data = new LogData();
        EventType e = new EventType();
        BufferedReader br = null;
        EventType event;
        List<String> subs;
        String ret = "";

        try {

            String line;
            br = new BufferedReader(new FileReader("log4j-eh.log"));

            while ((line = br.readLine()) != null) {
                event = getEventInfoLog(line);
                subs = getSubscribersLog(line);

                if (filter.getFrom().compareTo(event.getFrom()) == 0 && filter.getType().compareTo(event.getType()) == 0) {
                    data.setEvent(event);
                    data.addListConsumers(subs);
                    ret += data.writeObject();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return ret;
    }

    public void flushEvents() {
        this.events.getEvent().clear();
    }

    // Access each Subscriber's notify Service to send the events
    public void notifyEvent(EventType event) {

        WebTarget target;
        Response r;
        LogData data = new LogData();
        data.setEvent(event);
        List<ConsumerType> subs;

        subs = applyFilter(event);

        for (ConsumerType c : subs) {

            target = setTarget(BneartIT.getSubscriberURL(c.getUid()));
            r = notifySubscriber(event, target);
        }
    }

    // For an event apply the filter of each subscriber and return a list
    public List<ConsumerType> applyFilter(EventType e) {

        List<ConsumerType> toNotify = new ArrayList<ConsumerType>();

        Iterator<ConsumerType> itSub = m_registered.getConsumer().iterator();

        while (itSub.hasNext()) {

            ConsumerType c = itSub.next();

            // Using the type and from as filter, also can be used the timestamp.. && c.getFilter().getFrom().compareTo(e.getFrom()) == 0
            if (c.getFilter().getType().compareTo(e.getType()) == 0) {
                toNotify.add(c);
            }
        }

        return toNotify;
    }

    // Checks if there is any producer publishing events according to the subscriber filter
    boolean ExistsEventsForConsumer(ConsumerType c) {
        for (ProducerType p : m_registered.getProducer()) {
            if (c.getFilter().getFrom().equalsIgnoreCase(p.getUid()) && c.getFilter().getType().equalsIgnoreCase(p.getType())) {
                return true;
            }
        }
        return false;
    }

    /*
     Line format example "date time severityString SystemName from type severityInt/Sub1;Sub2;Sub3"
     Example: 
            
     2016-01-20 11:16:23 DEBUG EventHandlerSystem:348 porto-sensor-1  temperature 1 10ºC Subscriber1;Subscriber2;Subcriber3
            
     So 
     array[0]->date
     array[1]->time
     array[2]->severityString
     array[3]->SystemName
     array[4]->from
     array[5]->type
     array[6]->severityInt
     array[7]->payload
     array[8]->subscriberList
     */
    public List<String> getSubscribersLog(String line) {

        String[] array = line.split(" ");
        List<String> subs = Arrays.asList(array[8].split(";"));
        return subs;

    }

    public EventType getEventInfoLog(String line) {

        EventType e = new EventType();

        String type, from, payload;
        String[] array;
        array = line.split(" ");
        type = array[5];
        from = array[4];
        payload = array[7];
        Integer severity = Integer.parseInt(array[6]);
        e.setType(type);
        e.setFrom(from);
        Meta m = new Meta();
        m.setSeverity(severity);
        e.setDescription(m);
        e.setPayload(payload);

        return e;
    }

    public WebTarget setTarget(String URI) {
        Client c = ClientBuilder.newClient();
        WebTarget target;
        target = c.target(URI);
        return target;
    }

    public Response notifySubscriber(EventType e, WebTarget target) {

        return target.path("notify").request(MediaType.APPLICATION_XML).post(
                Entity.entity(e, MediaType.APPLICATION_XML));

    }

}
