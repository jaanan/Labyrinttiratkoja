package labyrintti.algoritmit.ratkaisu;

import java.util.Objects;

class Solmu {
  
    private final static int RAJAKUSTANNUS = 1;
    private final int rivi;
    private final int sarake;
    private final boolean onkoMuuri;
    private Solmu vanhempi;
    // Kuljettu matka alkusolmusta tähän solmuun.
    private int kuljettu; 
    // Arvioitu matka tästä solmusta loppusolmuun.
    private int arvio;  
    // lopullinen matka alkusolmusta loppusolmuun tämän solmun kautta.
    private int ratkaisu; 

    Solmu(int rivi, int sarake, boolean onkoMuuri) {
        this.rivi = rivi;
        this.sarake = sarake;
        this.onkoMuuri = onkoMuuri;
        vanhempi = this;
    }

    int getRivi() {
        return rivi;
    }

    int getSarake() {
        return sarake;
    }

    boolean onkoMuuri() {
        return onkoMuuri;
    }

    Solmu getVanhempi() {
        return vanhempi;
    }

    int getRatkaisu() { //tarkista
        return ratkaisu;
    }

    void laskeArvio(Solmu solmu) {
        this.arvio = Math.abs(solmu.rivi - this.rivi) + Math.abs(solmu.sarake - this.sarake); // saako tämmöistä Math.abs käyttää?
    }

    boolean onkoParempi(Solmu solmu) {
        return solmu.kuljettu + RAJAKUSTANNUS < this.kuljettu;
    }

    void muokkaaMatka(Solmu solmu) {
        this.vanhempi = solmu;
        this.kuljettu = solmu.kuljettu + RAJAKUSTANNUS;
        ratkaisu = kuljettu + arvio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var solmu = (Solmu) o;
        return rivi == solmu.rivi &&
            sarake == solmu.sarake &&
            onkoMuuri == solmu.onkoMuuri;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rivi, sarake, onkoMuuri);
    }
}
