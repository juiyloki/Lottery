package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;

import java.util.*;

public class GraczStałoBlankietowy extends Gracz{


    // Atrybuty Gracza Stałoblankietowego.

    private final Blankiet ulubionyBlankiet;

    private final int częstotliwośćZakupów;
    private int numerNastępnegoLosowania;

    private final ArrayList<Kolektura> kolektury;
    private int następnaKolektura;


    // Konstruktor.

    public GraczStałoBlankietowy(
            String imię,
            String nazwisko,
            long pesel,
            Centrala centrala,
            long środkiFinansowe,
            ArrayList<Kolektura> kolektury,
            Blankiet ulubionyBlankiet,
            int częstotliwośćZakupów
    ) {

        // Wywołanie konstruktora nadklasy Gracz.

        super(imię, nazwisko, pesel, środkiFinansowe, centrala);

        // Inicjalizacja atrybutów.

        this.kolektury = kolektury;
        this.częstotliwośćZakupów = częstotliwośćZakupów;
        this.ulubionyBlankiet = ulubionyBlankiet;

        this.numerNastępnegoLosowania = this.dajInformację().dajNajbliższeLosowanie();
        this.następnaKolektura = 0;

    }


    // Metoda implementująca strategię kupowania Kuponów Gracza Stałoblankietowego.

    @Override
    public void kupKupony() throws Wyjątek {

        // Sprawdzamy czy minęła odpowiednia liczba losowań od ostatnich zakupów.

        if (this.dajInformację().dajNajbliższeLosowanie() < numerNastępnegoLosowania) return;

        // Kupujemy Kupon na podstawie ulubionego Blankietu.

        kolektury.get(następnaKolektura).kupKuponBlankiet(this, ulubionyBlankiet);

        // Aktualizujemy listę kolektur i zapisujemy następne losowanie.

        następnaKolektura = ++następnaKolektura % kolektury.size();
        numerNastępnegoLosowania += częstotliwośćZakupów;

    }


}
