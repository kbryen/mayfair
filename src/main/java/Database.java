/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
    
    public HSSFWorkbook getHSSFWorkbook(String file)
    {
        if (file != null)
        {
            try (InputStream inp = new FileInputStream(file))
            {
                HSSFWorkbook workbook;
                Workbook wb = WorkbookFactory.create(inp);
                workbook = (HSSFWorkbook) wb;
                return workbook;
            }
            catch (IOException | InvalidFormatException ex)
            {
                return new HSSFWorkbook();
            }
        }
        else
        {
            return new HSSFWorkbook();
        }
    }
}
