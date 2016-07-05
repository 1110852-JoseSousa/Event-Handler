/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventhandler.arrowhead;

import eventhandler.datalayer.DBProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cister
 */
public class ArrowheadProperties {
    
    private String server;
    private String domain;
    private String hostname;
    private String tsig;
    

    FileInputStream inputStream;

    public ArrowheadProperties() {

        File f = new File("eventhandler.properties");
        Properties prop = new Properties();

        try {
            inputStream = new FileInputStream(f);
            prop.load(inputStream);

        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
}
