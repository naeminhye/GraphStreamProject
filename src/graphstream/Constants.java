/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphstream;

/**
 *  Tạm thời!!
 *  Lớp Constants lưu lại các giá trị như Username, password
 */
public class Constants {
    
     private Constants() {
            // restrict instantiation
    }

    public static String DRIVER = "bolt";
    public static String HOST = "localhost";
    public static String PORT = "7687";
    public static final String USERNAME = "neo4j";//"reader";
    public static final String PASSWORD = "1234";
    public static final String CONNECTION_URL = "jdbc:neo4j:bolt://localhost:7687";
    public static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    
}
