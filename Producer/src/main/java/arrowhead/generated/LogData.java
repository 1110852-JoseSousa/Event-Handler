package arrowhead.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for LogData complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="LogData">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="event" type="{http://www.arrowhead.org/eventhandler/events}EventType" maxOccurs="1" minOccurs="0"/>
 *         &lt;element name="consumer" type="Integer" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogData", propOrder = {
    "event",
    "consumerList"
})

@XmlRootElement(name = "LogData")
public class LogData {

    protected EventType event;
    protected List<String> consumerList;

    public LogData() {
        this.event = new EventType();
        this.consumerList = new ArrayList<>();
    }

    /**
     * Gets the event.
     *
     * @return possible object is {@link EventType }
     *
     */
    public EventType getEvent() {
        return event;
    }

    /**
     * Sets the event properties.
     *
     *
     * @param from allowed object is {@link String }
     * @param type allowed object is {@link String }
     * @param description allowed object is {@link Meta }
     * @param payload allowed object is {@link String }
     */
    public void setEvent(String from, String type, Meta description, String payload) {
        this.event.setFrom(from);
        this.event.setType(type);
        this.event.setDescription(description);
        this.event.setPayload(payload);
    }

    /**
     * Sets the event properties.
     *
     *
     * @param event allowed object is {@link EventType }
     */
    public void setEvent(EventType event) {
        this.event = event;
    }

    /**
     * Sets the list of consumers that were notified.
     *
     * @param listC allowed object is {@link List<ConsumerType> }
     *
     */
    public void setConsumers(List<String> listC) {
        this.consumerList.clear();
        for (String uid : listC) {
            this.consumerList.add(uid);
        }
    }

    /**
     * Gets the list of notified subscribers.
     *
     * @return possible object is {@link List<Integer> }
     *
     */
    public List<String> getListConsumers() {
        return this.consumerList;
    }

    public void addConsumer(String uid) {
        this.consumerList.add(uid);
    }

    public void addListConsumers(List<String> l) {
        for (String c : l) {
            this.consumerList.add(c);
        }
    }

    /*How data will be written to a file*/
    public String writeObject() {
        String eventInfo = this.event.getFrom() + " " + this.event.getType() + " "
                + this.event.getDescription().getSeverity() + " " + event.getPayload() + " ";
        String consumerList = "";
        for (String uid : this.consumerList) {
            consumerList += uid + ";";
        }
        return eventInfo + consumerList + "\n";
    }
}
