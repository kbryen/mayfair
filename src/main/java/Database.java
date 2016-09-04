/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mayfair;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author kian_bryen
 */
public class Database {
    private final String logFile = "S:/MayfairApplication/log/CurrentLog.txt";
    
    public Connection getConnection() 
    {
        Connection connection = null;
        try {
            String host = "jdbc:mysql://192.168.200.2:3306/mayfair";
            String username = "mayfair";
            String password = "mayfair";
            connection = DriverManager.getConnection(host, username, password);
        }
        catch (SQLException er) {
            System.out.println(er.getMessage());
        }
        return connection;
    }
    
    public void writeToLog(String message)
    {
        try
        {
            try (FileWriter fw = new FileWriter(logFile,true)) 
            {
                String timestamp = new Date().toString();
                fw.append("["+ timestamp + "] " + message + "\n");
            }
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
