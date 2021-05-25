package labyrintti.malli;

import java.util.Objects;

// Tässä luokassa käsitellään yksittäisen labyrintin palasen tietoja

public class Pala {

    public enum Tyyppi {
        KULKU,
        MUURI,
        ULOS;
    }

// nämä seuraavat alla on mun lisäämät 2021
// private int rivi;
// private int sarake;
// private int tyyppi;

    //2021 pystyisikö tuon enum Tyypin toteuttamaan esimerkiksi Integerinä? Tai pystyykö taulukoihin sisällyttämään enumeja?

    private int rivi;
    private int sarake;
    private final Tyyppi tyyppi;

    public Pala(int rivi, int sarake, Tyyppi tyyppi) {
        this.rivi = rivi;
        this.sarake = sarake;
        this.tyyppi = tyyppi;
    }

// tämä tässä alla on mun luoma 2021
//    public Pala(int rivi, int sarake, int tyyppi) {
//        this.rivi = rivi;
//        this.sarake = sarake;
//        this.tyyppi = tyyppi;
//    }

// pystyisikö tässä esim olemaan joku if lause, että jos tyyppi on KULKU niin palautettaisiinkin esimerkiksi int 1? näin päästäisiin eroon tuosta Tyypistä. 

    public int getRivi() {
        return rivi;
    }

    public int getSarake() {
        return sarake;
    }

    public boolean onkoKulku() {
        return tyyppi == Tyyppi.KULKU;
    }

//Muutetaan niin, että KULKU = 1, MUURI = 2, ULOS = 3 Jos tämä tulisi käyttöön, niin tuo == pitäis muuttaa johonkin toiseen muotoon.

//    public boolean onkoKulku() {
//        return tyyppi == 1;
//    }

    public boolean onkoMuuri() {
        return tyyppi == Tyyppi.MUURI;
    }

//    public boolean onkoMuuri() {
//        return tyyppi == 2;
//    }

    public boolean onkoUlos() {
        return tyyppi == Tyyppi.ULOS;
    }

//    public boolean onkoUlos() {
//        return tyyppi == 3;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var pala = (Pala) o;
        return rivi == pala.rivi &&
            sarake == pala.sarake &&
            tyyppi == pala.tyyppi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rivi, sarake, tyyppi);
    }

    @Override
    public String toString() {
        return "Pala{" +
            "rivi=" + rivi +
            ", sarake=" + sarake +
            ", tyyppi=" + tyyppi +
            '}';
    }
}
