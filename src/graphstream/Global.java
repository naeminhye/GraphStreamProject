/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphstream;

/**
 *
 * @author Hieu Nguyen
 */
public class Global {
    
     private Global() {
            // restrict instantiation
    }
    public static String DRIVER = Constants.DRIVER;//"bolt";
    public static String HOST = Constants.HOST;//"localhost";
    public static String PORT = Constants.PORT;//"7687";
    public static String USERNAME = Constants.USERNAME;//"neo4j";
    public static String PASSWORD = Constants.PASSWORD;//"1234";
    public static String CONNECTION_URL = Constants.CONNECTION_URL;//"jdbc:neo4j:bolt://localhost:7687";
}
