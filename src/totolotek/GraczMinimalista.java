package Totolotek.totolotek;

import Totolotek.wyjątki.Wyjątek;

public class GraczMinimalista extends Gracz{


    // Atrybuty Gracza Minimalisty.

    private final Kolektura ulubionaKolektura;


    // Konstruktor.

    public GraczMinimalista(
            String imię,
            String nazwisko,
            long pesel,
            Centrala centrala,
            long środki_finansowe,
            Kolektura ulubionaKolektura
    ) {

        // Wywołanie konstruktora nadklasy Gracz.

        super(imię, nazwisko, pesel, środki_finansowe, centrala);

        // Zapisanie ulubionej kolektury.

        this.ulubionaKolektura = ulubionaKolektura;

    }


    // Metoda implementująca strategię kupowania Kuponów Gracza Minimalistę.

    @Override
    public void kupKupony() throws Wyjątek {
        ulubionaKolektura.kupKuponLosowo(this,1,1);
    }


}
