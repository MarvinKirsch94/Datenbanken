package zettel2.group17.logistikDB_Verwaltung;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class Lager {

    private int lnr;
    private String lort;
    private int lplz;

    public Lager(int lnr, String lort, int lplz) {

        this.lnr = lnr;
        this.lort = lort;
        this.lplz = lplz;
    }

    public int getLnr() {
        return lnr;
    }

    public void setLnr(int lnr) {
        this.lnr = lnr;
    }

    public String getLort() {
        return lort;
    }

    public void setLort(String lort) {
        this.lort = lort;
    }

    public int getLplz() {
        return lplz;
    }

    public void setLplz(int lplz) {
        this.lplz = lplz;
    }
}
