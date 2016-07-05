/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventhandler.datalayer;

import eventhandler.model.Consumer;

/**
 *
 * @author José Sousa
 */
public interface ConsumerDAO {
    
    public void insertConsumer(Consumer c);
    
    public void removeConsumer(String uid);
    
    public Consumer getConsumer(String uid);
}
