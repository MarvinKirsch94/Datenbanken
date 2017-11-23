package zettel2.group41.logistikDB_Verwaltung;

import java.util.ArrayList;

/**
 * Created by Marvin Kirsch on 23.11.2017.
 * Matrikelnr.: 11118687
 */
public class JDBC_Verwaltung {

    private ArrayList<Artikel> artikel = new ArrayList<>();
    private ArrayList<Kunde> kunden = new ArrayList<>();
    private ArrayList<Lager> lager = new ArrayList<>();
    private ArrayList<Lagerbestand> lagerbestaende = new ArrayList<>();

    //LogistikDB-Verwaltung ------- Menu

    //A)
    public void showAllArtikel() {


    }

    //B)
    public void showAllLager() {


    }

    //C)
    public void showAllKunde() {


    }

    //D)
    public void showArtDetailsAndLagerbestand() {


    }

    //E)
    public void setNewLagerbestandForArtikel() {


    }

    //F)
    public void updateMgeOfLagerbestand() {


    }

    //setter and getter

    public ArrayList<Artikel> getArtikel() {
        return artikel;
    }

    public void setArtikel(ArrayList<Artikel> artikel) {
        this.artikel = artikel;
    }

    public ArrayList<Kunde> getKunden() {
        return kunden;
    }

    public void setKunden(ArrayList<Kunde> kunden) {
        this.kunden = kunden;
    }

    public ArrayList<Lager> getLager() {
        return lager;
    }

    public void setLager(ArrayList<Lager> lager) {
        this.lager = lager;
    }

    public ArrayList<Lagerbestand> getLagerbestaende() {
        return lagerbestaende;
    }

    public void setLagerbestaende(ArrayList<Lagerbestand> lagerbestaende) {
        this.lagerbestaende = lagerbestaende;
    }
}
