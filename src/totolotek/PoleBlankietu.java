package Totolotek.totolotek;

import java.util.*;

public class PoleBlankietu {


    // Atrybuty Pola Blankietu.

    private int[] zaznaczoneLiczby;
    private boolean czyWypełnione;
    private boolean czyWażne;
    private boolean czyAnulowane;


    // Konstruktor.

    PoleBlankietu() {
        this.zaznaczoneLiczby = null;
        this.czyWypełnione = false;
        this.czyWażne = false;
        this.czyAnulowane = false;
    }


    // Upraszczamy tablicę poprzez psoortowanie i usunięcie powtórek.

    static int[] uprośćTablicę(int[] tablica) {

        if (tablica == null) return null;

        // Sortujemy i liczymy wielkość przyszłej tablicy

        Arrays.sort(tablica);
        int różneWartości = 1;
        for (int i = 1; i < tablica.length; i++) {
            if (tablica[i] != tablica[i - 1]) różneWartości++;
        }

        // Jeśli nie ma powtórek to zwracamy tablicę.

        if (różneWartości == tablica.length) return tablica;

        // W przeciwnym wypadku tworzymy nową tablicę i iterator dla niej.

        int[] nowaTablica = new int[różneWartości];
        int j = 0;
        nowaTablica[j++] = tablica[0];

        // Wypełniamy tablicę.

        for (int i = 1; i < tablica.length; i++) {
            if (tablica[i] != tablica[i-1]) {
                nowaTablica[j++] = tablica[i];
            }
        }

        return nowaTablica;

    }


    // Metoda umożliwiająca wypełnienie pola, dodatkowo sprawdzająca poprawność.

    void wypełnij(int[] liczby) {

        this.czyWypełnione = true;
        zaznaczoneLiczby = uprośćTablicę(liczby);
        if (zaznaczoneLiczby.length != 6) czyWażne = false;
        else czyWażne = true;

    }


    // Metoda umożliwiająca anulowanie pola.

    void anuluj() {
        this.czyAnulowane = true;
    }


    // Metoda umożliwiająca odczytanie zaznaczonych liczb.

    int[] odczytaj() {
        return zaznaczoneLiczby;
    }


    // Metoda zwracająca wartość logiczną odpowiadającą poprawności pola.

    boolean czyWażne() {
        if (czyAnulowane || !czyWypełnione) return false;
        return czyWażne;
    }


    // Metoda zwracająca wartość logiczną odpowiadającą wypełnieniu pola.

    boolean czyWypełnione() {
        return czyWypełnione;
    }


}
