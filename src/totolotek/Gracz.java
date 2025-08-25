package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;

import java.util.*;

public abstract class Gracz {


    // Atrybuty każdego Gracza.

    private final String imię;
    private final String nazwisko;
    private final long pesel;

    private long środkiFinansowe;
    private final Set<Kupon> aktywneKupony;

    private final Informacja informacja;


    // Abstrakcyjne metody dla podklas:

    // Metoda unikalnej dla każdej podklasy strategii kupowania Kuponów.

    public abstract void kupKupony() throws Wyjątek;


    // Konstruktor.

    public Gracz(String imię, String nazwisko, long pesel, long środkiFinansowe, Centrala centrala) {

        this.imię = imię;
        this.nazwisko = nazwisko;
        this.pesel = pesel;

        this.środkiFinansowe = środkiFinansowe;
        this.aktywneKupony = new HashSet<>();

        this.informacja = centrala.dajInformację();

    }


    // Metoda udostępniająca refernecję do Informacji Centrali Totolotka.

    protected Informacja dajInformację() {
        return informacja;
    }


    // Metoda dodająca Kupon do portfela Gracza.

    void dodajKupon(Kupon kupon) {
        aktywneKupony.add(kupon);
    }


    // Metoda przeglądająca portfel gracza i realizująca gotowe Kupony.

    public void zrealizujGotoweKupony() throws Wyjątek {

        Iterator<Kupon> iterator = aktywneKupony.iterator();

        // Pętla przeglądająca wszystkie kupony.

        while (iterator.hasNext()) {

            // Jeśli Kupon nie ma aktywnych Losowań to jest realizowany.

            Kupon kupon = iterator.next();
            if (!kupon.czyAktywneLosowania()) {
                kupon.dajKolekturę().zrealizujKupon(this, kupon);
                iterator.remove();
            }

        }


    }


    // Metoda umożliwiająca pobranie środków finansowych z portfelu Gracza:
    // w przypadku sukcesu zwraca true i odejmuje kwotę,
    // a w przypadku niewystarczających środków zwraca fałsz.

    public boolean pobierzŚrodki(long kwota) {
        if (środkiFinansowe < kwota) return false;
        środkiFinansowe -= kwota;
        return true;
    }


    // Metoda dodająca środki finansowe do portfelu Gracza.
    
    public void dodajŚrodki(long kwota) {
        środkiFinansowe += kwota;
    }


    // Metoda toString().

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("Nazwisko: ").append(nazwisko);
        s.append("\nImię: ").append(imię);
        s.append("\nPESEL: ").append(pesel);
        s.append("\nPosiadane środki: ").append(środkiFinansowe);
        if (aktywneKupony.isEmpty()) s.append("\nBrak posiadanych kuponów.");
        else {
            s.append("\nIdentyfikatory posiadanych kuponów: ");
            for (Kupon kupon : aktywneKupony) {
                s.append("\n").append(kupon.dajIdentyfikator());
            }
        }
        s.append("\n");

        return s.toString();
    }


}
