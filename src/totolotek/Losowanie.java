package Totolotek.totolotek;

public class Losowanie {


    // Atrybuty Losowania.

    private final int numerPorządkowy;
    private final int[] wygraneLiczby;

    private long dochód;

    private final int[] zwycięskieZakłady;
    private long[] puleNagród;
    private long[] kwotyWygranych;


    // Konstruktor.

    Losowanie(int numerPorządkowy, Centrala centrala) {

        this.numerPorządkowy = numerPorządkowy;
        this.wygraneLiczby = centrala.losujLiczby();

        this.dochód = 0;

        this.zwycięskieZakłady = new int[5];
        this.puleNagród = new long[5];
        this.kwotyWygranych = new long[5];

    }


    // Metoda pozwalająca na ustawienie puli nagród.

    void ustawPule(long[] puleNagród) {
        this.puleNagród = puleNagród;
    }


    // Metoda pozwalająca na ustawienie kwot wygranych.

    void ustawKwoty(long[] kwotyWygranych) {
        this.kwotyWygranych = kwotyWygranych;
    }


    // Metoda zwracająca tablicę zwycięskich zakładów.

    int[] dajZwycięskieZakłady() {
        return zwycięskieZakłady;
    }


    // Metoda zwracająca tablicę kwot wygranych.

    long[] dajKwotyWygranych() {
        return kwotyWygranych;
    }


    // Metoda zwracająca kwotę całkowitego dochodu z Losowania.

    long dajDochód() {
        return dochód;
    }


    // Metoda zwracająca tablicę numer porżadkowy Losowania.

    public int dajNumer() {
        return numerPorządkowy;
    }


    // Metoda zwracająca tablicę liczb wygranych w Losowaniu.

    public int[] dajLiczby() {
        return wygraneLiczby;
    }


    // Metoda pozwalająca na odnotowanie zwycięskiego zakładu
    // z nagrodą o danym stopniu.

    void zwycięskiZakład(int stopień) {
        zwycięskieZakłady[stopień]++;
    }


    // Metoda odnotowywująca dochód z zakupu Zakładów na to Losowanie.

    void dodajDochód(long kwota) {
        dochód += kwota;
    }


    // Metoda drukująca wszystkie informacje o tym Losowaniu.

    void drukujWszystko() {
        System.out.println(this);
        System.out.println("\nKwoty wygranych: ");
        for (int i = 1; i < 5; i++) {
            System.out.println("Stopień " + i + ": " +
                    kwotyWygranych[i] / 100 + " zł " +
                    kwotyWygranych[i] % 100 + " gr");
        }
        System.out.println("\nLiczby zwycięskich zakładów: ");
        for (int i = 1; i < 5; i++) {
            System.out.println("Stopień " + i + ": " +
                    zwycięskieZakłady[i]);
        }
        System.out.println("\nPule nagród: ");
        for (int i = 1; i < 5; i++) {
            System.out.println("Stopień " + i + ": " +
                    puleNagród[i] / 100 + " zł " +
                    puleNagród[i] % 100 + " gr");
        }
        System.out.println();
        System.out.println();
    }


    // Metoda equals().

    @Override
    public boolean equals(Object objekt) {
        if (!(objekt instanceof Losowanie losowanie)) return false;
        return losowanie.dajNumer() == this.dajNumer();
    }


    // Metoda toString().

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append("Losowanie nr ");
        s.append(numerPorządkowy);
        s.append("\nWyniki: ");

        for (Integer liczba : wygraneLiczby) {
            if (liczba < 10) s.append(" ");
            s.append(liczba);
            s.append(" ");
        }

        s.append("\nDochód z losowania: ");
        s.append(dochód);

        return s.toString();

    }


}

