package zettel2.group41.logistikDB_Verwaltung;

import java.util.Date;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Artikel {

    private int artnr;
    private String artbez;
    private int mge;
    private double preis;
    private double steu;
    private Date edat;

    public Artikel(int artnr, String artbez, int mge, double preis, double steu, Date edat) {

        this.artnr = artnr;
        this.artbez = artbez;
        this.mge = mge;
        this.preis = preis;
        this.steu = steu;
        this.edat = edat;
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

    public int getMge() {
        return mge;
    }

    public void setMge(int mge) {
        this.mge = mge;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public double getSteu() {
        return steu;
    }

    public void setSteu(double steu) {
        this.steu = steu;
    }

    public Date getEdat() {
        return edat;
    }

    public void setEdat(Date edat) {
        this.edat = edat;
    }
}
