package com.put.lab2;

import java.util.ArrayList;
import java.util.Iterator;

public class Firma implements Iterable<Pracownik> {

    private ArrayList<Pracownik> pracownicy = new ArrayList<>();

    public Firma dodajPracownika(Pracownik pracownik) {
        pracownicy.add(pracownik);
        return this;
    }

    public int liczbaPracownikow() {
        return pracownicy.size();
    }

    public int sredniaPensja() {
        int suma = 0;
        for (Pracownik p : this) {
            suma += p.getPensja();
        }

        return suma / pracownicy.size();
    }

    public int sredniaPensja(Stanowisko s) {
        int suma = 0;
        Iterator<Pracownik> p = this.iterator(Stanowisko.ROBOL);
        while (p.hasNext()) {
            suma += p.next().getPensja();
        }

        return suma / pracownicy.size();
    }

    @Override
    public Iterator<Pracownik> iterator() {
        return pracownicy.iterator();
    }

    public Iterator<Pracownik> iterator(Stanowisko s) {
        return pracownicy.stream()
                .filter(p -> p.getStanowisko() == s)
                .iterator();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Pracownik pracownik : pracownicy) {
            result.append(pracownik)
                    .append("\n");
        }
        return result.toString();
    }


}
