package com.put.lab2;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Stanowisko {

    DYREKTOR("Dyrektor", 1, "Szefunieńko"),
    KIEROWNIK("Kierownik", 2, "Szefuńco"),
    SETNIK("Setnik", 3, "Szef mniejszy"),
    DZIESIETNIK("Dziesiętnik", 4, "Podszef"),
    ROBOL("Robol", 5, "Zwykły robol");

    private final String nazwa;
    private final int poziom;
    private final String opis;

    private Stanowisko(String nazwa, int poziom, String opis) {
        this.nazwa = nazwa;
        this.poziom = poziom;
        this.opis = opis;
    }

}
