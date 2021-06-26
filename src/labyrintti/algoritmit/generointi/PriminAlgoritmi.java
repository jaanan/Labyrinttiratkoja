package labyrintti.algoritmit.generointi;
import labyrintti.malli.Pala;
import static labyrintti.malli.Pala.Tyyppi.KULKU;
import static labyrintti.malli.Pala.Tyyppi.MUURI;

public class PriminAlgoritmi {
    private int korkeus;
    private int leveys;
    private char [][] sokkelo;
    private Pala alku;
    private Pala loppu;
    private Piste vika;
    private boolean kerta;
    private int poistuminen;
    private long timeElapsed;

    public PriminAlgoritmi(int korkeus, int leveys) {
        // algoritmi saa parametreinä generoitavan labyrintin koon
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.sokkelo = new char[korkeus][leveys];
        this.kerta = false;
    }
    public Pala[] generoi() {
        // luodaan seinillä alustettu sokkelo
        StringBuilder merkit = new StringBuilder(leveys);
        for (int x = 0; x < leveys; x++)
            merkit.append('*');
        for (int x = 0; x < korkeus; x++) sokkelo[x] = merkit.toString().toCharArray();
        // kutsutaan varsinaista algoritmiä
        prim();
        // Palautetaan labyrintti sellaisessa muodossa, jotta Labyrintti-tiedosto ymmärtää sen ja osaa tehdä siitä samanlaisen labyrintin kuin Kruskalistakin.
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
                    this.poistuminen = y;
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

    public long getAika() {
        return this.timeElapsed;
    }

    public void prim() {
        long start = System.nanoTime();
        // valitaan labyrintille sisäänkäynti ja merkitään se S-kirjaimella
        int alku = 0;
        int piste = 1;
        Piste pointti = new Piste(alku, piste, null);
        sokkelo[pointti.rivi][pointti.sarake] = 'S';

        // iteroidaan pisteiden lähinaapurit läpi
        var kaaret = new Piste[korkeus*leveys];
        int kelvolliset = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0 || i != 0 && j != 0)
                    continue;
                try {
                    if (sokkelo[pointti.rivi + i][pointti.sarake + j] == '.') continue;
                } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                    continue;
                }
                // lisätään kelvolliset pisteet kaaritaulukkoon
                kaaret[kelvolliset] = new Piste(pointti.rivi + i, pointti.sarake + j, pointti);
                kelvolliset++;
            }

            // sekoitetaan kaaret, jotta saadaan sattumanvaraisuutta ja käydään kaikki kaaret läpi
            for (int m = 0; m < kelvolliset; m++) {

                // valitaan käsiteltävä piste sattumanvaraisesti

                int range = (kelvolliset-1 - m) + 1;
                int randomIndexToSwap = (int)(Math.random() * range) + m;
                Piste temp = kaaret[randomIndexToSwap];
                kaaret[randomIndexToSwap] = kaaret[m];
                kaaret[m] = temp;

                Piste nykyinen = kaaret[m];
                Piste seuraava = nykyinen.opposite();
                // jotta tulostaessa voidaan merkitä labyrintin ulosmeno, tallennetaan viimeisin piste
                if (seuraava.rivi >= 0 && seuraava.sarake >= 0 && seuraava.rivi < this.korkeus && seuraava.sarake < this.leveys) {
                    this.vika = seuraava;
                }

               try {
                    // jos sekä piste että sen vastakkainen piste ovat muuria
                    if (sokkelo[nykyinen.rivi][nykyinen.sarake] == '*') {
                        if (sokkelo[seuraava.rivi][seuraava.sarake] == '*') {

                            // avataan pisteiden välille polku
                            sokkelo[nykyinen.rivi][nykyinen.sarake] = '.';
                            sokkelo[seuraava.rivi][seuraava.sarake] = '.';

                            // iteroidaan pisteen suorat naapurit, kuten jo aiemmin ylhäällä tehtiin
                            for (int ii = -1; ii <= 1; ii++) {
                                for (int jj = -1; jj <= 1; jj++) {
                                    if (ii == 0 && jj == 0 || ii != 0 && jj != 0) {
                                        continue;
                                    } try {
                                        if (sokkelo[seuraava.rivi + ii][seuraava.sarake + jj] == '.') continue;
                                    } catch (Exception e) {
                                        continue;
                                    }
                                    kaaret[kelvolliset] = new Piste(seuraava.rivi + ii, seuraava.sarake + jj, seuraava);
                                    kelvolliset++;
                                }
                            }
                        }
                    }
                } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
                }
            }
            if (vika != null && !this.kerta) {
                sokkelo[vika.rivi][vika.sarake] = 'E';
                this.kerta = true;
            }
        }
        long finish = System.nanoTime();
        this.timeElapsed = finish - start;
    }
    static class Piste {
        Integer rivi;
        Integer sarake;
        Piste juuri;

        // piste saa aina vanhempansa parametrina. Näin voidaan luoda puu, josta labyrintti muodostuu
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