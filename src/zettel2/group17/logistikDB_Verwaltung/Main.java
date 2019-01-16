package zettel2.group17.logistikDB_Verwaltung;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        Connection connection = DB_Connection.establishConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            //Erfassung einer Kundenbestellung

            //Display Menu
            System.out.println("DB Operation Manager" + "\n<----------------------------->\n");
            System.out.println("(A)  Anzeige aller Artikel.");
            System.out.println("(B)  Anzeige aller Bestellung.");
            System.out.println("(C)  Anzeige aller Kunden.");
            System.out.println("(D)  Update der Werte in Bestellpositionen und Rsum in Bestellung anhand der Bstnr.");
            System.out.println("(E)  Anzeige von Stammdaten und Bestelldaten zu ARTNR.");
            System.out.println("(F)  Anzeige von Stammdaten und Artikelbezeichnung zu BSTNR.");
            System.out.println("(insert) Erweitert sofern moeglich die Datenbank mit den \nin ARTIKEL.csv vorhandenen Daten.\n");
            System.out.println("(ro) Erfassung einer Bestellung mit beliebig vielen Bpositionen.");

            String in;

            JDBC_Insert ji = new JDBC_Insert();
            JDBC_Verwaltung jv = new JDBC_Verwaltung();

            switch (in = br.readLine()) {
                case "A":

                    jv.showAllArtikel(connection);
                    break;
                case "B":

                    jv.showAllBestellung(connection);
                    break;
                case "C":

                    jv.showAllKunde(connection);
                    break;
                case "D":

                    System.out.println("Geben sie eine Bestellnummer ein die geupdatet werden soll: \n");

                    int bstnr = Integer.parseInt(br.readLine());

                    jv.ubdateAllWERTandRSUM(connection, bstnr);
                    break;
                case "E":

                    System.out.println("Geben sie eine ARTNR ein zu der sie die Stammdaten und alle Bestelldaten erfahren wollen: ");
                    int artnr = Integer.parseInt(br.readLine());

                    jv.showAllBposforArtnr(connection, artnr);
                    break;
                case "F":

                    System.out.println("Geben sie eine Bestellnummer ein zu der sie die Stammdaten und alle Bestellpositionen mit Artikelbezeichnung sehen möchten: ");
                    int bstnrf = Integer.parseInt(br.readLine());

                    jv.showAllBposAndArtbezforBstnr(connection, bstnrf);
                    break;
                case "insert":

                    ji.readFromCSV(JDBC_Insert.getFileLocation());

                    ji.writeToDB(connection);
                    break;
                case "ro":

                    boolean knr_found = false;
                    int y = 0;
                    do {
                        System.out.println("Geben sie eine gültige knr ein: ");
                        y = Integer.parseInt(br.readLine());

                        jv.showAllKunde(connection);
                        for (Kunde k : jv.getKunden()) {
                            if (k.getKnr() == y) knr_found = true;
                        }
                    } while (!knr_found);

                    ArrayList<Integer> artnrn = new ArrayList<>();
                    ArrayList<Integer> mgen = new ArrayList<>();

                    while (true) {
                        boolean artnr_found = false;
                        int artnrt = 0;
                        do {
                            System.out.println("Geben sie eine gueltige Artnr. ein um eine Bpos anzulegen: ");
                            artnrt = Integer.parseInt(br.readLine());
                            jv.showAllArtikel(connection);
                            for (Artikel a : jv.getArtikel()) {
                                if (a.getArtnr() == artnrt) artnr_found = true;
                            }
                        } while (!artnr_found);
                        System.out.println("Geben sie eine Menge ein : ");
                        int mget = Integer.parseInt(br.readLine());
                        artnrn.add(artnrt);
                        mgen.add(mget);
                        System.out.println("Möchten sie die Eingabe beenden [y/n]?");
                        String ant = br.readLine();
                        if (ant.equals("y")) break;
                    }

                    System.out.println(jv.registerAnOrder(connection, y, artnrn.stream().mapToInt(i -> i).toArray(), mgen.stream().mapToInt(i -> i).toArray()));
                    jv.showAllBestellung(connection);

                    break;
                default:
                    return;
            }
        }
    }
}
