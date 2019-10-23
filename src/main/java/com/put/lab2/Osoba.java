package com.put.lab2;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Osoba {

    protected final String imie;
    protected final String nazwisko;

    Osoba(String imie, String nazwisko) {
        this.imie = imie;
        this.nazwisko = nazwisko;
    }

}
