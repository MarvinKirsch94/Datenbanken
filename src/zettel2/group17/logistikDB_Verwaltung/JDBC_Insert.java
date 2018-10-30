package zettel2.group17.logistikDB_Verwaltung;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private SimpleDateFormat dateFormatRead = new SimpleDateFormat("DD.MM.YY");
    private SimpleDateFormat dateFormatWrite = new SimpleDateFormat("YYYY-MM-DD");

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
                int mge = 0;
                double preis = 0;
                double steu = 0;
                Date edat = null;

                if(s[0] != null) {
                    artnr = Integer.parseInt(s[0]);
                } else {
                    artnr = 0;
                }

                artbez = s[1];
                mge = (int)Double.parseDouble(s[2]);
                preis = Double.parseDouble(s[3]);

                try {

                    steu = Double.parseDouble(s[4]);
                    if (steu != 0.07 && steu != 0.19) {

                        throw new ArithmeticException("steu can only be 0.07 or 0.19 \n Data Error!");
                    }

                } catch(ArithmeticException ae) {

                    ae.printStackTrace();
                    localError = true;
                }

                try {
                    edat = dateFormatRead.parse(s[5]);

                } catch (ParseException e) {

                    e.printStackTrace();
                    localError = true;
                }

                //read complete

                if(!localError) {
                    Artikel a = new Artikel(artnr, artbez, mge, preis, steu, edat);
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
                String s3 = "'" + String.valueOf(a.getMge()) + "'";
                String s4 = "'" + (a.getPreis() + "").replace(".", ",") + "'";
                String s5 = "'" + (a.getSteu() + "").replace(".", ",") + "'";
                String s6 = "TO_DATE('" + dateFormatWrite.format(a.getEdat()) + "','YYYY-MM-DD')";
                /*
                st.setString(1, s1);
                st.setString(2, s2);
                st.setString(3, s3);
                st.setString(4, s4);
                st.setString(5, s5);
                st.setString(6, s6);
                st1.executeUpdate("INSERT INTO ARTIKEL (ARTNR, ARTBEZ, MGE, PREIS, STEU, EDAT) VALUES (" + s1 + ", " + s2 + ", " + s3 + ", " + s4 + ", " + s5 + ", " + s6 + ")");
                */
                String query = "INSERT INTO ARTIKEL (ARTNR, ARTBEZ, MGE, PREIS, STEU, EDAT) VALUES (" + s1 + ", " + s2 + ", " + s3 + ", " + s4 + ", " + s5 + ", " + s6 + ")";
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
