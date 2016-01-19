/**
 * todo: the query for publishers/consumers could return XML
 *
 */
package arrowhead;

import arrowhead.generated.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import eventhandler.operations.*;
import javax.ws.rs.client.WebTarget;

//import com.rits.cloning.Cloner;
public class EventHandlerSystem {

    final static Logger logger = Logger.getLogger(EventHandlerSystem.class);
    private static final EventHandlerSystem instance = new EventHandlerSystem();
    
    public static EventHandlerSystem getInstance() {
        return instance;
    }
    EventHandlerOperations eventOp = new EventHandlerOperations();

    //	private static final Cloner cloner = new Cloner();
    public Registered m_registered;
    public Events events;

    private EventHandlerSystem() {
        m_registered = new Registered();
        events = new Events();
    }

    public void addEvent(EventType e) {
        this.events.getEvent().add(e);
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

    /* public boolean ImportEntity(String uid, Registered r) {
        
     Iterator<ProducerType> ip = r.getProducer().iterator();
     while (ip.hasNext()) {
     ProducerType p = ip.next();
     if (p.getUid().equals(uid)) {
     ProducerType newp = DeepCopyProducer(p);
     m_registered.getProducer().add(newp);
     System.out.println("added producer " + uid);
     return true;
     }
     }
     Iterator<ConsumerType> ic = r.getConsumer().iterator();
     while (ic.hasNext()) {
     ConsumerType c = ic.next();
     if (c.getUid().equals(uid)) {
     ConsumerType newp = DeepCopyConsumer(c);
     m_registered.getConsumer().add(newp);
     System.out.println("added consumer " + uid);
     return true;
     }
     }
     System.out.println("could not add " + uid);

     return false;
     }
     */
    
   /*Instead of a Database server, a file will be used to store data*/
    
   public Events GetHistoricalData(FilterType filter) {
        Events ret = new Events();
        

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
            target = eventOp.setTarget("http://localhost:8081", c.getUid());
            System.out.println(data.writeObject());
            r = eventOp.notifySubscriber(event, target);
        }
        storeEvent(event);
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
                System.out.println("CHECK PRODUCER: " + p.getUid() + "  " + p.getType());
                return true;
            }
        }
        return false;
    }
    
    public void storeEvent(EventType event){
        
        List<ConsumerType> subs = applyFilter(event);
        LogData data = new LogData();
        data.setEvent(event);
        for(ConsumerType c : subs){
            data.addConsumer(c.getUid());
        }
        logger.debug(data.writeObject());
        
    }
    

}
