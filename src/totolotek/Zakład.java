package Totolotek.totolotek;

import java.util.*;

public class Zakład {


    // Atrybut Zakładu.

    private final int[] liczby;


    // Konstruktor.

    Zakład(int[] liczby) {
        Arrays.sort(liczby);
        this.liczby = liczby;
    }


    // Metoda udostępniająca liczby zakładu.

    int[] dajLiczby() {
        return this.liczby;
    }


    // Metoda toString().

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        for (int i : this.liczby) {
            if (i < 10) s.append(" ");
            s.append(i);
            s.append(" ");
        }
        return s.toString();
    }


}
