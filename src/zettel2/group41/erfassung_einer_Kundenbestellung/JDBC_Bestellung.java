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

    private SimpleDateFormat dateFormatWrite = new SimpleDateFormat("dd.MM.YYYY - HH.mm.ss");

    public JDBC_Bestellung(int knr, int artnr, int bmenge, double rbet) {
        this.knr = knr;
        this.artnr = artnr;
        this.bmenge = bmenge;
        this.rbet = rbet;
        this.bdat = dateFormatWrite.format(LocalDateTime.now().toString());
        this.ldat = dateFormatWrite.format(LocalDateTime.now().plusDays(14).toString());
    }

    public static void buchen(Connection connection, int artnr, int knr, int bmenge) {

        //SELECT * FROM LAGERBESTAND WHERE ARTNR = x

        try {

            ArrayList<Lagerbestand> lb = new ArrayList<>();
            int bstnr = 0;
            int menge = 0;
            int lnr = 0;

            Statement st = connection.createStatement();

            String query = "SELECT BSTNR, MENGE FROM LAGERBESTAND WHERE ARTNR = " + artnr;

            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {

                bstnr = rs.getInt("BSTNR");
                menge = rs.getInt("MENGE");
                lnr = rs.getInt("LNR");

                Lagerbestand l = new Lagerbestand(bstnr, artnr, lnr, menge);

                lb.add(l);
            }

            int abmenge = menge;

            for(int i = 0; i < lb.size(); i++) {

                int temp = lb.get(i).getMenge();
                if(abmenge > temp) {

                    abmenge -= lb.get(i).getMenge();
                    lb.get(i).setMenge(0);
                } else if(abmenge <= temp) {

                    //minus and update
                    lb.get(i).setMenge(lb.get(i).getMenge() - abmenge);
                    abmenge = 0;
                }

                Statement stu = connection.createStatement();

                String queryu = "UPDATE LAGERBESTAND SET MENGE = " + lb.get(i).getMenge() + "WHERE BSTNR = " + lb.get(i).getBstnr();

                st.executeUpdate(query);
            }

            Statement sts = connection.createStatement();
            String querys = "SELECT PREIS FROM ARTIKEL WHERE ARTNR = " + artnr;
            ResultSet rss = st.executeQuery(querys);

            double rbet = rss.getDouble("PREIS");
            rbet *= menge;

            JDBC_Bestellung jb = new JDBC_Bestellung(knr, artnr, menge, rbet);

            String s1 = "'" + jb.knr + "', ";
            String s2 = "'" + jb.artnr + "', ";
            String s3 = "'" + jb.bmenge + "', ";
            String s4 = "'" + jb.bdat + "', ";
            String s5 = "'" + jb.ldat + "', ";
            String s6 = "'" + 1 + "', ";
            String s7 = "'" + jb.rbet + "')";

            Statement sti = connection.createStatement();
            String queryi = "INSERT INTO KUBEST(KNR, ARTNR, BMENGE, BDAT, LDAT, STATUS, RBET) VALUES " + s1 + s2 + s3 + s4 + s5 + s6 + s7;
            sti.addBatch(queryi);
            sti.executeBatch();

            try {
                Data.writeData(connection, knr, jb.ldat, jb.bmenge, jb.rbet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
