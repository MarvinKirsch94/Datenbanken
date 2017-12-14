package zettel2.group41.erfassung_einer_Kundenbestellung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Marvin Kirsch on 24.11.2017.
 * Matrikelnr.: 11118687
 */
public class Data {

    public static void writeData(Connection connection, int knr, String ldat, int bmenge, double rbet, int nr) throws IOException, SQLException {

        Statement st = connection.createStatement();

        String query = "SELECT * FROM KUNDE WHERE KNR = " + knr;

        ResultSet rs = st.executeQuery(query);
        rs.next();

        String fileName = "AB" + knr + "B" + nr;
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

        //Kundenanschrift
        bw.write(knr + "\n");
        bw.write(rs.getString("KNAME") + "\n");
        bw.write(rs.getInt("PLZ") + "\n");
        bw.write(rs.getString("ORT") + "\n");
        bw.write(rs.getString("STRASSE") + "\n");
        bw.write(ldat + "\n");
        bw.write(bmenge + "\n");
        bw.write(rbet + "\n");

        bw.flush();
        bw.close();
    }
}
