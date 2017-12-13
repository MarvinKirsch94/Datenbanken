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

            //TODO Erfassung einer Kundenbestellung

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
            JDBC_Verwaltung jv = new JDBC_Verwaltung();

            switch (in = br.readLine()) {
                case "A":

                    jv.showAllArtikel(connection);
                    break;
                case "B":

                    jv.showAllLager(connection);
                    break;
                case "C":

                    jv.showAllKunde(connection);
                    break;
                case "D":

                    System.out.println("Geben sie eine Artikelnr ein zu der sie nähere Informationen wünschen: \n");

                    int artnr = Integer.parseInt(br.readLine());

                    jv.showArtDetailsAndLagerbestand(connection, artnr);
                    break;
                case "E":

                    jv.setNewLagerbestandForArtikel(connection);
                    break;
                case "F":

                    jv.updateMgeOfLagerbestand(connection);
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
