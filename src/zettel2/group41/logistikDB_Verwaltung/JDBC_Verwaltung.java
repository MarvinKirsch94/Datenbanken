package zettel2.group41.logistikDB_Verwaltung;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class JDBC_Verwaltung {

    private ArrayList<Artikel> artikel = new ArrayList<>();
    private ArrayList<Kunde> kunden = new ArrayList<>();
    private ArrayList<Lager> lager = new ArrayList<>();
    private ArrayList<Lagerbestand> lagerbestaende = new ArrayList<>();

    private SimpleDateFormat dateFormatWrite = new SimpleDateFormat("dd.MM.YYYY - HH.mm.ss");

    //LogistikDB-Verwaltung ------- Menu

    //A)
    public void showAllArtikel(Connection connection) {

        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM ARTIKEL");

            int artnr;
            String artbez;
            int mge;
            double preis;
            double steu;
            Date edat;

            artikel.clear();

            while(rs.next()) {

                artnr = rs.getInt("ARTNR");
                artbez = rs.getString("ARTBEZ");
                mge = rs.getInt("MGE");
                preis = rs.getDouble("PREIS");
                steu = rs.getDouble("STEU");
                edat = rs.getDate("EDAT");

                Artikel a = new Artikel(artnr, artbez, mge, preis, steu, edat);

                artikel.add(a);
            }

            System.out.println("artnr\tartbez\t\tmge\t\tpreis\t\tsteu\t\tedat");
            for(Artikel art : artikel) {

                System.out.format("%d\t\t%s\t%d\t\t%.2f\t\t%.2f\t\t%s\n",
                        art.getArtnr(),
                        (art.getArtbez().length() < 8 ? (art.getArtbez().length() < 4 ? art.getArtbez() + "\t\t" : art.getArtbez() + "\t") : art.getArtbez()),
                        art.getMge(),
                        art.getPreis(),
                        art.getSteu(),
                        dateFormatWrite.format(art.getEdat()));
            }
            System.out.println("");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    //B)
    public void showAllLager(Connection connection) {

        try {

            Statement st = connection.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM LAGER");

            int lnr;
            String lort;
            int lplz;

            lager.clear();

            while(rs.next()) {

                lnr = rs.getInt("LNR");
                lort = rs.getString("LORT");
                lplz = rs.getInt("LPLZ");

                Lager l = new Lager(lnr, lort, lplz);

                lager.add(l);
            }

            System.out.println("lnr\tlort\t\tlplz");
            for(Lager lr : lager) {

                System.out.format("%d\t%s\t%d\n",
                        lr.getLnr(),
                        lr.getLort(),
                        lr.getLplz());
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
    public long showArtDetailsAndLagerbestand(Connection connection, int artnr) {

        /*
        STATEMENT
        SELECT ARTIKEL.*, LAGERBESTAND.*, LAGER.LPLZ, LAGER.LORT FROM ((ARTIKEL
        INNER JOIN LAGERBESTAND ON ARTIKEL.ARTNR = LAGERBESTAND.ARTNR)
        INNER JOIN LAGER ON LAGERBESTAND.LNR = LAGER.LNR) WHERE ARTIKEL.ARTNR = ?;
        */

        long s1 = 0;

        try {

            Statement st = connection.createStatement();

            String executionstr = "SELECT ARTIKEL.*, LAGERBESTAND.*, LAGER.LPLZ, LAGER.LORT " +
                    "FROM ((ARTIKEL " +
                    "INNER JOIN LAGERBESTAND ON ARTIKEL.ARTNR = LAGERBESTAND.ARTNR) " +
                    "INNER JOIN LAGER ON LAGERBESTAND.LNR = LAGER.LNR) WHERE ARTIKEL.ARTNR = " + artnr;

            ResultSet rs = st.executeQuery(executionstr);

            String artbez;
            int mge;
            double preis;
            double steu;
            String edat;
            int bstnr;
            int lnr;
            int lplz;
            String lort;

            System.out.println("artnr\tartbez\tmge\tpreis\tsteu\tedat\t\t\t\t\tbstnr\tmenge\tlnr\tlplz\tlort");

            while(rs.next()) {

                //artnr
                artbez = rs.getString("ARTBEZ");
                mge = rs.getInt("MGE");
                preis = rs.getDouble("PREIS");
                steu = rs.getDouble("STEU");
                edat = dateFormatWrite.format(rs.getDate("EDAT"));
                bstnr = rs.getInt("BSTNR");
                long menge = rs.getLong("MENGE");
                lnr = rs.getInt("LNR");
                lplz = rs.getInt("LPLZ");
                lort = rs.getString("LORT");

                System.out.format("%d\t\t%s\t%d\t%.2f\t%.2f\t%s\t%d\t\t%d\t%d\t%d\t%s\n", artnr, artbez, mge, preis, steu, edat, bstnr, menge, lnr, lplz, lort);

                s1 += menge;
            }

            System.out.println("");

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return s1;
    }

    //E)
    public void setNewLagerbestandForArtikel(Connection connection, int bstnr, int artnr, int lnr, int menge) {
        try {

            Statement st = connection.createStatement();

            String query = "INSERT INTO LAGERBESTAND (BSTNR, ARTNR, LNR, MENGE) VALUES ('" + bstnr + "', '" + artnr + "', '" + lnr + "', '" + menge + "')";

            st.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //F)
    public void updateMgeOfLagerbestand(Connection connection, int bstnr, int menge) {

        try {

            Statement st = connection.createStatement();

            String query = "UPDATE LAGERBESTAND SET MENGE = " + menge + "WHERE BSTNR = " + bstnr;

            int n = st.executeUpdate(query);
            System.out.println(n + "\n" + bstnr + "\n" + menge);
            System.out.println("\n" + query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Lager> getLager() {
        return lager;
    }

    public void setLager(ArrayList<Lager> lager) {
        this.lager = lager;
    }

    public ArrayList<Lagerbestand> getLagerbestaende() {
        return lagerbestaende;
    }

    public void setLagerbestaende(ArrayList<Lagerbestand> lagerbestaende) {
        this.lagerbestaende = lagerbestaende;
    }
}
