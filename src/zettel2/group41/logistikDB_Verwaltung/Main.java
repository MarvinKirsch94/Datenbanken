package zettel2.group41.logistikDB_Verwaltung;

import zettel2.group41.erfassung_einer_Kundenbestellung.Data;
import zettel2.group41.erfassung_einer_Kundenbestellung.JDBC_Bestellung;

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
            System.out.println("(insert) Erweitert sofern moeglich die Datenbank mit den \nin ARTIKEL.csv vorhandenen Daten.\n");
            System.out.println("(kb) Erfassung einer Kundenbestellung.");

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

                    System.out.println("Gesamt Bestand des Artikels beträgt sich auf: " + jv.showArtDetailsAndLagerbestand(connection, artnr));
                    break;
                case "E":

                    System.out.println("Geben sie nun die Daten des neuen Lagerbestandes ein.");
                    System.out.println("Please insert bstnr: ");
                    int b = Integer.parseInt(br.readLine());
                    System.out.println("Please insert artnr: ");
                    int a = Integer.parseInt(br.readLine());
                    System.out.println("Please insert lnr: ");
                    int l = Integer.parseInt(br.readLine());
                    System.out.println("Please insert menge: ");
                    int m = Integer.parseInt(br.readLine());

                    jv.setNewLagerbestandForArtikel(connection, b, a, l, m);
                    break;
                case "F":

                    System.out.println("Geben sie nun die Daten ein.");
                    System.out.println("Geben sie die bst ein: ");
                    int bstnr = Integer.parseInt(br.readLine());
                    System.out.println("Geben sie die neue Menge ein: ");
                    int menge = Integer.parseInt(br.readLine());

                    jv.updateMgeOfLagerbestand(connection, bstnr, menge);
                    break;
                case "insert":

                    ji.readFromCSV(JDBC_Insert.getFileLocation());

                    ji.writeToDB(connection);
                    break;
                case "kb":

                    //TODO aufgabe 6
                    //a)
                    System.out.println("Geben sie die Daten für die Bestellung ein.");
                    System.out.println("Geben sie die artnr ein: ");
                    int x = Integer.parseInt(br.readLine());
                    System.out.println("Geben sie die knr ein: ");
                    int y = Integer.parseInt(br.readLine());
                    System.out.println("Geben sie die bmenge ein: ");
                    int z = Integer.parseInt(br.readLine());
                    //b)
                    long s1 = jv.showArtDetailsAndLagerbestand(connection, x);

                    if(z <= s1) {

                        //menge von lagerbestanden abziehen und neue zeile Kubest
                        //bdat jetzt ldat = bdat + 14 tage rbet = bmenge mal preis
                        //datei auftragsbestätigung kundenanschrift ldat bmenge rbet
                        //dateiname: ab+knr+b+bnr

                        JDBC_Bestellung.buchen(connection, x, y, z);
                    } else {

                        //dx = bmenge - s1
                        //"nicht genügend Lagerbestand des Artikels vorhanden"
                        int dx = z - (int)s1;
                        System.out.println("Es sind " + dx + " zu wenig Artikel vorhanden\n!nicht genügend Lagerbestand des Artikels vorhanden!\n");
                    }


                    //eingabe
                    //bmenge artnr knr
                    break;
                default:
                    return;
            }
        }
    }
}
