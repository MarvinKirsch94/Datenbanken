package zettel2.group17.logistikDB_Verwaltung;

import java.util.Date;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Artikel {

    private int artnr;
    private String artbez;
    private String mge;
    private double preis;
    private String kuehl;
    private int anzbo;

    public Artikel(int artnr, String artbez, String mge, double preis, String kuehl, int anzbo) {

        this.artnr = artnr;
        this.artbez = artbez;
        this.mge = mge;
        this.preis = preis;
        this.kuehl = kuehl;
        this.anzbo = anzbo;
    }

    public int getArtnr() {
        return artnr;
    }

    public void setArtnr(int artnr) {
        this.artnr = artnr;
    }

    public String getArtbez() {
        return artbez;
    }

    public void setArtbez(String artbez) {
        this.artbez = artbez;
    }

    public String getMge() {
        return mge;
    }

    public void setMge(String mge) {
        this.mge = mge;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getKuehl() {
        return kuehl;
    }

    public void setKuehl(String kuehl) {
        this.kuehl = kuehl;
    }

    public int getAnzbo() {
        return anzbo;
    }

    public void setAnzbo(int anzbo) {
        this.anzbo = anzbo;
    }
}
