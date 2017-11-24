package zettel2.group41.logistikDB_Verwaltung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        Connection connection = DB_Connection.establishConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            //Display Menu
            System.out.println("DB Operation Manager" + "\n<----------------------------->\n");
            System.out.println("(A)  Anzeige aller Artikel.");
            System.out.println("(B)  Anzeige aller Lager.");
            System.out.println("(C)  Anzeige aller Kunden.");
            System.out.println("(D)  Stammdaten zu Artnr. sowie Laberbestand und Summe dieser.");
            System.out.println("(E)  Erfassung eines neuen Lagerbestands eines Artikels.");
            System.out.println("(F)  Update der Menge eines ausgewählten Lagerbestands.");
            System.out.println("(insert) Erweitert sofern moeglich die Datenbank mit den \nin ARTIKEL.csv vorhandenen Daten.");

            String in;

            JDBC_Insert ji = new JDBC_Insert();

            switch (in = br.readLine()) {
                case "A":
                    break;
                case "B":
                    break;
                case "C":
                    break;
                case "D":
                    break;
                case "E":
                    break;
                case "F":
                    break;
                case "insert":

                    ji.readFromCSV(JDBC_Insert.getFileLocation());

                    ji.writeToDB(connection);
                    break;
                default:
                    return;
            }
        }
    }
}
