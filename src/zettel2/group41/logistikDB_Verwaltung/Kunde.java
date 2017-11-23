package zettel2.group41.logistikDB_Verwaltung;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Kunde {

    private int knr;
    private String kname;
    private int plz;
    private String ort;
    private String strasse;

    public Kunde(int knr, String kname, int plz, String ort, String strasse) {

        this.knr = knr;
        this.kname = kname;
        this.plz = plz;
        this.ort = ort;
        this.strasse = strasse;
    }

    public int getKnr() {
        return knr;
    }

    public void setKnr(int knr) {
        this.knr = knr;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }
}
