package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;

import java.util.*;

public class GraczStałoLiczbowy extends Gracz{


    // Atrybuty Gracza Stałoliczbowego

    private final int[] ulubioneLiczby;

    private int numerOstatniegoLosowania;

    private final ArrayList<Kolektura> kolektury;
    private int następnaKolektura;


    // Konstruktor.

    public GraczStałoLiczbowy (
            String imię,
            String nazwisko,
            long pesel,
            Centrala centrala,
            long środkiFinansowe,
            ArrayList<Kolektura> kolektury,
            int[] ulubioneLiczby
            ) {

        // Wywołanie konstruktora nadklasy Gracz.

        super(imię, nazwisko, pesel, środkiFinansowe, centrala);

        // Inicjalizujemy pozostałe atrybuty.

        this.kolektury = kolektury;
        this.ulubioneLiczby = ulubioneLiczby;
        this.numerOstatniegoLosowania = 0;
        this.następnaKolektura = 0;

    }


    // Metoda implementująca strategię kupowania Kuponów Gracza Stałoliczbowego.

    public void kupKupony() throws Wyjątek {

        // Sprawdzamy czy poprzedni Kupon ma jeszcze aktywne Losowania.

        if (numerOstatniegoLosowania > this.dajInformację().dajOstatnieLosowanie()) return;

        // Tworzymy nowy Blankiet i wypełniamy pola ulubionymi liczbam
        // oraz wybiermay liczbę losowań.

        Blankiet blankiet = new Blankiet();
        for (int i = 0; i < 8; i++) {
            blankiet.wypełnijPole(i + 1,ulubioneLiczby);
        }
        blankiet.wybierzLiczbęLosowań(10);

        // Kupujemy kupon.

        kolektury.get(następnaKolektura).kupKuponBlankiet(this, blankiet);

        // Aktualizujemy listę kolektur i ostatnie losowanie.

        numerOstatniegoLosowania = this.dajInformację().dajOstatnieLosowanie() + 10;
        następnaKolektura = ++następnaKolektura % kolektury.size();

    }


}
