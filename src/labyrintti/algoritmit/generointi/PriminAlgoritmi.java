package labyrintti.algoritmit.generointi;
import java.util.*;
import labyrintti.malli.Pala;
import static labyrintti.malli.Pala.Tyyppi.KULKU;
import static labyrintti.malli.Pala.Tyyppi.MUURI;

public class PriminAlgoritmi {
    private int korkeus;
    private int leveys;
    private char [][] maz;
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
        this.maz = new char[korkeus][leveys];
        this.kerta = false;
    }    
    public Pala[] generoi() {
    // rakennetaan sokkelo ja alustetaan se seinillä
    StringBuilder sb = new StringBuilder(leveys);
    for (int x = 0; x < leveys; x++)
    sb.append('*');
    for (int x = 0; x < korkeus; x++) maz[x] = sb.toString().toCharArray();
    prim();
    var i = 0;
    Pala[] primmaze = new Pala[korkeus*leveys];
        for(int x = 0; x < korkeus; x++) {
            for(int y = 0; y < leveys; y++){
                if (maz[x][y] == '*'){
                    primmaze[i] = new Pala(x, y, MUURI);
                    i++;
                } else if (maz[x][y] == 'S') {
                    alku = new Pala(x, y, KULKU);
                    primmaze[i] = alku;
                    i++;
                } else if (maz[x][y] == 'E') {
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
        Piste st = new Piste(alku, piste, null);
        maz[st.rivi][st.sarake] = 'S';

        // iteroidaan solmun suorat naapurit läpi
        ArrayList <Piste> frontier = new ArrayList <Piste> ();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                    try {
                        if (maz[st.rivi + x][st.sarake + y] == '.') continue;
                    } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                        continue;
                    }
        // lisätään kelvolliset solmut rajalle
        frontier.add(new Piste(st.rivi + x, st.sarake + y, st));
        }

        while (!frontier.isEmpty()) {

        // valitaan nykyinen solmu sattumanvaraisesti
        Piste cu = frontier.remove((int)(Math.random() * frontier.size()));
        Piste op = cu.opposite(maz.length-1, maz[0].length-1);
        // jotta voidaa merkitä ulosmeno, tallennetaan viimeisin solmu    
        this.vika = op;
            try {
                // jos sekä solmu että sen vastakkainen solmu ovat muuria 
                if (maz[cu.rivi][cu.sarake] == '*') { // mites näihin if lauseisiin sai sen tai merkin? | ehkä
                    if (maz[op.rivi][op.sarake] == '*') {

                        // avataan solmujen välille polku
                        maz[cu.rivi][cu.sarake] = '.';
                        maz[op.rivi][op.sarake] = '.';

                        // iteroidaan solmun suorat naapurit, sama kuin aiemmin
                        for (int z = -1; z <= 1; z++) {
                            for (int q = -1; q <= 1; q++) {
                                if (z == 0 && q == 0 || z != 0 && q != 0) {
                                    continue;
                                } try {
                                    if (maz[op.rivi + z][op.sarake + q] == '.') continue;
                                } catch (Exception e) {
                                        continue;
                                }
                                int pointX = op.rivi + z;
                                int pointY = op.sarake + q;
                                if (pointX >= 0 && pointY >= 0 && pointX < maz.length && pointY < maz[0].length){
                                    frontier.add(new Piste(pointX, pointY, op));
                                }
                            }
                        }    
                    }
                } 
            } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
            } 
        }  if (frontier.isEmpty() && vika != null && !this.kerta) {
            maz[vika.rivi][vika.sarake] = 'E'; //miksi tämä tulee kaksi kertaa?  Miksi aina r2c1 on E?
            this.kerta = true; // tämä on lisätty, koska koodi teki muuten aina saman myös r2c1:een
         }   
    }   
    // for (int i = 0; i < korkeus; i++) {
    //    for (int j = 0; j < leveys; j++)
    //        System.out.print(maz[i][j]);
    //        System.out.println();
    // }

}
    static class Piste {
        Integer rivi;
        Integer sarake;
        Piste parent;

        public Piste(int x, int y, Piste p) {
            rivi = x;
            sarake = y;
            parent = p;
            }
    // lasketaan, onko annettu vastakkainen solmu eri suuntaan parent-solmusta katsottuna.
        public Piste opposite(int maxX,int maxY) {
            if (this.rivi.compareTo(parent.rivi) != 0){
                return new Piste(Math.min(Math.max(0, this.rivi + this.rivi.compareTo(parent.rivi)), maxX), this.sarake, this);
            }
            if (this.sarake.compareTo(parent.sarake) != 0){
                return new Piste(this.rivi, Math.min(Math.max(0, this.sarake + this.sarake.compareTo(parent.sarake)), maxY), this);
            }

            return null;
        }
    }   
}
