package Totolotek.totolotek;

import Totolotek.wyjątki.*;

import static java.lang.Math.*;

public class Blankiet {


    // Atrybuty Blankietu.

    private final PoleBlankietu[] pola;
    private int liczbaLosowań;


    // Konstruktor.

    public Blankiet() {

        this.liczbaLosowań = 1;
        this.pola = new PoleBlankietu[Informacja.LICZBA_PÓL_W_BLANKIECIE];

        // Inicjalizujemy pola.

        for (int i = 0; i < Informacja.LICZBA_PÓL_W_BLANKIECIE; i++) {
            this.pola[i] = new PoleBlankietu();
        }

    }


    // Metoda umożliwiająca wypełnienie pola Blankietu.

    public void wypełnijPole(int numer, int[] liczby) throws Wyjątek{

        // Sprawdzamy, czy pole istnieje i czy nie jest przypadkiem wypełnione.
        
        if (numer < 1 || numer > Informacja.LICZBA_PÓL_W_BLANKIECIE) throw new
                WyjątekNiepoprawnegoArgumentu(
                        "Wybierz pole o numerze od 1 do 8 włącznie.");
        if (this.pola[numer - 1].czyWypełnione()) throw new
                WyjątekNielegalnejAktywności("Pole zostało już wypełnione.");

        // Wypełniamy pole.
        
        this.pola[numer - 1].wypełnij(liczby);

    }


    // Metoda umożliwiająca anulowanie pola.

    public void anulujPole(int numer) throws Wyjątek {

        if (numer > Informacja.LICZBA_PÓL_W_BLANKIECIE)
            throw new WyjątekNiepoprawnegoArgumentu(
                    "Blankiet nie posiada takiego pola.");

        if (numer < 1) throw new WyjątekNiepoprawnegoArgumentu(
                    "Pole powinno byc dodatnie");
        
        this.pola[numer - 1].anuluj();
        
    }


    // Metody umożliwiające zakreślenie porządanej liczby losowań.

    public void wybierzLiczbęLosowań(int liczbaLosowań)
            throws WyjątekNiepoprawnegoArgumentu {

        // Sprawdzamy poprawność argumentów.

        if (liczbaLosowań > Informacja.LICZBA_LOSOWAŃ_W_BLANKIECIE_SUP)
            throw new WyjątekNiepoprawnegoArgumentu(
                    "Liczba losowań powinna być mniejsza od 10.");
        else if (liczbaLosowań < Informacja.LICZBA_LOSOWAŃ_W_BLANKIECIE_INF)
            throw new WyjątekNiepoprawnegoArgumentu(
                    "Liczba losowań powinna być dodatnia.");

            // Jeśli zaznaczono liczbę losowań kilkukrotnie,
            // to zapisana zostanie tylko największa z poprawnych wartości.

        else this.liczbaLosowań = max(this.liczbaLosowań, liczbaLosowań);

    }


    // Metoda udostępniająca liczbę losowań.

    int dajLiczbęLosowań() {
        return this.liczbaLosowań;
    }


    // Metoda udostępniająca poprawnie zaznaczone zakłady.

    Zakład[] dajZakłady() {

        // Liczymy ilość poprawnie zaznaczonych pól.

        int ważnePola = 0;
        for (int i = 0; i < Informacja.LICZBA_PÓL_W_BLANKIECIE; i++) {
            if (this.pola[i].czyWażne()) ważnePola++;
        }

        // Tworzymy nową tablicę o odpowiednim rozmiarze.

        Zakład[] zakłady = new Zakład[ważnePola];

        // Wypełniamy nową tablicę zakładami.

        int j = 0;
        for (int i = 0; i < ważnePola; i++) {
            if (this.pola[i].czyWażne()) {
                zakłady[j++] = new Zakład(this.pola[i].odczytaj());
            }
        }

        // Zwracamy nową tablicę.

        return zakłady;

    }


}
