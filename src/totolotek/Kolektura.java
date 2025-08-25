package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;
import Totolotek.wyjątki.WyjątekNielegalnejAktywności;
import Totolotek.wyjątki.WyjątekNiepoprawnegoArgumentu;

import java.util.*;

public class Kolektura {
    
    
    // Atrybuty Kolektury.

    private final Centrala centrala;
    private final int numerUnikatowy;
    
    private final Set<Kupon> aktywneKupony;
    private final Set<Kupon> zrealizowaneZnacząceKupony;
    
    
    // Konstruktor.

    public Kolektura(Centrala centrala, int numerUnikatowy) {
        
        this.centrala = centrala;
        this.numerUnikatowy = numerUnikatowy;
        
        this.aktywneKupony = new HashSet<>();
        this.zrealizowaneZnacząceKupony = new HashSet<>();
        
    }



    // Metoda losowania i jej metody pomocnicze:

    
    // Metoda losowania.

    void losowanie(Losowanie losowanie) {

        // Iterujemy osobno po zrealizowanych i niezrealizowanych kuponach.
        
        for (Kupon kupon : aktywneKupony) {
            policzKupony(kupon,losowanie);
        }
        
        Iterator<Kupon> iterator = zrealizowaneZnacząceKupony.iterator();

        while (iterator.hasNext()) {

            Kupon kupon = iterator.next();
            policzKupony(kupon,losowanie);

            // Jeśli zrealizowany Kupon nie ma więcej
            // aktywnych losowań to zostaje usunięty z listy.

            if (!kupon.czyAktywneLosowania()) {
                iterator.remove();
            }

        }


    }

    
    // Metoda licząca Kupony, tj:
    // Przychód losowania z kuponów,
    // Liczbę zwycięskich zakładów każdego stopnia.

    private void policzKupony(Kupon kupon, Losowanie losowanie) {

        int numer = losowanie.dajNumer();

        if (kupon.czyTwojeLosowanie(numer)) {

            losowanie.dodajDochód(kupon.dajDochódNaLosowanie());
            sprawdźKupon(kupon, losowanie);

        }

    }


    // Metoda sprawdzająca Kupony,
    // tj. sprawdzająca liczbę zwycięskich zakładów.

    private void sprawdźKupon(Kupon kupon, Losowanie losowanie) {

        int[] wygraneLiczby = losowanie.dajLiczby();
        for (Zakład zakład : kupon.dajZakłady()) {

            if (zakład != null) {
                int trafione =
                        policzTrafienia(zakład.dajLiczby(), wygraneLiczby);

                if (trafione > 2) {
                    losowanie.zwycięskiZakład(7 - trafione);
                    kupon.wygraneLosowanie(losowanie);
                }
            }

        }

    }


    // Metoda licząca trafienia pojedynczego Zakładu.

    private int policzTrafienia(int[] a, int[] b) {

        // Algorytm rodem z WDP

        Arrays.sort(a);
        Arrays.sort(b);

        int trafione = 0;
        int i = 0, j = 0;

        while (i < 6 && j < 6) {

            if (a[i] == b[j]) {
                trafione++;
                i++;
                j++;
            }

            else if (a[i] > b[j]) j++;
            else i++;

        }

        return trafione;

    }



    // Metoda realizacji Kuponów i jej metody pomocnicze:


    // Metoda realizacji kuponów.

    public void zrealizujKupon(Gracz gracz, Kupon kupon) throws Wyjątek {

        // Sprawdzamy, czy Kupon został kupiony
        // w tej Kolekturze i czy nie został jeszcze zrealizowany.

        if (!aktywneKupony.contains(kupon)) {
            throw new WyjątekNielegalnejAktywności("Wykryto próbę oszustwa.");
        }

        // Obliczamy wygraną kwotę i ewentualnie wypłacamy nagrodę.
        // Wołana funckja pomocnicza zwraca tablicę kwoty,
        // gdzie kwoty[0] to wygrana netto, a kwoty[1] to podatek.

        long[] kwoty = obliczWygranaKwotę(kupon);
        if (kwoty[0] > 0) {
            if (kwoty[1] > 0) centrala.odprowadźPodatek(kwoty[1]);
            centrala.wypłaćNagrodę(kwoty[0]);
            gracz.dodajŚrodki(kwoty[0]);
        }

        // Usuwamy z aktywnych kuponów i ewentualnie
        // dodajemy do zbioru kuponów zrealizowanych znaczących.

        aktywneKupony.remove(kupon);
        if (kupon.czyAktywneLosowania()) {
            zrealizowaneZnacząceKupony.add(kupon);
        }

    }


    // Metoda licząca wygraną kwotę.

    private long[] obliczWygranaKwotę(Kupon kupon) {

        // Tworzymy tablicę i zapisujemy Zakłady.

        long[] kwoty = new long[2];
        Zakład[] zakłady = kupon.dajZakłady();

        // Iterujemy po każdym Zakłsdzie.

        for (Zakład zakład : zakłady) {
            if (zakład != null) {

                int[] liczby = zakład.dajLiczby();

                // Iterujemy po kązdym z wygranych losowań Kuponu.

                for (Losowanie losowanie : kupon.dajWygraneLosowania()) {

                    // Liczymy trafienia.

                    int trafione = policzTrafienia(
                            losowanie.dajLiczby(), liczby);

                    // Jeśli kwalifikujemy się do wygranej to
                    // liczymy kwoty zwolnione i nie zwolnione z podatku.

                    if (trafione > 2) {

                        long wygrana =
                                losowanie.dajKwotyWygranych()[7 - trafione];

                        if (wygrana >= 228000) {

                            long podatek = (long) (wygrana * 0.1);
                            kwoty[0] += (wygrana - podatek);
                            kwoty[1] += podatek;

                        } else kwoty[0] += wygrana;

                    }


                }

            }
        }

        // Zwracamy kwoty wygranych[0] i podatku[1].

        return kwoty;

    }


    // Metody kupowania kuponów:


    // Metoda sprzedaży gotowego Kuponu.

    private void sprzedaj(Gracz gracz, Kupon kupon) throws Wyjątek{

        // Sprawdzamy, czy Gracza stać na zakup.

        if (!gracz.pobierzŚrodki(kupon.dajCenę())) {
            throw new WyjątekNielegalnejAktywności("Niewystarczające środki.");
        }

        // Po pobraniu środków autoryzujemy Kupon
        // nadając mu numer unikatowy i identyfikator.

        kupon.autoryzuj();

        // Przekazujemy przychód Cenrali
        // i zapisujemy Kupon w Koelkturze oraz u Gracza.

        centrala.przychódZaKupon(kupon);
        gracz.dodajKupon(kupon);
        aktywneKupony.add(kupon);

    }


    // Metoda zakupu Kuponu na podstawie Blankietu.

    public void kupKuponBlankiet(Gracz gracz, Blankiet blankiet) throws Wyjątek{

        Kupon kupon = new Kupon(
                this,
                centrala,
                blankiet.dajLiczbęLosowań(),
                blankiet.dajZakłady()
        );

        sprzedaj(gracz,kupon);

    }


    // Metoda zakupu Kupona na chybił-trafił.

    public void kupKuponLosowo (
            Gracz gracz, int liczbaLosowań, int liczbaZakładów) throws Wyjątek{

        if (liczbaZakładów < 1 || liczbaLosowań < 1)
            throw new WyjątekNiepoprawnegoArgumentu(
                    "Argumnety powinny być dodatnie.");

        if (liczbaZakładów > Informacja.LICZBA_PÓL_W_BLANKIECIE ||
                liczbaLosowań > Informacja.LICZBA_LOSOWAŃ_W_BLANKIECIE_SUP)
            throw new WyjątekNiepoprawnegoArgumentu(
                    "Argumnety są zbyt duże.");

        Zakład[] zakłady = new Zakład[liczbaZakładów];
        for (int i = 0; i < zakłady.length; i++) {
            zakłady[i] = new Zakład(centrala.losujLiczby());
        }

        Kupon kupon = new Kupon(this, centrala, liczbaLosowań, zakłady);

        sprzedaj(gracz, kupon);

    }


    // Metoda udostępniająca numer unikatowy Kolektury.

    int dajNumer() {
        return numerUnikatowy;
    }



}
