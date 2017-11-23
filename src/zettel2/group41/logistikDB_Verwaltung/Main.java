package zettel2.group41.logistikDB_Verwaltung;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Main {

    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String protocol = "jdbc:oracle:thin:@"; //Oracle protocol String

    public static void main(String[] args) {

        //driver
        System.out.println("Hello");
        try {

            Class.forName(driver);

        } catch(Exception ex1) {

            System.err.println("Where is your Oracle JDBC Driver?");
            ex1.printStackTrace();
        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        //Connection details
        String host = "schelling.nt.fh-koeln.de" + ":";
        String port = "1521" + ":";
        String sid = "xe";
        String user = "dbprak41";
        String pwd = "kORO1";
        String url = protocol + host + port + sid;

        try {

            connection = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }

        //Connection test ...
        if (connection != null) {

            System.out.println("You made it, take control over your database now!");
        } else {

            System.out.println("Failed to make connection!");
        }
    }
}
