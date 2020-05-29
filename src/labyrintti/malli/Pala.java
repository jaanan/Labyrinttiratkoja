package labyrintti.malli;

import java.util.Objects;

// Tässä luokassa käsitellään yksittäisen labyrintin palasen tietoja

public class Pala {

    public enum Tyyppi {
        KULKU,
        MUURI,
        ULOS;
    }

    private int rivi;
    private int sarake;
    private final Tyyppi tyyppi;

    public Pala(int rivi, int sarake, Tyyppi tyyppi) {
        this.rivi = rivi;
        this.sarake = sarake;
        this.tyyppi = tyyppi;
    }

    public int getRivi() {
        return rivi;
    }

    public int getSarake() {
        return sarake;
    }

    public boolean onkoKulku() {
        return tyyppi == Tyyppi.KULKU;
    }

    public boolean onkoMuuri() {
        return tyyppi == Tyyppi.MUURI;
    }

    public boolean onkoUlos() {
        return tyyppi == Tyyppi.ULOS;
    }

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
