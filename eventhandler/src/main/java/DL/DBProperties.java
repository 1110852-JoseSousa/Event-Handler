/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DL;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;

/**
 *
 * @author Cister
 */
public class DBProperties {

    private String db_url;
    private String username;
    private String password;
    private String db_driver;

    FileInputStream inputStream;

    public DBProperties() {

        File f = new File("db.properties");
        Properties prop = new Properties();

        try {
            inputStream = new FileInputStream(f);
            prop.load(inputStream);

        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

        setDb_url(prop.getProperty("dburl"));
        setUsername(prop.getProperty("username"));
        setPassword(prop.getProperty("password"));
        setDb_driver(prop.getProperty("driver"));

    }

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb_driver() {
        return db_driver;
    }

    public void setDb_driver(String db_driver) {
        this.db_driver = db_driver;
    }

}
