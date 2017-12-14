package zettel2.group41.erfassung_einer_Kundenbestellung;

import zettel2.group41.logistikDB_Verwaltung.JDBC_Verwaltung;
import zettel2.group41.logistikDB_Verwaltung.Lagerbestand;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marvin Kirsch on 24.11.2017.
 * Matrikelnr.: 11118687
 */
public class JDBC_Bestellung {

    private int knr;
    private int artnr;
    private int bmenge;
    private String bdat;
    private String ldat;
    private double rbet;
    //private int s1;

    private static SimpleDateFormat dateFormatWrite = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

    public JDBC_Bestellung(int knr, int artnr, int bmenge, double rbet) {
        this.knr = knr;
        this.artnr = artnr;
        this.bmenge = bmenge;
        this.rbet = rbet;
        LocalDateTime ldtn = LocalDateTime.now();
        LocalDateTime ldtl = LocalDateTime.now().plusDays(14);
        Date dn = Date.from(ldtn.atZone(ZoneId.systemDefault()).toInstant());
        Date dl = Date.from(ldtl.atZone(ZoneId.systemDefault()).toInstant());
        this.bdat = dateFormatWrite.format(dn);
        this.ldat = dateFormatWrite.format(dl);
    }

    public static void buchen(Connection connection, int artnr, int knr, int bmenge) {

        //SELECT * FROM LAGERBESTAND WHERE ARTNR = x

        try {

            ArrayList<Lagerbestand> lb = new ArrayList<>();
            int bstnr = 0;
            int menge = 0;
            int lnr = 0;

            Statement st = connection.createStatement();

            String query = "SELECT BSTNR, MENGE, LNR FROM LAGERBESTAND WHERE ARTNR = " + artnr;

            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {

                bstnr = rs.getInt("BSTNR");
                menge = rs.getInt("MENGE");
                lnr = rs.getInt("LNR");

                Lagerbestand l = new Lagerbestand(bstnr, artnr, lnr, menge);

                lb.add(l);
            }

            int abmenge = bmenge;

            for(int i = 0; i < lb.size(); i++) {

                if(abmenge == 0) {
                    break;
                }

                int temp = lb.get(i).getMenge();
                if(abmenge > temp) {

                    abmenge -= lb.get(i).getMenge();
                    lb.get(i).setMenge(0);
                } else if(abmenge <= temp) {

                    //minus and update
                    lb.get(i).setMenge(lb.get(i).getMenge() - abmenge);
                    abmenge = 0;
                }
                System.out.println(lb.get(i).getMenge());
                Statement stu = connection.createStatement();

                String queryu = "UPDATE LAGERBESTAND SET MENGE = " + lb.get(i).getMenge() + "WHERE BSTNR = " + lb.get(i).getBstnr();

                stu.executeUpdate(queryu);
            }

            Statement sts = connection.createStatement();
            String querys = "SELECT PREIS FROM ARTIKEL WHERE ARTNR = " + artnr;
            ResultSet rss = st.executeQuery(querys);

            rss.next();
            double rbet = rss.getDouble("PREIS");
            rbet *= menge;

            JDBC_Bestellung jb = new JDBC_Bestellung(knr, artnr, bmenge, rbet);

            Statement stnr = connection.createStatement();
            String querynr = "SELECT * FROM KUBEST";
            ResultSet rsnr = st.executeQuery(querynr);

            int nr = 1;
            while(rsnr.next()) {
                nr++;
            }

            String s1 = "'" + jb.knr + "', ";
            String s2 = "'" + jb.artnr + "', ";
            String s3 = "'" + jb.bmenge + "', ";
            String s4 = "TO_DATE('" + jb.bdat + "','dd.mm.yyyy hh24:mi:ss'), ";
            String s5 = "TO_DATE('" + jb.ldat + "','dd.mm.yyyy hh24:mi:ss'), ";
            String s6 = "'" + 1 + "', ";
            String s7 = "'" + String.format("%.2f",jb.rbet) + "')";

            Statement sti = connection.createStatement();
            String queryi = "INSERT INTO KUBEST (BENR, KNR, ARTNR, BMENGE, BDAT, LDAT, STATUS, RBET) VALUES (" + "'" + nr + "', " + s1 + s2 + s3 + s4 + s5 + s6 + s7;
            sti.addBatch(queryi);
            sti.executeBatch();

            try {
                Data.writeData(connection, knr, jb.ldat, jb.bmenge, jb.rbet, nr);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
