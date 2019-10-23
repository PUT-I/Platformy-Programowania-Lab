package com.put.lab2;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Pracownik extends Osoba {

    private final Stanowisko stanowisko;
    private final int pensja;


    public Pracownik(String imie, String nazwisko, Stanowisko stanowisko, int pensja) {
        super(imie, nazwisko);
        this.stanowisko = stanowisko;
        this.pensja = pensja;
    }

}
