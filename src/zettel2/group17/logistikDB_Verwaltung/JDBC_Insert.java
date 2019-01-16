package zettel2.group17.logistikDB_Verwaltung;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class JDBC_Insert {

    private static final String fileLocation = "ARTIKEL.csv";

    public static String getFileLocation() {
        return fileLocation;
    }

    private int counter;

    private ArrayList<Artikel> artikel = new ArrayList<>();

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    //private SimpleDateFormat dateFormatRead = new SimpleDateFormat("DD.MM.YY");
    //private SimpleDateFormat dateFormatWrite = new SimpleDateFormat("YYYY-MM-DD");

    public boolean readFromCSV(String fileLocation) {

        this.counter = 0;

        BufferedReader br = null;
        boolean localError = false;

        try {

            br = new BufferedReader(new FileReader(fileLocation));

        } catch (FileNotFoundException e) {

            e.printStackTrace();
            localError = true;
        }

        try {

            br.readLine();
            String buffer = "";
            while((buffer = br.readLine()) != null) {

                localError = false;

                String s[] = buffer.split(";");
                int artnr = 0;
                String artbez = "";
                String mge = "";
                double preis = 0;
                String kuehl = "";
                int anzbo = 0;

                if (s[0].equals("")) {
                    artnr = 0;
                } else {
                    artnr = Integer.parseInt(s[0]);
                }
                try {
                    artbez = s[1];
                    mge = s[3];
                    preis = Double.parseDouble(s[2]);
                    kuehl = s[4];
                    anzbo = Integer.parseInt(s[5]);
                } catch (Exception e) {
                    e.printStackTrace();
                    localError = true;
                }
                //read complete

                if(!localError) {
                    Artikel a = new Artikel(artnr, artbez, mge, preis, kuehl, anzbo);
                    artikel.add(a);
                    this.counter++;
                }
            } // end of while

        } catch (Exception e) {

            e.printStackTrace();
            localError = true;
        }

        return !localError;
    }

    public boolean writeToDB(Connection connection) throws SQLException {

        try {
            //String SQL_INSERT = "INSERT INTO ARTIKEL (ARTNR, ARTBEZ, MGE, PREIS, STEU, EDAT) VALUES (?, ?, ?, ?, ?, ?)";
            //PreparedStatement st = connection.prepareStatement(SQL_INSERT);
            Statement st = connection.createStatement();

            for(Artikel a : artikel) {

                String s1 = String.valueOf(a.getArtnr());
                String s2 = "'" + a.getArtbez() + "'";
                String s3 = "'" + a.getMge() + "'";
                String s4 = "'" + (a.getPreis() + "").replace(".", ",") + "'";
                String s5 = "'" + a.getKuehl() + "'";
                String s6 = "'" + a.getAnzbo() + "'";

                String query = "INSERT INTO ARTIKEL (ARTNR, ARTBEZ, MGE, PREIS, KUEHL, ANZBO) VALUES (" + s1 + ", " + s2 + ", " + s3 + ", " + s4 + ", " + s5 + ", " + s6 + ")";
                System.out.println(query);

                st.addBatch(query);
            }

            int all[] = st.executeBatch();
            int n = 0;
            for(int i : all) {
                n += i;
            }

            System.out.println("-> " + n + " Eintraege wurden erfolgreich angelegt.");

            System.out.println("Von " + this.getCounter() + " eingelesenen Artikeln wurden " + n + "erfolgreich in die Datenbank geschrieben.");

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }
}
