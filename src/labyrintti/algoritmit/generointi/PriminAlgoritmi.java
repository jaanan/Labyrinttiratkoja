package labyrintti.algoritmit.generointi;
import java.util.*;
import labyrintti.malli.Pala;
import static labyrintti.malli.Pala.Tyyppi.KULKU;
import static labyrintti.malli.Pala.Tyyppi.MUURI;

public class PriminAlgoritmi {
    private int korkeus;
    private int leveys;
    private char [][] sokkelo;
    //    private StringBuilder tulosta;
    private Pala alku;
    private Pala loppu;
    private Piste vika;
    private boolean kerta;
    private int poistuminen;

    public PriminAlgoritmi(int korkeus, int leveys) {
        // gemeroitavan sokkelon mitat
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.sokkelo = new char[korkeus][leveys];
        this.kerta = false;
    }
    public Pala[] generoi() {
        // rakennetaan sokkelo ja alustetaan se seinillä
        StringBuilder merkit = new StringBuilder(leveys);
        for (int x = 0; x < leveys; x++)
            merkit.append('*');
        for (int x = 0; x < korkeus; x++) sokkelo[x] = merkit.toString().toCharArray();
        prim();
        var i = 0;
        Pala[] primmaze = new Pala[korkeus*leveys];
        for(int x = 0; x < korkeus; x++) {
            for(int y = 0; y < leveys; y++){
                if (sokkelo[x][y] == '*'){
                    primmaze[i] = new Pala(x, y, MUURI);
                    i++;
                } else if (sokkelo[x][y] == 'S') {
                    alku = new Pala(x, y, KULKU);
                    primmaze[i] = alku;
                    i++;
                } else if (sokkelo[x][y] == 'E') {
                    this.poistuminen = y; //olettaen, että y on sarake
                    loppu = new Pala(x, y, KULKU);
                    primmaze[i] = loppu;
                    i++;
                } else {
                    primmaze[i] = new Pala(x, y, KULKU);
                    i++;
                }
            }
        }
        return primmaze;
    }
    public Pala getAstu() {
        return alku;
    }

    public Pala getUlos() {
        return loppu;
    }

    public int getPoistu() {
        return this.poistuminen;
    }

    public void prim() {
        // valitaan alkupiste
        int alku = 0;
        int piste = 1;
        Piste pointti = new Piste(alku, piste, null);
        sokkelo[pointti.rivi][pointti.sarake] = 'S';

        // iteroidaan solmun suorat naapurit läpi
        ArrayList <Piste> rajasto = new ArrayList <Piste> ();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0 || i != 0 && j != 0)
                    continue;
                try {
                    if (sokkelo[pointti.rivi + i][pointti.sarake + j] == '.') continue;
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // lisätään kelvolliset solmut rajalle
                rajasto.add(new Piste(pointti.rivi + i, pointti.sarake + j, pointti));
            }

            while (!rajasto.isEmpty()) {

                // valitaan nykyinen solmu sattumanvaraisesti
                Piste nykyinen = rajasto.remove((int)(Math.random() * rajasto.size()));
                Piste seuraava = nykyinen.opposite();
                // jotta voidaa merkitä ulosmeno, tallennetaan viimeisin solmu
                if (seuraava.rivi >= 0 && seuraava.sarake >= 0 && seuraava.rivi < this.korkeus && seuraava.sarake < this.leveys) {
                    this.vika = seuraava;
                }

               try {
                    // jos sekä solmu että sen vastakkainen solmu ovat muuria
                    if (sokkelo[nykyinen.rivi][nykyinen.sarake] == '*') { // mites näihin if lauseisiin sai sen tai merkin? | ehkä
                        if (sokkelo[seuraava.rivi][seuraava.sarake] == '*') {

                            // avataan solmujen välille polku
                            sokkelo[nykyinen.rivi][nykyinen.sarake] = '.';
                            sokkelo[seuraava.rivi][seuraava.sarake] = '.';

                            // iteroidaan solmun suorat naapurit, sama kuin aiemmin
                            for (int ii = -1; ii <= 1; ii++) {
                                for (int jj = -1; jj <= 1; jj++) {
                                    if (ii == 0 && jj == 0 || ii != 0 && jj != 0) {
                                        continue;
                                    } try {
                                        if (sokkelo[seuraava.rivi + ii][seuraava.sarake + jj] == '.') continue;
                                    } catch (Exception e) {
                                        continue;
                                    }
                                    rajasto.add(new Piste(seuraava.rivi + ii, seuraava.sarake + jj, seuraava));
                                }
                            }
                        }
                    }
                } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
                }
            }  if (rajasto.isEmpty() && vika != null && !this.kerta) {
                sokkelo[vika.rivi][vika.sarake] = 'E'; //miksi tämä tulee kaksi kertaa?  Miksi aina r2c1 on E?
                this.kerta = true; // tämä on lisätty, koska koodi teki muuten aina saman myös r2c1:een
            }
        }

    }
    static class Piste {
        Integer rivi;
        Integer sarake;
        Piste juuri;
        public Piste(int vaaka, int pysty, Piste p) {
            rivi = vaaka;
            sarake = pysty;
            juuri = p;
        }
        // lasketaan, onko annettu vastakkainen solmu eri suuntaan juurisolmusta katsottuna.
        public Piste opposite() {
            if (this.rivi.compareTo(juuri.rivi) != 0)
                return new Piste(this.rivi + this.rivi.compareTo(juuri.rivi), this.sarake, this);
            if (this.sarake.compareTo(juuri.sarake) != 0)
                return new Piste(this.rivi, this.sarake + this.sarake.compareTo(juuri.sarake), this);
            return null;
        }
    }
}