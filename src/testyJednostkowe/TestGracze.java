package Totolotek.testyJednostkowe;

import Totolotek.totolotek.*;
import Totolotek.wyjątki.*;

import java.lang.reflect.*;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestGracze {


    // Stałe potrzebne do testów.

    private Centrala centrala;
    private Kolektura kolektura;


    // Inicjalizacja następująca przed każdym testem.

    @BeforeEach
    void przygotowanieDoTestu() {

        BudżetPaństwa budżetPaństwa = new BudżetPaństwa();
        centrala = new Centrala(budżetPaństwa);
        centrala.stwórzKolekturę();
        kolektura = centrala.dajInformację().dajKolektury().getFirst();

    }


    // Testy.

    // Metoda kupKupony.

    @Test
    @SuppressWarnings("unchecked")
    void testGraczMinimalistaKupKupony() throws Exception {

        // Tworzymy gracza minimalistę z wystarczającymi środkami.

        GraczMinimalista gracz = new GraczMinimalista(
                "Hannah",
                "Montana",
                12345678901L,
                centrala,
                1000,
                kolektura);

        // Wywołujemy metodę kupKupony.

        gracz.kupKupony();

        // Sprawdzamy, czy gracz kupił jeden kupon
        // z jednym zakładem na jedno losowanie.

        Field aktywneKuponyField =
                Gracz.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> aktywneKupony =
                (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(1, aktywneKupony.size());

        // Sprawdzamy, czy środki zostały pomniejszone
        // o 300 groszy (cena brutto kuponu).

        Field środkiFinansoweField =
                Gracz.class.getDeclaredField("środkiFinansowe");
        środkiFinansoweField.setAccessible(true);
        long środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertEquals(700, środkiFinansowe);

        // Sprawdzamy, czy kupon jest poprawnie zarejestrowany w kolekturze.

        Field aktywneKuponyKolekturaField =
                Kolektura.class.getDeclaredField("aktywneKupony");
        aktywneKuponyKolekturaField.setAccessible(true);
        HashSet<Kupon> kuponyWKolekturze =
                (HashSet<Kupon>) aktywneKuponyKolekturaField.get(kolektura);
        assertEquals(1, kuponyWKolekturze.size());

    }


    @Test
    @SuppressWarnings("unchecked")
    void testGraczLosowyKupKupony() throws Exception {

        // Tworzeymy gracza losowego.

        GraczLosowy gracz = new GraczLosowy(
                "Marek",
                "Fujarek",
                12345678901L,
                centrala);

        // Wywołujemy metodę kupKupony.

        gracz.kupKupony();

        // Sprawdzeamy, czy gracz kupił od 1 do 100 kuponów.

        Field aktywneKuponyField =
                Gracz.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> aktywneKupony =
                (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertTrue(!aktywneKupony.isEmpty() && aktywneKupony.size() <= 100);

        // Sprawdzamy, czy środki zostały odpowiednio pomniejszone.

        Field środkiFinansoweField =
                Gracz.class.getDeclaredField("środkiFinansowe");
        środkiFinansoweField.setAccessible(true);
        long środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertTrue(środkiFinansowe >= 0);

        // Sprawdzamy, czy kupony są zarejestrowane w kolekturze.

        Field aktywneKuponyKolekturaField =
                Kolektura.class.getDeclaredField("aktywneKupony");
        aktywneKuponyKolekturaField.setAccessible(true);
        HashSet<Kupon> kuponyWKolekturze =
                (HashSet<Kupon>) aktywneKuponyKolekturaField.get(kolektura);
        assertEquals(aktywneKupony.size(), kuponyWKolekturze.size());

    }


    @Test
    @SuppressWarnings("unchecked")
    void testGraczStałoBlankietowyKupKupony() throws Exception {

        // Tworzymy blankiet z jednym zakładem.

        Blankiet blankiet = new Blankiet();
        blankiet.wypełnijPole(1, new int[]{1, 2, 3, 4, 5, 6});
        blankiet.wybierzLiczbęLosowań(2);
        ArrayList<Kolektura> kolektury =
                new ArrayList<>(Collections.singletonList(kolektura));

        // Tworzymy gracza stałoblankietowego.

        GraczStałoBlankietowy gracz = new GraczStałoBlankietowy(
                "Martin",
                "Luther King",
                12345678901L,
                centrala,
                2000,
                kolektury,
                blankiet,
                2);

        // Wywołujemy metodę kupKupony.

        gracz.kupKupony();

        // Sprawdzamy, czy gracz kupił jeden kupon.

        Field aktywneKuponyField =
                Gracz.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> aktywneKupony =
                (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(1, aktywneKupony.size());

        // Sprawdzamy, czy środki zostały pomniejszone o 600 groszy.

        Field środkiFinansoweField =
                Gracz.class.getDeclaredField("środkiFinansowe");
        środkiFinansoweField.setAccessible(true);
        long środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertEquals(1400, środkiFinansowe);

        // Sprawdzamy, czy gracz nie kupi nowego kuponu
        // przed upływem wyznaczonej liczby losowań.

        centrala.losowanie();
        gracz.kupKupony();
        aktywneKupony = (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(1, aktywneKupony.size());

        // Po drugim losowaniu gracz powinien móc kupić nowy kupon.

        centrala.losowanie();
        gracz.kupKupony();
        aktywneKupony = (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(2, aktywneKupony.size());

    }


    @Test
    @SuppressWarnings("unchecked")
    void testGraczStałoLiczbowyKupKupony() throws Exception {

        // Tworzymy gracza stałoliczbowego.

        ArrayList<Kolektura> kolektury = new ArrayList<>(Collections.singletonList(kolektura));
        int[] liczby = {1, 2, 3, 4, 5, 6};
        GraczStałoLiczbowy gracz = new GraczStałoLiczbowy(
                "Grana",
                "Padano",
                12345678901L,
                centrala,
                100000,
                kolektury,
                liczby);

        // Wywołujemy metodę kupKupony.

        gracz.kupKupony();

        // Sprawdzamy, czy gracz kupił jeden kupon z 8 zakładami na 10 losowań.

        Field aktywneKuponyField =
                Gracz.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> aktywneKupony =
                (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(1, aktywneKupony.size());

        // Sprawdzamy, czy środki zostały pomniejszone o 24000 groszy.

        Field środkiFinansoweField =
                Gracz.class.getDeclaredField("środkiFinansowe");
        środkiFinansoweField.setAccessible(true);
        long środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertEquals(76000, środkiFinansowe);

        // Sprawdzamy, czy gracz nie kupi nowego
        // kuponu przed zakończeniem losowań.

        for (int i = 0; i < 9; i++) centrala.losowanie();
        gracz.kupKupony();
        aktywneKupony = (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(1, aktywneKupony.size());

        // Po 10 losowaniach gracz powinien kupić nowy kupon.

        centrala.losowanie();
        gracz.kupKupony();
        aktywneKupony = (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(2, aktywneKupony.size());

    }


    // Test realizacji gotowych kuponów.

    @Test
    @SuppressWarnings("unchecked")
    void testRealizacjiGotowychKuponów() throws Exception {

        // Tworzymy gracza minimalistę.

        GraczMinimalista gracz = new GraczMinimalista(
                "Hannah",
                "Montana",
                12345678901L,
                centrala,
                1000,
                kolektura);

        // Kupujemy kupon i przeprowadzamy losowanie.

        gracz.kupKupony();
        centrala.losowanie();

        // Sprawdzamy, czy gracz ma jeden kupon przed realizacją.

        Field aktywneKuponyField =
                Gracz.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> aktywneKupony =
                (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertEquals(1, aktywneKupony.size());

        // Realizujemy kupony.
        gracz.zrealizujGotoweKupony();

        // Sprawdzamy, czy kupon został usunięty.

        aktywneKupony = (HashSet<Kupon>) aktywneKuponyField.get(gracz);
        assertTrue(aktywneKupony.isEmpty());

        // Sprawdzamy, czy kupon nie został zapisany
        // w zbiorze zrealizowanych w kolekturze.

        Field zrealizowaneZnacząceKuponyField =
                Kolektura.class.getDeclaredField("zrealizowaneZnacząceKupony");
        zrealizowaneZnacząceKuponyField.setAccessible(true);
        HashSet<Kupon> zrealizowaneKupony =
                (HashSet<Kupon>) zrealizowaneZnacząceKuponyField.get(kolektura);
        assertTrue(zrealizowaneKupony.isEmpty());

    }

    // Test dodania i pobrania środków.

    @Test
    void testDodaniaPobraniaŚrodków() throws Exception {

        // Tworzymy gracza minimalistę.

        GraczMinimalista gracz = new GraczMinimalista(
                "Hannah",
                "Montana",
                12345678901L,
                centrala,
                100000,
                kolektura);


        // Dodanie środków.

        gracz.dodajŚrodki(50000);
        Field środkiFinansoweField =
                Gracz.class.getDeclaredField("środkiFinansowe");
        środkiFinansoweField.setAccessible(true);
        long środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertEquals(150000, środkiFinansowe);

        // Pobranie środków.

        boolean wynik = gracz.pobierzŚrodki(30000);
        assertTrue(wynik, "Pobranie 300 zł powinno się udać");
        środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertEquals(120000, środkiFinansowe);

        // Próba pobrania zbyt dużej kwoty.

        wynik = gracz.pobierzŚrodki(150000);
        assertFalse(wynik);
        środkiFinansowe = (long) środkiFinansoweField.get(gracz);
        assertEquals(120000, środkiFinansowe);

    }


    // Test wyjątku.

    @Test
    @SuppressWarnings("unchecked")
    void testBrakuŚrodków() throws Exception {

        // Tworzymy gracza minimalistę z niewystarczającymi środkami.

        GraczMinimalista gracz = new GraczMinimalista(
                "Hannah",
                "Montana",
                12345678901L,
                centrala,
                200,
                kolektura);

        // Sprawdzamy, czy próba zakupu kuponu rzuca wyjątek.

        assertThrows(WyjątekNielegalnejAktywności.class, gracz::kupKupony);

        // Sprawdzamy, czy gracz nie ma żadnych kuponów.

        Field aktywneKuponyField = Gracz.class.getDeclaredField("aktywneKupony");
        aktywneKuponyField.setAccessible(true);
        HashSet<Kupon> aktywneKupony = (HashSet<Kupon>) aktywneKuponyField.get(gracz);

        assertTrue(aktywneKupony.isEmpty());

    }


}


