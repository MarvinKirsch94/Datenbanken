package zettel2.group17.logistikDB_Verwaltung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class JDBC_Verwaltung {

    private ArrayList<Artikel> artikel = new ArrayList<>();
    private ArrayList<Kunde> kunden = new ArrayList<>();
    private ArrayList<Bestellung> bestellung = new ArrayList<>();
    private ArrayList<Bpos> bpos = new ArrayList<>();

    private SimpleDateFormat dateFormatWrite = new SimpleDateFormat("dd.MM.YYYY - HH.mm.ss");

    //LogistikDB-Verwaltung ------- Menu

    //A)
    public void showAllArtikel(Connection connection) {

        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM ARTIKEL");

            int artnr;
            String artbez;
            String mge;
            double preis;
            String kuehl;
            int anzbo;

            artikel.clear();

            while(rs.next()) {

                artnr = rs.getInt("ARTNR");
                artbez = rs.getString("ARTBEZ");
                mge = rs.getString("MGE");
                preis = rs.getDouble("PREIS");
                kuehl = rs.getString("KUEHL");
                anzbo = rs.getInt("ANZBO");

                Artikel a = new Artikel(artnr, artbez, mge, preis, kuehl, anzbo);

                artikel.add(a);
            }

            System.out.println("artnr\tartbez\t\tmge\t\tpreis\t\tkuehl\t\tanzbo");
            for(Artikel art : artikel) {

                System.out.format("%d\t\t%s\t%s\t\t%f\t\t%s\t\t%d\n",
                        art.getArtnr(),
                        (art.getArtbez().length() < 8 ? (art.getArtbez().length() < 4 ? art.getArtbez() + "\t\t" : art.getArtbez() + "\t") : art.getArtbez()),
                        art.getMge(),
                        art.getPreis(),
                        art.getKuehl(),
                        art.getAnzbo());
            }
            System.out.println("");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    //B)
    public void showAllBestellung(Connection connection) {

        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM BESTELLUNG");

            int bstnr;
            int knr;
            int status;
            double rsum;

            bestellung.clear();

            while(rs.next()) {

                bstnr = rs.getInt("BSTNR");
                knr = rs.getInt("KNR");
                status = rs.getInt("STATUS");
                rsum = rs.getDouble("RSUM");

                Bestellung b = new Bestellung(bstnr, knr, status, rsum);

                bestellung.add(b);
            }

            System.out.println("bstnr\tknr\tstatus\trsum");
            for (Bestellung b : bestellung) {

                System.out.format("%d\t\t%d\t\t%d\t\t%f\n",
                        b.getBstnr(),
                        b.getKnr(),
                        b.getStatus(),
                        b.getRsum());
            }
            System.out.println("");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    //C)
    public void showAllKunde(Connection connection) {

        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM KUNDE");

            int knr;
            String kname;
            int plz;
            String ort;
            String str;

            kunden.clear();

            while(rs.next()) {

                knr = rs.getInt("KNR");
                kname = rs.getString("KNAME");
                plz = rs.getInt("PLZ");
                ort = rs.getString("ORT");
                str = rs.getString("STRASSE");

                Kunde k = new Kunde(knr, kname, plz, ort, str);

                kunden.add(k);
            }

            System.out.println("knr\tkname\tplz\t\tort\t\tstr");
            for(Kunde kunde : kunden) {

                System.out.format("%d\t%s\t\t%d\t%s\t%s\n",
                        kunde.getKnr(),
                        kunde.getKname(),
                        kunde.getPlz(),
                        kunde.getOrt(),
                        kunde.getStrasse());
            }
            System.out.println("");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    //D)
    public void ubdateAllWERTandRSUM(Connection connection, int bstnr) {

        /*
        for bstnr soll update auf allen wert der bpos zeilen gemacht werden
        und in der bestellung dann die zugehörige gesamt summe eingetragen werden
        */
        try {

            Statement st1 = connection.createStatement();

            String query1 = "UPDATE " +
                    "(SELECT BPOS.WERT AS old, ARTIKEL.PREIS * BPOS.MGE AS arg FROM BPOS " +
                    "INNER JOIN ARTIKEL " +
                    "ON BPOS.ARTNR = ARTIKEL.ARTNR " +
                    "WHERE BPOS.BSTNR = " + bstnr + ")B " +
                    "SET B.old = B.arg";

            int n1 = st1.executeUpdate(query1);
            System.out.println(n1 + "\n" + bstnr);
            System.out.println("\n" + query1);

            Statement st2 = connection.createStatement();

            String query2 = "SELECT p.BSTNR, SUM(p.WERT) as total " +
                    "FROM BESTELLUNG b " +
                    "INNER JOIN BPOS p " +
                    "ON b.BSTNR = p.BSTNR " +
                    "WHERE b.BSTNR = " + bstnr + " " +
                    "GROUP BY p.BSTNR, b.RSUM";

            ResultSet n2 = st2.executeQuery(query2);
            n2.next();
            double rsum = n2.getDouble("total");

            Statement st3 = connection.createStatement();
            String query3 = "UPDATE BESTELLUNG b SET b.RSUM = " + rsum + ", b.STATUS = 1 WHERE b.BSTNR = " + bstnr;

            int n3 = st3.executeUpdate(query3);
            System.out.println(n3 + "\n" + bstnr);
            System.out.println("\n" + query3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //E)
    public void showAllBposforArtnr(Connection connection, int artnr) {
        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT a.ARTNR, a.ARTBEZ, a.MGE, a.PREIS, a.KUEHL, a.ANZBO, b.POSNR, b.BSTNR, b.MGE, b.WERT FROM (ARTIKEL a INNER JOIN BPOS b ON a.ARTNR = b.ARTNR) WHERE a.ARTNR = " + artnr);

            int nr = 0;
            String artbez = "";
            String mge = "";
            double preis = 0.0;
            String kuehl = "";
            int anzbo = 0;
            int posnr = 0;
            int bstnr = 0;
            int mgeb = 0;
            double wert = 0.0;

            System.out.println("artnr\tartbez\tmge\tpreis\tkuehl\tanzbo\tposnr\tbstnr\tmgeb\twert");
            while(rs.next()) {

                nr = rs.getInt("ARTNR");
                artbez = rs.getString("ARTBEZ");
                mge = rs.getString("MGE");
                preis = rs.getDouble("PREIS");
                kuehl = rs.getString("KUEHL");
                anzbo = rs.getInt("ANZBO");
                posnr = rs.getInt("POSNR");
                bstnr = rs.getInt("BSTNR");
                mgeb = rs.getInt(10);
                wert = rs.getDouble("WERT");

                System.out.format("%d\t%s\t%s\t%f\t%s\t%d\t%d\t%d\t%d\t%f\n", nr, artbez, mge, preis, kuehl, anzbo, posnr, bstnr, mgeb, wert);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    //F)
    public void showAllBposAndArtbezforBstnr(Connection connection, int bstnr) {

        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT BESTELLUNG.KNR,BESTELLUNG.STATUS,BESTELLUNG.RSUM,BPOS.*,ARTIKEL.ARTBEZ FROM (BESTELLUNG INNER JOIN BPOS ON BESTELLUNG.BSTNR = BPOS.BSTNR) INNER JOIN ARTIKEL ON BPOS.ARTNR = ARTIKEL.ARTNR WHERE BESTELLUNG.BSTNR = " + bstnr);

            String artbez = "";
            int posnr = 0;
            int artnr = 0;
            int knr = 1;
            int status = 0;
            double rsum = 0.0;
            int mge = 0;
            double wert = 0.0;

            System.out.println("bstnr\tartnr\tartbez\tmge\tposnr\tknr\twert\tstatus\tsum");
            while (rs.next()) {

                artnr = rs.getInt("ARTNR");
                artbez = rs.getString("ARTBEZ");
                mge = rs.getInt("MGE");
                posnr = rs.getInt("POSNR");
                knr = rs.getInt("KNR");
                wert = rs.getDouble("WERT");
                status = rs.getInt("STATUS");
                rsum = rs.getDouble("RSUM");

                System.out.format("%d\t%d\t%s\t%d\t%d\t%d\t%f\t%d\t%f\n", bstnr, artnr, artbez, mge, posnr, knr, wert, status, rsum);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    //Aufg 6)
    public boolean registerAnOrder(Connection connection, int knr, int[] artnrn, int[] mgen) {

        try {

            Bestellung b = new Bestellung(0, knr, 0, 0);
            Statement st1 = connection.createStatement();
            st1.executeUpdate("INSERT INTO BESTELLUNG (BSTNR, KNR, STATUS, RSUM, BESTDAT) VALUES (" + b.getBstnr()
                    + "," + b.getKnr() + "," + b.getStatus() + ",0," + "TO_DATE('" + b.getBdat() + "','dd.mm.yyyy hh24:mi:ss')" + ")");
            this.showAllBestellung(connection);
            int bstnr = bestellung.get(bestellung.size() - 1).getBstnr();

            int counter = artnrn.length;
            for (int i = 0; i < counter; i++) {

                //BPOS anlegen
                Statement st = connection.createStatement();
                st.executeUpdate("INSERT INTO BPOS (ARTNR, BSTNR, MGE) VALUES (" + artnrn[i] + "," + bstnr + "," + mgen[i] + ")");
            }
            this.ubdateAllWERTandRSUM(connection, bstnr);

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return true;
    }

    //setter and getter

    public ArrayList<Artikel> getArtikel() {
        return artikel;
    }

    public void setArtikel(ArrayList<Artikel> artikel) {
        this.artikel = artikel;
    }

    public ArrayList<Kunde> getKunden() {
        return kunden;
    }

    public void setKunden(ArrayList<Kunde> kunden) {
        this.kunden = kunden;
    }

    public ArrayList<Bestellung> getBestellung() {
        return bestellung;
    }

    public void setBestellung(ArrayList<Bestellung> bestellung) {
        this.bestellung = bestellung;
    }

    public ArrayList<Bpos> getBpos() {
        return bpos;
    }

    public void setBpos(ArrayList<Bpos> bpos) {
        this.bpos = bpos;
    }
}
