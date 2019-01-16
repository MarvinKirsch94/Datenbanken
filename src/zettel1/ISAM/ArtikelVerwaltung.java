
package zettel1.ISAM;



import java.io.*;
import java.util.*;

public class ArtikelVerwaltung {

    List<Artikel> artikel = new ArrayList<>();
    List<Di> isam = new ArrayList<>();

    //Methoden
    public void schreiben(int artnr, String artbez, double preis, String mge, String kuehl){
        try{

            FileWriter fileW = new FileWriter("ARTIKEL.CSV",true);

            PrintWriter pw = new PrintWriter(fileW);

            pw.print(artnr);
            pw.print(';');
            pw.print(artbez);
            pw.print(';');
            pw.print(preis);
            pw.print(';');
            pw.print(mge);
            pw.print(';');
            pw.print(kuehl+System.getProperty("line.separator"));

            pw.close();

        } catch(IOException ioex){
            System.out.println("Es ist folgender IOExpeptionFehler aufgetreten: "+ioex.getMessage());
        }

    }

    public void indexVerwaltung(){
        try{

            RandomAccessFile file = new RandomAccessFile("ARTIKEL.CSV","r");
            long offset = 0;

            offset = file.getFilePointer();
            artikel.clear();
            isam.clear();

            String line;
            while ((line = file.readLine()) != null) {

                Di obj = new Di();

                String[] split = line.split(";");

                obj.offset = offset;
                obj.artnr = Integer.parseInt(split[0]);

                isam.add(obj);
                Artikel eingabe = new Artikel();
                eingabe.artnr = Integer.parseInt(split[0]);
                eingabe.artbez = split[1];
                eingabe.preis = Double.parseDouble(split[2]);
                eingabe.mge = split[3];
                eingabe.kuehl = split[4];

                artikel.add(eingabe);
                offset = file.getFilePointer();
            }
            file.close();
        } catch(IOException ioex){
            System.out.println("Es ist folgender IOExpeptionFehler aufgetreten: "+ioex.getMessage());
        }
    }

    public void indexSchreiben(){
        Di obj = new Di();
        try{

            FileWriter fw = new FileWriter("ARTIKEL.IDX",false);
            fw.write("OFFSET; ARTNR "+System.getProperty("line.separator"));
            for (int i = 0;i < isam.size();i++) {
                obj = isam.get(i);
                fw.write(""+obj.offset+";"+obj.artnr+System.getProperty("line.separator"));
            }
            fw.close();
        } catch(IOException ioex){
            System.out.println("Es ist folgender IOExpeptionFehler aufgetreten: "+ioex.getMessage());
        }
    }

    public void ausgabe(){
        System.out.println("");
        System.out.println("ARTNR; ARTBEZ; PREIS; MGE; KUEHL");
        System.out.println("-------------------");
        for (int i =0; i < artikel.size(); i++){
            Artikel art = new Artikel();
            art = artikel.get(i);
            System.out.println(art.artnr+";"+art.artbez+";"+art.preis+";"+art.mge +";"+art.kuehl);
        }
    }

    public void indexAusgabe(){

        System.out.println("");
        System.out.println("Offset; Artnr");
        System.out.println("------------------");
        for (int i = 0; i <= isam.size(); i++){
            Di di = new Di();
            di = isam.get(i);
            System.out.println(di.offset+"; "+di.artnr);
        }
    }

    public int seek(int suchnr){
        Di di = new Di();
        for (int i = 0; i<artikel.size(); i++){
            long offset;

            di = isam.get(i);

            if (di.artnr == suchnr) {
                offset = di.offset;
                try{
                    RandomAccessFile file = new RandomAccessFile("ARTIKEL.CSV","r");
                    //springt an "Offset-Position"
                    file.seek(offset);
                    System.out.println("");
                    System.out.println("Folgender Artikel wurde gefunden: ");
                    System.out.println("");
                    System.out.println("ARTNR;ARTBEZ;PREIS;MGE;KUEHL");

                    System.out.println(file.readLine());

                    file.close();
                } catch(IOException ioex){
                    System.out.println("Es ist ein Fehler aufgetreten: "+ioex.getMessage());
                }
                return 1;
            }
        }
        return -1;
    }
} 