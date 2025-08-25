package Totolotek.wyjątki;

public class Wyjątek extends Exception{

    public Wyjątek(String wiadomość) {
        super(wiadomość);
    }

    public void wypisz() {
        System.out.println(this.getMessage());
    }

}
