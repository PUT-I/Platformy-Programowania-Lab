package com.put.lab2;

import java.util.Iterator;

public class Lab2 {

    public static void main(String args[]) {
        Firma firma = new Firma();
        firma.dodajPracownika(new Pracownik("i1", "n1", Stanowisko.KIEROWNIK, 20000))
                .dodajPracownika(new Pracownik("i1", "n1", Stanowisko.DYREKTOR, 30000))
                .dodajPracownika(new Pracownik("i1", "n1", Stanowisko.ROBOL, 1500))
                .dodajPracownika(new Pracownik("i2", "n2", Stanowisko.ROBOL, 1200))
                .dodajPracownika(new Pracownik("i2", "n2", Stanowisko.ROBOL, 1600))
                .dodajPracownika(new Pracownik("i2", "n2", Stanowisko.ROBOL, 2500))
                .dodajPracownika(new Pracownik("i2", "n2", Stanowisko.ROBOL, 5100))
                .dodajPracownika(new Pracownik("i2", "n2", Stanowisko.ROBOL, 1000))
                .dodajPracownika(new Pracownik("i2", "n2", Stanowisko.ROBOL, 500))
                .dodajPracownika(new Pracownik("i1", "n1", Stanowisko.DYREKTOR, 30000));

        System.out.println("Pracownicy:");
        for (Pracownik p : firma) {
            System.out.println(p);
        }

        System.out.println();
        System.out.println("Robole:");

        Iterator<Pracownik> p = firma.iterator(Stanowisko.ROBOL);
        while (p.hasNext()) {
            Pracownik p1 = p.next();
            System.out.println(p1.toString());
        }

        System.out.println();
        System.out.println("Srednia: " + firma.sredniaPensja());
        System.out.println("Srednia dla robol: " + firma.sredniaPensja(Stanowisko.ROBOL));
    }

}
