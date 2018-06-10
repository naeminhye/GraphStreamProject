/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

/**
 *
 * @author Hieu Nguyen
 */
public class GlobalVariables {
    
    private String driver = "BOLT";
    private String port = "7687";
    private String username = "reader";
    private String password = "1234";
    
    public String getDriver() {
        return driver;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setDriver(String driverType) {
        this.driver = driverType;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
