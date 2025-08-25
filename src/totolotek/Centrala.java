package Totolotek.totolotek;

import java.util.*;

public class Centrala {


    // Atrybuty Centrali Totolotka.

    private long środkiFinansowe;
    private long kumulacja;

    private final ArrayList<Losowanie> losowania;
    private final List<Kolektura> kolektury;

    private final BudżetPaństwa budżetPaństwa;
    private final Informacja informacja;

    private int najwyższyNumerKuponu;
    private int najwyższeLosowanie;


    // Konstruktor.

    public Centrala(BudżetPaństwa budżet_państwa) {

        this.kumulacja = 0;
        this.środkiFinansowe = 0;

        this.kolektury = new ArrayList<>();
        this.losowania = new ArrayList<>();

        this.informacja = new Informacja(this);
        this.budżetPaństwa = budżet_państwa;

        this.najwyższyNumerKuponu = 0;
        this.najwyższeLosowanie = 0;

    }


    // Metoda przeprowadzania losowania.

    public void losowanie() {

        // Tworzymy nowe losowanie i dodajemy je do listy.

        Losowanie losowanie = new Losowanie(++najwyższeLosowanie, this);
        losowania.add(losowanie);

        // Wywołujemy metodę losowania w Kolekturach,
        // czyli liczymy zwycięskie zakłady oraz dochód z tego losowania.

        for (Kolektura kolektura : kolektury) {
            kolektura.losowanie(losowanie);
        }

        // Na podstawie zdobytych informacji (zapisanych w obiekcie losowanie)
        // liczymy pule nagród oraz wygrane kwoty.

        // Wywołujemy metodę liczącą pule nagród.

        long[] puleNagród = obliczPuleNagród(losowanie);

        // Dodajemy ewentualną kumulację lub zwiększamy jej wartość.

        if (kumulacja != 0) puleNagród[1] += kumulacja;
        if (losowanie.dajZwycięskieZakłady()[1] == 0)
            kumulacja = puleNagród[1];

        // Ustawiamy pule nagród oraz liczymy kwoty wygranych.

        losowanie.ustawPule(puleNagród);
        losowanie.ustawKwoty(obliczKwotyWygranych(losowanie, puleNagród));

        // Przekazujemy informację o odbytym losowaniu do Informacji.

        informacja.losowanie();

    }


    // Metoda obliczająca kwoty wygranych w danym losowaniu.

    private long[] obliczKwotyWygranych(
            Losowanie losowanie, long[] puleNagród) {

        // Pobuiermay infromację o ilości zwycięskich zakładów
        // z losowania i zapisujemy referencję.

        int[] zwycięskieZakłady = losowanie.dajZwycięskieZakłady();

        // Tworzymy nową tablicę kwot wygranych.

        long[] kwotyWygranych = new long[5];
        kwotyWygranych[0] = 0;

        // Kwoty wygranych liczymy dzieląc daną pulę
        // przez ilość zwycięskich zakładów.
        // W przypadku braku zwycięskich zakładów w danej kategorii,
        // ustawiamy kwotę wygranej na 0.

        for (int i = 1; i < 5; i++) {
            if (zwycięskieZakłady[i] != 0)
                kwotyWygranych[i] = puleNagród[i] / zwycięskieZakłady[i];
            else kwotyWygranych[i] = 0L;
        }

        // Zwracamy metodzie losowanie obliczone kwoty wygranych.

        return kwotyWygranych;

    }


    // Metoda obliczająca pule nagród w danym losowaniu.

    private long[] obliczPuleNagród(Losowanie losowanie) {

        // Liczymy kwotę przeznaczoną na nagrody.

        long nagrody = (long) (
                Informacja.PROCENT_DOCHODU_LOSOWANIA_PRZEZNACZONY_NA_NAGRODY
                        * losowanie.dajDochód()
        );
        long[] puleNagród = new long[5];

        // Uzupełniamy tablicę puli nagród.
        // Indeksy odpowiadają stopniowi wygranej.

        puleNagród[0] = 0;
        
        // Liczymy część kwoty z nagród przeznaczonej na nagrody 1 stopnia.
        
        long częśćZPuliNaPierwszyStopień = (long) (
                Informacja.PROCENT_NAGRÓD_PRZEZNACZONY_NA_1_STOPIEŃ 
                        * nagrody
        );

        // Pula nagród na 1 stopień to maksimum
        // z przeznaczonego procenta nagród i gwarantowanej kwoty.

        puleNagród[1] = Math.max(
                częśćZPuliNaPierwszyStopień, 
                Informacja.GWARANTOWANA_PULA_1_STOPNIA
        );

        // Pula nagród na 2 stopień to ustalony procent kwoty nagród.
        // Odejmujemy od kwoty nagród pule I i II stopnia.

        puleNagród[2] = (long) (
                Informacja.PROCENT_RESZTY_NAGRÓD_PRZEZNACZONY_NA_2_STOPIEŃ
                        * nagrody
        );

        nagrody -= częśćZPuliNaPierwszyStopień;
        nagrody -= puleNagród[2];

        // Kwota nagród 4 stopnia jest ustalona, więc pula
        // to iloczyn jej i ilości zwycięskich zakładów.
        
        puleNagród[4] = Informacja.NAGRODA_4_STOPNIA
                * losowanie.dajZwycięskieZakłady()[4];
        nagrody -= puleNagród[4];

        // Pula nagród 3 stopnia to maksimum
        // z pozostałej kwota nagród i gwarantowanej stawki.
        
        puleNagród[3] = Math.max(
                nagrody,
                Informacja.USTALONY_MNOŻNIK_DLA_NAGRÓD_3_STOPNIA
                        * Informacja.CENA_ZAKŁADU_NETTO
                        * losowanie.dajZwycięskieZakłady()[3]
        );

        // Po obliczeniu pul zwracamy tablicę do metody losowania.
        
        return puleNagród;

    }


    // Funkcja losująca ustaloną ilość liczb.

    int[] losujLiczby() {

        Random random = new Random();

        // Tworzymy i wypełniamy tablicę liczbami biorących udział w grze.

        int[] tablica = new int[Informacja.GRANICA_LICZB_W_GRZE];
        for (int i = 0; i < Informacja.GRANICA_LICZB_W_GRZE; i++) {
            tablica[i] = i + 1;
        }

        // Tasujemy stworzoną tablicę poprzez zamienianie losowych pozycji n razy.

        for (int i = 0; i < Informacja.GRANICA_LICZB_W_GRZE; i++) {

            int losowyIneks = random.nextInt(Informacja.GRANICA_LICZB_W_GRZE);

            int bufor = tablica[i];
            tablica[i] = tablica[losowyIneks];
            tablica[losowyIneks] = bufor;

        }

        // Zwracamy posortowane 6 pierwszych liczb.

        int[] wynik = Arrays.copyOf(tablica,Informacja.LICZBY_W_ZAKŁADZIE);
        Arrays.sort(wynik);

        return wynik;

    }


    // Metoda tworząca nową Kolekturę.

    public void stwórzKolekturę() {

        Kolektura kolektura = new Kolektura(
                this, kolektury.size() + 1);
        kolektury.add(kolektura);

    }


    // Metoda udostępniająca refernecję do Informacji Centrali Totolotka.

    public Informacja dajInformację() {
        return informacja;
    }


    // Metoda udostępniająca referencję
    // (ograniczoną do czytania) do listy Kolektur.

    List<Kolektura> dajKolektury() {
        return Collections.unmodifiableList(kolektury);
    }


    // Metoda umożliwiająca wypłacenie graczowi nagrody za zwycięski zakład.
    // W przypadku braku środków pobierana jest subwencja z Budżetu Państwa.

    void wypłaćNagrodę(long kwota) {

        if (środkiFinansowe >= kwota) środkiFinansowe -= kwota;
        else {
            budżetPaństwa.przekazanieSubwencji(kwota - środkiFinansowe);
            środkiFinansowe = 0;
        }

    }


    // Metoda przekazywania Centrali przez Kolekturę
    // przychodu za sprzedany Kupon.
    // Przychód dzielony jest na dochód i podatek.

    public void przychódZaKupon(Kupon kupon) {

        int podatek = kupon.dajOdprowadzonyPodatek();
        int cena = kupon.dajCenę();

        odprowadźPodatek(podatek);
        środkiFinansowe += (cena - podatek);

    }


    // Metoda umożliwiająca odprowadzenie podatku do Budżetu Państwa.

    void odprowadźPodatek(long kwota) {
        budżetPaństwa.pobraniePodatku(kwota);
    }


    // Metoda udostępniająca odpowiedni numer porządkowy tworzonego Kuponu.

    int nowyKupon() {
        return ++najwyższyNumerKuponu;
    }


    // Metoda drukująca podsumowanie działalności Centrali.

    public void drukujWszystko() {
        for (Losowanie losowanie : losowania) {
            losowanie.drukujWszystko();
        }
        System.out.println("Środki finansowe: " +
                środkiFinansowe / 100 + " zł " +
                środkiFinansowe % 100 + " gr");
    }


}
