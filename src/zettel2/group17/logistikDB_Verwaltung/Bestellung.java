package zettel2.group17.logistikDB_Verwaltung;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Bestellung {

    private int bstnr;
    private int knr;
    private int status;
    private double rsum;
    private String bdat;
    private String ldat;
    private String rdat;

    private static SimpleDateFormat dateFormatWrite = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");

    public Bestellung(int bstnr, int knr, int status, double rsum) {

        this.bstnr = bstnr;
        this.status = status;
        this.knr = knr;
        this.rsum = rsum;
        LocalDateTime ldtn = LocalDateTime.now();
        Date dn = Date.from(ldtn.atZone(ZoneId.systemDefault()).toInstant());
        this.bdat = dateFormatWrite.format(dn);
    }

    public int getBstnr() {
        return bstnr;
    }

    public void setBstnr(int bstnr) {
        this.bstnr = bstnr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getKnr() {
        return knr;
    }

    public void setKnr(int knr) {
        this.knr = knr;
    }

    public void setRsum(double rsum) {
        this.rsum = rsum;
    }

    public double getRsum() {
        return rsum;
    }

    public String getBdat() {
        return bdat;
    }

    public void setBdat(String bdat) {
        this.bdat = bdat;
    }

    public String getLdat() {
        return ldat;
    }

    public void setLdat(String ldat) {
        this.ldat = ldat;
    }

    public String getRdat() {
        return rdat;
    }

    public void setRdat(String rdat) {
        this.rdat = rdat;
    }

    public static SimpleDateFormat getDateFormatWrite() {
        return dateFormatWrite;
    }

    public static void setDateFormatWrite(SimpleDateFormat dateFormatWrite) {
        Bestellung.dateFormatWrite = dateFormatWrite;
    }
}
