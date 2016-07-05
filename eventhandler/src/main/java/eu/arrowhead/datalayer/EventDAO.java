/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.datalayer;

import eu.arrowhead.model.Event;
import eu.arrowhead.model.Filter;
import java.util.List;

/**
 *
 * @author José Sousa
 */
public interface EventDAO {

    public void insertEvent(Event e);

    public List<Event> getEventsThroughFilter(Filter f);
}


