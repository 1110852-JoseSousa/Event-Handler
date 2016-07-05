/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventhandler.datalayer;

import eventhandler.model.Event;
import eventhandler.model.Filter;
import java.util.List;

/**
 *
 * @author José Sousa
 */
public interface EventDAO {

    public void insertEvent(Event e);

    public List<Event> getEventsThroughFilter(Filter f);
}


