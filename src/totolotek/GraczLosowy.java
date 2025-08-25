package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;

import java.util.*;

public class GraczLosowy extends Gracz{


    // Stałe Gracza Losowego.

    private final long GRANICA_LOSOWANYCH_ŚRODKÓW = 100000001L;
    private final int GRANICA_LOSOWANYCH_KUPONÓW = 100;


    // Konstruktor.

    public GraczLosowy(
            String imię,
            String nazwisko,
            long pesel,
            Centrala centrala
    ) {

        // Wywołanie konstruktora nadklasy Gracz.

        super(imię,nazwisko,pesel,0,centrala);

        // Losowanie i dodanie środków do portfelu Gracza.

        Random random = new Random();
        long środkiFinansowe = random.nextLong(GRANICA_LOSOWANYCH_ŚRODKÓW);
        this.dodajŚrodki(środkiFinansowe);

    }


    // Metoda implementująca strategię kupowania Kuponów Gracza Losowego.

    @Override
    public void kupKupony() throws Wyjątek {

        // Pobieramy listę Kolektur z Informacji Centrali Totolotka i losujemy jedną Kolekturę.

        List<Kolektura> kolektury = this.dajInformację().dajKolektury();
        Random random = new Random();
        Kolektura kolektura = kolektury.get(random.nextInt(kolektury.size()));

        // W wylosowanej kolekturze kupujemy losową liczbę Kuponów o losowych liczbach zakładów i losowań.

        int liczbaKuponów = random.nextInt(GRANICA_LOSOWANYCH_KUPONÓW) + 1;
        for (int i = 0; i < liczbaKuponów; i++) {
            kolektura.kupKuponLosowo(
                    this,
                    random.nextInt(
                            Informacja.LICZBA_LOSOWAŃ_W_BLANKIECIE_SUP) + 1,
                    random.nextInt(Informacja.LICZBA_PÓL_W_BLANKIECIE) + 1
            );
        }

    }



}
