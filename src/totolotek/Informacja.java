package Totolotek.totolotek;

import java.util.*;

public class Informacja /* Centrali Totolotka */ {


    // Atrybuty Informacji Centrali Totolotka.

    private int ostatnieLosowanie;
    private final List<Kolektura> kolektury;


    // Stałe Pakietu Totolotek:

    // Zakłady:

    public static final int CENA_ZAKŁADU_BRUTTO = 300;
    public static final int CENA_ZAKŁADU_NETTO = 240;
    public static final int PODATEK_OD_ZAKŁADU = 60;
    public static final int LICZBY_W_ZAKŁADZIE = 6;

    // Blankiety:

    public static final int LICZBA_PÓL_W_BLANKIECIE = 8;
    public static final int LICZBA_LOSOWAŃ_W_BLANKIECIE_SUP = 10;
    public static final int LICZBA_LOSOWAŃ_W_BLANKIECIE_INF = 1;

    // Losowania:

    public static final int GRANICA_LICZB_W_GRZE = 49;

    // Nagrody:

    public static final long GWARANTOWANA_PULA_1_STOPNIA = 200000000;
    public static final double PROCENT_DOCHODU_LOSOWANIA_PRZEZNACZONY_NA_NAGRODY = 0.51;
    public static final double PROCENT_NAGRÓD_PRZEZNACZONY_NA_1_STOPIEŃ = 0.44;
    public static final double PROCENT_RESZTY_NAGRÓD_PRZEZNACZONY_NA_2_STOPIEŃ = 0.08;
    public static final long NAGRODA_4_STOPNIA = 2400L;
    public static final long USTALONY_MNOŻNIK_DLA_NAGRÓD_3_STOPNIA = 15L;


    // Konstruktor.

    Informacja(Centrala centrala) {
        ostatnieLosowanie = 0;
        kolektury = centrala.dajKolektury();
    }


    // Funkcja aktualizująca numer ostatniego odbytego losowania.

    void losowanie() {
        ostatnieLosowanie++;
    }


    // Funkcja udostępniająca numer ostatniego odbytego losowania.

    public int dajOstatnieLosowanie() {
        return ostatnieLosowanie;
    }


    // Funkcja udostępniająca numer najbliższego losowania.

    public int dajNajbliższeLosowanie() {
        return ostatnieLosowanie + 1;
    }


    // Funckja udostępniająca listę Kolektur.

    public List<Kolektura> dajKolektury() {
        return kolektury;
    }


}
