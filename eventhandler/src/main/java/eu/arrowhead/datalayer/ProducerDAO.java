/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.datalayer;

import eu.arrowhead.model.Producer;

/**
 *
 * @author José Sousa
 */
public interface ProducerDAO {
 
    public void insertProducer(Producer c);
    
    public void removeProducer(String uid);
    
    public Producer getProducer(String uid);
    
}
