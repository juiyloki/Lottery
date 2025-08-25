package Totolotek.testyJednostkowe;

import Totolotek.totolotek.*;

import java.lang.reflect.*;
import java.util.*;

import Totolotek.wyjątki.WyjątekNielegalnejAktywności;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestSystem {

    // Stałe potrzebne do każdego testu.

    private Centrala centrala;
    private GraczMinimalista gracz;
    private Kolektura kolektura;
    private BudżetPaństwa budżetPaństwa;

    // Inicjalizacja stałych wykonywana przed każdym testem.

    @BeforeEach
    void przygotowanie() throws Exception {

        budżetPaństwa = new BudżetPaństwa();
        centrala = new Centrala(budżetPaństwa);
        centrala.stwórzKolekturę();
        kolektura = centrala.dajInformację().dajKolektury().getFirst();
        gracz = new GraczMinimalista(
                "Hannah",
                "Montana",
                12345678901L,
                centrala,
                100000,
                kolektura);

        // Przygotowanie konstruktora Zakład dla testów.

        Constructor<Zakład> konstruktorZakład =
                Zakład.class.getDeclaredConstructor(int[].class);
        konstruktorZakład.setAccessible(true);

    }

    @Test
    @SuppressWarnings("unchecked")
    void testLosowanie() throws Exception {

        // Wykonujemy losowania.

        centrala.losowanie();

        // Sprawdzamy listę losowań w centrali.

        Field losowaniaField = Centrala.class.getDeclaredField("losowania");
        losowaniaField.setAccessible(true);
        ArrayList<Losowanie> losowania =
                (ArrayList<Losowanie>) losowaniaField.get(centrala);
        assertEquals(1, losowania.size());

        // Weryfikujemy numer losowania i wylosowane liczby.

        Losowanie losowanie = losowania.getFirst();
        assertEquals(1, losowanie.dajNumer(),
                "Numer losowania powinien być 1");
        assertEquals(6, losowanie.dajLiczby().length);

        // Sprawdzamy, czy liczby są w przedziale 1-49 i posortowane.

        int[] liczby = losowanie.dajLiczby();
        for (int liczba : liczby) {
            assertTrue(liczba >= 1 && liczba <= 49);
        }

        for (int i = 1; i < liczby.length; i++) {
            assertTrue(liczby[i] > liczby[i - 1]);
        }

    }


    @Test
    @SuppressWarnings("unchecked")
    void testKwotWygranych() throws Exception {

        // Kupujemy kupon i przeprowadzamy losowanie.

        gracz.kupKupony();
        centrala.losowanie();

        // Pobieramy losowanie.

        Field losowaniaField = Centrala.class.getDeclaredField("losowania");
        losowaniaField.setAccessible(true);
        ArrayList<Losowanie> losowania =
                (ArrayList<Losowanie>) losowaniaField.get(centrala);
        Losowanie losowanie = losowania.getFirst();

        // Ustawiamy zwycięskie zakłady dla testu.

        Field zwycięskieZakładyField =
                Losowanie.class.getDeclaredField("zwycięskieZakłady");
        zwycięskieZakładyField.setAccessible(true);
        int[] zwycięskieZakłady = (int[]) zwycięskieZakładyField.get(losowanie);
        zwycięskieZakłady[4] = 1; // Jeden zakład z nagrodą IV stopnia

        // Wywołujemy metodę obliczPuleNagród.

        Method obliczPuleNagród =
                Centrala.class.getDeclaredMethod(
                        "obliczPuleNagród", Losowanie.class);
        obliczPuleNagród.setAccessible(true);
        Method ustawPuleTest =
                Losowanie.class.getDeclaredMethod(
                        "ustawPule", long[].class);
        ustawPuleTest.setAccessible(true);

        long[] pule = (long[]) obliczPuleNagród.invoke(centrala, losowanie);
        ustawPuleTest.invoke(losowanie, new Object[]{pule});

        // Wywołujemy metodę obliczKwotyWygranych.

        Method obliczKwotyWygranych =
                Centrala.class.getDeclaredMethod(
                        "obliczKwotyWygranych", Losowanie.class, long[].class);
        obliczKwotyWygranych.setAccessible(true);
        long[] kwoty = (long[])
                obliczKwotyWygranych.invoke(centrala, losowanie, pule);

        // Weryfikujemy kwoty wygranych.

        assertEquals(Informacja.NAGRODA_4_STOPNIA, kwoty[4]);
        assertEquals(0, kwoty[1]);
        assertEquals(0, kwoty[2]);
        assertEquals(0, kwoty[3]);

    }


    @Test
    void testPuliNagród() throws Exception {

        // Przygotowanie metod.

        Constructor<Losowanie> konstruktorLosowanie =
                Losowanie.class.getDeclaredConstructor(
                        int.class, Centrala.class);
        konstruktorLosowanie.setAccessible(true);

        Losowanie losowanie = konstruktorLosowanie.newInstance(1,centrala);

        // Symulacja dochodu 10 mln zł netto.

        Method dodajDochódTest =
                losowanie.getClass().getDeclaredMethod(
                        "dodajDochód", long.class);
        dodajDochódTest.setAccessible(true);
        dodajDochódTest.invoke(losowanie, 1000000000L);

        Method dajZwycięskieZakładyTest =
                losowanie.getClass().getDeclaredMethod("dajZwycięskieZakłady");
        dajZwycięskieZakładyTest.setAccessible(true);
        int[] zwycięskieZakłady =
                (int[]) dajZwycięskieZakładyTest.invoke(losowanie);

        // 100 zwycięzców III stopnia.

        zwycięskieZakłady[3] = 100;

        // 1000 zwycięzców IV stopnia.

        zwycięskieZakłady[4] = 1000;

        Method obliczPuleNagródTest =
                centrala.getClass().getDeclaredMethod(
                        "obliczPuleNagród", Losowanie.class);
        obliczPuleNagródTest.setAccessible(true);

        long[] pule = (long[]) obliczPuleNagródTest.invoke(centrala, losowanie);

        // Liczymy pule nagród.

        assertEquals(224400000, pule[1]);   // 44% * 51% * 10 mln
        assertEquals(40800000, pule[2]);    // 8% * 51% * 10 mln
        assertEquals(2400000, pule[4]);     // 2400 * 100
        assertEquals(242400000, pule[3]);   // max(pozostała kwota, 100*15*240

    }

    @Test
    @SuppressWarnings("unchecked")
    void testSprzedażyKuponu() throws Exception {

        // Gracz kupuje kupon.

        gracz.kupKupony();

        // Sprawdzamy, czy kupon jest zarejestrowany w kolekturze.

        Field aktywneKuponyField =
                Kolektura.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> kupony =
                (HashSet<Kupon>) aktywneKuponyField.get(kolektura);
        assertEquals(1, kupony.size());

        // Sprawdzamy, czy środki trafiły do centrali.

        Field środkiFinansoweField =
                Centrala.class.getDeclaredField("środkiFinansowe");
        środkiFinansoweField.setAccessible(true);
        long środkiFinansowe = (long) środkiFinansoweField.get(centrala);
        assertEquals(240, środkiFinansowe);

        // Sprawdzamy, czy podatek trafił do budżetu.

        Field pobranePodatkiField =
                BudżetPaństwa.class.getDeclaredField("pobranePodatki");
        pobranePodatkiField.setAccessible(true);
        long pobranePodatki = (long) pobranePodatkiField.get(budżetPaństwa);
        assertEquals(60, pobranePodatki);

    }

    @Test
    void testPróbyOszustwa() throws Exception {

        Constructor<Zakład> konstruktorZakład =
                Zakład.class.getDeclaredConstructor(int[].class);
        konstruktorZakład.setAccessible(true);

        // Tworzymy nieautoryzowany kupon.

        Constructor<Kupon> konstruktorKupon =
                Kupon.class.getDeclaredConstructor(
                Kolektura.class, Centrala.class, int.class, Zakład[].class);
        konstruktorKupon.setAccessible(true);
        Kupon kupon = konstruktorKupon.newInstance(
                kolektura, centrala, 1,
                new Zakład[]{konstruktorZakład.newInstance(
                        new Object[]{new int[]{1, 2, 3, 4, 5, 6}})});

        // Próba realizacji nieautoryzowanego kuponu.

        assertThrows(WyjątekNielegalnejAktywności.class,
                () -> kolektura.zrealizujKupon(gracz, kupon));
    }

}
