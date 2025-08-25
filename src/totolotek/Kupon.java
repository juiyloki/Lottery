package Totolotek.totolotek;

import java.util.*;

public class Kupon {


    // Atrybuty Kuponu.

    private int numerPorządkowy;
    private String identyfikator;

    private final Centrala centrala;
    private final Kolektura kolektura;

    private final int liczbaZakładów;
    private final int liczbaLosowań;
    private final int pierwszeLosowanie;

    private final int cena;
    private final int odprowadzonyPodatek;

    private final Zakład[] zakłady;
    private final List<Losowanie> wygraneLosowania;


    // Konstruktor.

    public Kupon(
            Kolektura kolektura,
            Centrala centrala,
            int liczbaLosowań,
            Zakład[] zakłady
    ) {

        this.numerPorządkowy = -1;
        this.identyfikator = null;

        this.centrala = centrala;
        this.kolektura = kolektura;

        this.liczbaZakładów = zakłady.length;
        this.liczbaLosowań = liczbaLosowań;
        this.pierwszeLosowanie =
                centrala.dajInformację().dajNajbliższeLosowanie();

        this.cena =
                Informacja.CENA_ZAKŁADU_BRUTTO * liczbaZakładów * liczbaLosowań;
        this.odprowadzonyPodatek =
                Informacja.PODATEK_OD_ZAKŁADU * liczbaZakładów * liczbaLosowań;

        this.zakłady = zakłady;
        this.wygraneLosowania = new ArrayList<>();

    }


    // Metoda umożliwiająca autoryzację Kuponu.

    void autoryzuj() {

        this.numerPorządkowy = centrala.nowyKupon();
        this.identyfikator = wygenerujIdentyfikator(
                numerPorządkowy, kolektura.dajNumer());

    }


    // Metoda generująca identyfikator Kuuponu.

    private String wygenerujIdentyfikator(long numerKuponu,int numerKolektury) {

        int sumaCyfr = 0;
        StringBuilder s = new StringBuilder();

        s.append(numerKuponu);
        s.append("-");
        sumaCyfr += String.valueOf(
                numerKuponu).chars().map(ch -> ch - '0').sum();

        s.append(numerKolektury);
        s.append("-");
        sumaCyfr += String.valueOf(
                numerKolektury).chars().map(ch -> ch - '0').sum();

        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            int cyfra = random.nextInt(10);
            s.append(cyfra);
            sumaCyfr += cyfra;
        }
        s.append("-");

        s.append(sumaCyfr % 100);

        return s.toString();

    }


    // Metoda dodająca Losowanie do listy wygranych.

    void wygraneLosowanie(Losowanie losowanie) {
        if (!wygraneLosowania.contains(losowanie))
            wygraneLosowania.add(losowanie);
    }
    
    
    // Metoda zwracająca identyfikator.

    String dajIdentyfikator() {
        return identyfikator;
    }
    
    
    // Metoda zwracająca Kolekturę.

    Kolektura dajKolekturę() {
        return kolektura;
    }
    
    
    // Metoda zwracająca wygrane losowania.

    List<Losowanie> dajWygraneLosowania() {
        return wygraneLosowania;
    }
    
    
    // Metoda zwracająca ostatnie losowanie Kuponu.

    int dajOstatnieLosowanie() {
        return pierwszeLosowanie + liczbaLosowań - 1;
    }


    // Metoda zwracająca liczbę losowań.

    int dajLiczbęLosowań() {
        return liczbaLosowań;
    }
    
    
    // Metoda zwracająca wartośc logiczną odpowiadającą temu,
    // czy Kupon bierze udział w danym losowaniu.

    boolean czyTwojeLosowanie(int numer) {
        return numer >= pierwszeLosowanie && 
                numer <= pierwszeLosowanie + liczbaLosowań - 1;
    }
    
    
    // Metoda zwracająca wartość logiczną prawdziwą wtedy i tylko wtedy,
    // gdy Kupon jest zapisany na Losowanie, które jeszcze się nie odbyło.

    boolean czyAktywneLosowania() {
        return dajOstatnieLosowanie() > 
                centrala.dajInformację().dajOstatnieLosowanie();
    }
    
    
    // Metoda zwracająca Zakłady Kuponu.

    Zakład[] dajZakłady() {
        return zakłady;
    }
    
    
    // Metoda zwracająca cenę Kuponu.

    int dajCenę() {
        return cena;
    }


    // Metoda zwracająca wartośc podatu odprowadzonego przy zakupie Kuponu.

    public int dajOdprowadzonyPodatek() {
        return odprowadzonyPodatek;
    }


    // Metoda zwracająca dochód Kuponu na każde Losowanie.

    int dajDochódNaLosowanie() {
        return (cena - odprowadzonyPodatek) / liczbaLosowań;
    }


    // Metoda equals().

    @Override
    public boolean equals(Object objekt) {
        if (!(objekt instanceof Kupon kupon)) return false;
        return kupon.dajIdentyfikator().equals(this.identyfikator);
    }


    // Metoda hashCode().

    @Override
    public int hashCode() {
        return identyfikator != null ? identyfikator.hashCode() : 0;
    }


    // Metoda toString().

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("KUPON NR ").append(identyfikator);

        for (int i = 0; i < liczbaZakładów; i++) {
            s.append("\n").append(i).append(": ").append(zakłady[i]);
        }

        s.append("\nLICZBA LOSOWAŃ: ").append(liczbaLosowań);
        s.append("\nNNUMERY LOSOWAŃ:\n");
        for (int i = 0; i < liczbaLosowań; i++) {
            if (pierwszeLosowanie + i < 10) s.append(" ");
            s.append((pierwszeLosowanie + i)).append(" ");
        }

        s.append("\nCENA: ");
        s.append(cena / 100).append(" zł ").append(cena % 100).append(" gr\n");

        return s.toString();

    }



}
