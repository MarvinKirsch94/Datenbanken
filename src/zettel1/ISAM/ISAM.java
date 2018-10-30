package zettel1.ISAM;
import java.io.*;
public class ISAM {

    
    public static void main(String args[]) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int keyboard, suchnr;
        int runtime_var = 1;

        int artnr;
        String artbez;
        double preis;
        String mge;
        String kuehl;

        ArtikelVerwaltung v = new ArtikelVerwaltung();

        while (runtime_var == 1) {
            System.out.println("");
            System.out.println("Verwaltung");
            System.out.println("(1) Neuen Eintrag erstellen.");
            System.out.println("(2) Alles anzeigen");
            System.out.println("(3) Suche.");
            System.out.println("(4) Ende");

            keyboard = Integer.parseInt(in.readLine());

            switch (keyboard) {
                case 1:
                    System.out.println("Artikelnummer: ");
                    artnr = Integer.parseInt(in.readLine());
                    System.out.println("Artikelbezeichnung: ");
                    artbez = in.readLine();
                    System.out.println("Preis: ");
                    preis = Double.parseDouble(in.readLine());
                    System.out.println("Mengeneinheit: ");
                    mge = in.readLine();
                    System.out.println("Kuehlklasse(TK, KS, NK): ");
                    kuehl = in.readLine();

                    v.schreiben(artnr, artbez, preis, mge, kuehl);

                    v.indexVerwaltung();
                    break;
                case 2:
                    v.indexVerwaltung();
                    v.ausgabe();
                    break;
                case 3:
                    v.indexVerwaltung();

                    System.out.println("Artikelnummer? ");
                    suchnr = Integer.parseInt(in.readLine());
                    int rueck = v.seek(suchnr);
                    if (rueck == -1) {
                        System.out.println("keine Daten gefunden ");
                    }
                    break;
                case 4:
                    v.indexVerwaltung();
                    v.indexSchreiben();
                    runtime_var = 0;
                    break;
                default:
                    System.out.println("ungültige Eingabe! Bitte erneut eingeben.");
            }
        }
    }
}
