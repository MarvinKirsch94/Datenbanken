package zettel2.group17.logistikDB_Verwaltung;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Bpos {

    private int posnr;
    private int bstnr;
    private int artnr;
    private int mge;
    private double wert;

    public Bpos(int posnr, int bstnr, int artnr, int mge, double wert) {

        this.bstnr = bstnr;
        this.artnr = artnr;
        this.posnr = posnr;
        this.mge = mge;
        this.wert = wert;
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

    public int getPosnr() {
        return posnr;
    }

    public void setPosnr(int posnr) {
        this.posnr = posnr;
    }

    public int getMge() {
        return mge;
    }

    public void setMge(int mge) {
        this.mge = mge;
    }

    public double getWert() {
        return wert;
    }

    public void setWert(double wert) {
        this.wert = wert;
    }
}
