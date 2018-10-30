package zettel2.group17.logistikDB_Verwaltung;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Lagerbestand {

    private int bstnr;
    private int artnr;
    private int lnr;
    private int menge;

    public Lagerbestand(int bstnr, int artnr, int lnr, int menge) {

        this.bstnr = bstnr;
        this.artnr = artnr;
        this.lnr = lnr;
        this.menge = menge;
    }

    public int getBstnr() {
        return bstnr;
    }

    public void setBstnr(int bstnr) {
        this.bstnr = bstnr;
    }

    public int getArtnr() {
        return artnr;
    }

    public void setArtnr(int artnr) {
        this.artnr = artnr;
    }

    public int getLnr() {
        return lnr;
    }

    public void setLnr(int lnr) {
        this.lnr = lnr;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }
}
