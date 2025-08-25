package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;

import java.util.*;

public class Main {

    private static final int LICZBA_KOLEKTUR = 10;
    private static final int LICZBA_GRACZY = 200;
    private static final int LICZBA_LOSOWAŃ = 20;

    private static final long RANDOM_PESEL_GRANICA = 100000000000L;
    private static final long RANDOM_śRODKI_GRANICA = 10000000000L;
    private static final int RANDOM_CZĘSTOTLIWOŚĆ_ZAKUPÓW_GRANICA = 15;
    private static final int SZANSA_NA_ANULOWANE_POLE = 20;

    // Prezentacja pakietu Totolotek w działaniu.

    public static void main(String[] args) {

        // Tworzenie Centrali.

        BudżetPaństwa budżetPaństwa = new BudżetPaństwa();
        Centrala centrala = new Centrala(budżetPaństwa);

        // Tworzenie ustalonej liczby Kolektur.

        for (int i = 0; i < LICZBA_KOLEKTUR ; i++) {
            centrala.stwórzKolekturę();
        }

        // Tworzenie po ustaloną liczbę graczy każdego rodzaju.

        HashSet<Gracz> gracze = new HashSet<>();
        stwórzGraczy(gracze, centrala);

        // Przeprowadzenie ustalonej liczby losowań, wraz z:
        // Kupywaniem Kuponów przez graczy,
        // Realizowaniem gotowych kuponów.

        for (int i = 0; i < LICZBA_LOSOWAŃ; i++) {
            for (Gracz gracz : gracze) {
                try {
                    gracz.kupKupony();
                } catch (Wyjątek wyjątek) {
                    wyjątek.wypisz();
                }
            }
            centrala.losowanie();
            for (Gracz gracz : gracze) {
                try {
                    gracz.zrealizujGotoweKupony();
                } catch (Wyjątek wyjątek) {
                    wyjątek.wypisz();
                }
            }
        }

        // Wydruk podsumowania.

        centrala.drukujWszystko();
        budżetPaństwa.drukujWszystko();

    }


    // Funkcja odpowiedzialna za tworzenie graczy 4 rodzajów.

    private static void stwórzGraczy(HashSet<Gracz> gracze, Centrala centrala) {

        Random random = new Random();
        List<Kolektura> kolektury = centrala.dajInformację().dajKolektury();

        // Tworzenie graczy losowych, o godności Marek Fujarek i losowym PESELu

        for (int i = 0; i < LICZBA_GRACZY; i++) {
            Gracz gracz = new GraczLosowy(
                    "Marek",
                    "Fujarek",
                    random.nextLong(RANDOM_PESEL_GRANICA),
                    centrala
            );
            gracze.add(gracz);
        }

        // Tworzenie graczy minimalistów,
        // o godności Hannah Montanah, oraz losowym:
        // PESELu, środkach oraz liście Kolektur.

        for (int i = 0; i < LICZBA_GRACZY; i++) {
            Gracz gracz = new GraczMinimalista(
                    "Hannah",
                    "Montanah",
                    random.nextLong(RANDOM_PESEL_GRANICA),
                    centrala,
                    random.nextLong(RANDOM_śRODKI_GRANICA),
                    kolektury.get(random.nextInt(kolektury.size()))
            );
            gracze.add(gracz);
        }

        // Tworzenie graczy stałoblankietowych
        // o godności Martin Luther King i losowym:
        // PESELu, środkach, liście Kolektur oraz Blankiecie
        // i częstotliwości zakupów.

        for (int i = 0; i < LICZBA_GRACZY; i++) {
            Gracz gracz = new GraczStałoBlankietowy(
                    "Martin",
                    "Luther King",
                    random.nextLong(RANDOM_PESEL_GRANICA),
                    centrala,
                    random.nextLong(RANDOM_śRODKI_GRANICA),
                    losujListęKolektur(kolektury),
                    losujBlankiet(centrala),
                    random.nextInt(RANDOM_CZĘSTOTLIWOŚĆ_ZAKUPÓW_GRANICA)
            );
            gracze.add(gracz);
        }

        // Tworzenie graczy stałobliczbowych o godności Grana Padano i losowym:
        // PESELu, środkach, liście Kolektur oraz ulubionych liczbach.

        for (int i = 0; i < LICZBA_GRACZY; i++) {
            Gracz gracz = new GraczStałoLiczbowy(
                    "Grana",
                    "Padano",
                    random.nextLong(RANDOM_PESEL_GRANICA),
                    centrala,
                    random.nextLong(RANDOM_śRODKI_GRANICA),
                    losujListęKolektur(kolektury),
                    centrala.losujLiczby()
            );
            gracze.add(gracz);
        }

    }


    // Funkcja zwracająca losowy Blankiet.

    private static Blankiet losujBlankiet(Centrala centrala) {

        Blankiet blankiet = new Blankiet();
        Random random = new Random();

        try {

            blankiet.wybierzLiczbęLosowań(
                    random.nextInt(Informacja.LICZBA_LOSOWAŃ_W_BLANKIECIE_SUP) + 1);
            for (int i = 0; i < Informacja.LICZBA_PÓL_W_BLANKIECIE; i++) {

                blankiet.wypełnijPole(i + 1, centrala.losujLiczby());
                int losowaLiczba1 = random.nextInt(SZANSA_NA_ANULOWANE_POLE) + 1;
                int losowaLiczba2 = random.nextInt(SZANSA_NA_ANULOWANE_POLE) + 1;
                if (losowaLiczba1 == losowaLiczba2) blankiet.anulujPole(i + 1);

            }

        } catch (Wyjątek wyjątek) {
            wyjątek.wypisz();
        }

        return blankiet;

    }


    // Funkcja zwracająca losowy podzbiór listy Kolektur.

    private static ArrayList<Kolektura> losujListęKolektur(
            List<Kolektura> kolektury) {

        // Tworzymy nową pustą listę oraz listę-kopię listy Kolektur.

        Random random = new Random();
        ArrayList<Kolektura> kolekturyKopia = new ArrayList<>(kolektury);
        ArrayList<Kolektura> noweKolektury = new ArrayList<>();

        // Losujemy liczbę kolektur w podzbiorze.

        for (int i = 0; i < random.nextInt(kolektury.size() - 1) + 1; i++) {

            // Losujemy numer na liście.

            int numer = random.nextInt(
                    random.nextInt(kolekturyKopia.size()) + 1);

            // Do nowej listy dodajemy Kolekturę o wylosowanym numerze
            // z listy-kopii po czym ją z niej usuwamy.

            noweKolektury.add(kolekturyKopia.get(numer));
            kolekturyKopia.remove(numer);

        }

        return noweKolektury;

    }


}
