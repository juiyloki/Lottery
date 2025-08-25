package Totolotek.testyJednostkowe;

import Totolotek.totolotek.*;
import Totolotek.wyjątki.*;

import java.lang.reflect.*;
import java.util.*;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestKuponyBlankiety {


    @Test
    public void testWypełnianiaBlankietu() throws Exception {

        Blankiet blankiet = new Blankiet();

        Method dajZakładyTest =
                blankiet.getClass().getDeclaredMethod("dajZakłady");
        dajZakładyTest.setAccessible(true);

        // Poprawne pole z 6 liczbami.

        int[] liczby = {1, 2, 3, 4, 5, 6};
        blankiet.wypełnijPole(1, liczby);
        Zakład[] zakłady = (Zakład[]) dajZakładyTest.invoke(blankiet);
        assertEquals(1, zakłady.length);

        Method dajLiczbyTest =
                zakłady[0].getClass().getDeclaredMethod("dajLiczby");
        dajLiczbyTest.setAccessible(true);

        assertArrayEquals(liczby, (int[]) dajLiczbyTest.invoke(zakłady[0]));

        // Niepoprawne pole z 5 liczbami.

        int[] złeLiczby = {1, 2, 3, 4, 5};
        blankiet.wypełnijPole(2, złeLiczby);
        assertEquals(1, ((Zakład[])dajZakładyTest.invoke(blankiet)).length);

        // Anulowanie pola.

        blankiet.wypełnijPole(3, liczby);
        blankiet.anulujPole(2);
        assertEquals(2, ((Zakład[])dajZakładyTest.invoke(blankiet)).length);

    }


    @Test
    void testLiczbyLosowańBlankietu() throws Exception {

        Blankiet blankiet = new Blankiet();

        Method dajLiczbęLosowańTest =
                blankiet.getClass().getDeclaredMethod("dajLiczbęLosowań");
        dajLiczbęLosowańTest.setAccessible(true);

        // Domyślna liczba losowań.

        assertEquals(1, dajLiczbęLosowańTest.invoke(blankiet));

        // Wybór liczby losowań.

        blankiet.wybierzLiczbęLosowań(5);
        assertEquals(5, dajLiczbęLosowańTest.invoke(blankiet));

        // Wybór większej liczby losowań.

        blankiet.wybierzLiczbęLosowań(8);
        assertEquals(8, dajLiczbęLosowańTest.invoke(blankiet));

        // Próba ustawienia niepoprawnej liczby losowań.

        assertThrows(WyjątekNiepoprawnegoArgumentu.class,
                () -> blankiet.wybierzLiczbęLosowań(11));
        assertThrows(WyjątekNiepoprawnegoArgumentu.class,
                () -> blankiet.wybierzLiczbęLosowań(0));

    }


    @Test
    void testOgraniczeniaBlankietu() throws Exception {

        // Przygotowanie blankietu i metod.

        Blankiet blankiet = new Blankiet();

        Constructor<Zakład> konstruktorZakład =
                Zakład.class.getDeclaredConstructor(int[].class);
        konstruktorZakład.setAccessible(true);

        Method dajZakładyTest =
                blankiet.getClass().getDeclaredMethod("dajZakłady");
        dajZakładyTest.setAccessible(true);
        Method dajLiczbęLosowańTest =
                blankiet.getClass().getDeclaredMethod("dajLiczbęLosowań");
        dajLiczbęLosowańTest.setAccessible(true);

        // Poprawny blankiet z 3 polami.

        int[] liczby = {1, 2, 3, 4, 5, 6};
        for (int i = 1; i <= 3; i++) {
            blankiet.wypełnijPole(i, liczby);
        }
        blankiet.wybierzLiczbęLosowań(5);

        Zakład[] zakłady = (Zakład[]) dajZakładyTest.invoke(blankiet);
        assertEquals(3, zakłady.length,
                "Blankiet powinien mieć 3 ważne pola");
        assertEquals(5, dajLiczbęLosowańTest.invoke(blankiet),
                "Blankiet powinien mieć 5 losowań");

        // Próba wypełnienia zbyt wielu pól (9).

        assertThrows(WyjątekNiepoprawnegoArgumentu.class,
                () -> blankiet.wypełnijPole(9, liczby),
                "Wypełnienie pola 9 powinno rzucić wyjątek");

        // Próba ustawienia zbyt dużej liczby losowań (11).

        assertThrows(WyjątekNiepoprawnegoArgumentu.class,
                () -> blankiet.wybierzLiczbęLosowań(11),
                "Ustawienie 11 losowań powinno rzucić wyjątek");

        // Próba ustawienia niepoprawnej liczby losowań (0).

        assertThrows(WyjątekNiepoprawnegoArgumentu.class,
                () -> blankiet.wybierzLiczbęLosowań(0),
                "Ustawienie 0 losowań powinno rzucić wyjątek");


    }


    @Test
    @SuppressWarnings("unchecked")
    void testPoprawnościIdentyfikatoraKuponu() throws Exception{

        // Przygotowanie Kuponu i metod.

        BudżetPaństwa budżet = new BudżetPaństwa();
        Centrala centrala = new Centrala(budżet);
        centrala.stwórzKolekturę();

        Method dajKolekturyTest =
                centrala.getClass().getDeclaredMethod("dajKolektury");
        dajKolekturyTest.setAccessible(true);

        Kolektura kolektura = ((List<Kolektura>)
                dajKolekturyTest.invoke(centrala)).getFirst();

        Constructor<Zakład> konstruktorZakład =
                Zakład.class.getDeclaredConstructor(int[].class);
        konstruktorZakład.setAccessible(true);

        Zakład[] zakłady =
                {konstruktorZakład.newInstance(
                        new Object[]{new int[]{1, 2, 3, 4, 5, 6}})};
        Kupon kupon = new Kupon(kolektura, centrala, 1, zakłady);

        // Autoryzacja i dzielenie identyfikatora na części.

        Method autoryzujTest = kupon.getClass().getDeclaredMethod("autoryzuj");
        autoryzujTest.setAccessible(true);
        Method dajIdentyfikatorTest =
                kupon.getClass().getDeclaredMethod("dajIdentyfikator");
        dajIdentyfikatorTest.setAccessible(true);

        autoryzujTest.invoke(kupon);
        String id = (String) dajIdentyfikatorTest.invoke(kupon);
        String[] parts = id.split("-");

        // Sprawdzenie poprawności poszczególnych części.

        assertEquals(4, parts.length);
        assertEquals("1", parts[0]);
        assertEquals("1", parts[1]);
        assertEquals(9, parts[2].length());
        assertTrue(Integer.parseInt(parts[3]) <= 99);

    }



    @Test
    @SuppressWarnings("unchecked")
    void testCenyPodatkuKuponu() throws Exception {

        // Przygotowwanie Kuponu i metod.

        BudżetPaństwa budżet = new BudżetPaństwa();
        Centrala centrala = new Centrala(budżet);
        centrala.stwórzKolekturę();

        Method dajKolekturyTest =
                centrala.getClass().getDeclaredMethod("dajKolektury");
        dajKolekturyTest.setAccessible(true);

        Kolektura kolektura = ((List<Kolektura>)
                dajKolekturyTest.invoke(centrala)).getFirst();

        Constructor<Zakład> konstruktorZakład =
                Zakład.class.getDeclaredConstructor(int[].class);
        konstruktorZakład.setAccessible(true);

        // Tworzenie Kuponu.

        Zakład[] zakłady = {
                konstruktorZakład.newInstance( new Object[]{
                        new int[]{1, 2, 3, 4, 5, 6}}),
                konstruktorZakład.newInstance((new Object[]{
                        new int[]{7, 8, 9, 10, 11, 12}}))
        };
        Kupon kupon = new Kupon(kolektura, centrala, 2, zakłady);

        // Przygotowanie metod do testu.

        Method dajCenęTest = kupon.getClass().getDeclaredMethod("dajCenę");
        dajCenęTest.setAccessible(true);
        Method dajOdprowadzonyPodatekTest =
                kupon.getClass().getDeclaredMethod("dajOdprowadzonyPodatek");
        dajOdprowadzonyPodatekTest.setAccessible(true);

        // Testy.

        // 2 zakłady * 2 losowania * 3 zł

        assertEquals(1200, dajCenęTest.invoke(kupon));

        // 2 zakłady * 2 losowania * 0,60 zł

        assertEquals(240, dajOdprowadzonyPodatekTest.invoke(kupon));


    }


}
