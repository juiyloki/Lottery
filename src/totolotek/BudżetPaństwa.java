package Totolotek.totolotek;

public class BudżetPaństwa {


    // Atrubuty Budżetu Państwa.

    private long pobranePodatki;
    private long przekazaneSubwencje;


    // Konstruktor.

    public BudżetPaństwa() {
        this.pobranePodatki = 0;
        this.przekazaneSubwencje = 0;
    }


    // Metoda zapisująca ilość subwencji przekazanych Centrali Totolotka.

    void przekazanieSubwencji(long kwota) {
        this.przekazaneSubwencje += kwota;
    }


    // Metoda archiwizująca kwotę podatku wpłaconego od Centrali Totolotka.

    void pobraniePodatku(long kwota) {
        this.pobranePodatki += kwota;
    }

    // Metoda wypisująca wartości atrybutów.

    public void drukujWszystko() {
        System.out.println("Przekazane subwencje: " +
                this.przekazaneSubwencje / 100 + " zł " +
                this.przekazaneSubwencje % 100 + " gr");
        System.out.println("Pobrane podatki: " +
                this.pobranePodatki / 100 + " zł " +
                this.pobranePodatki % 100 + " gr");
    }


}
