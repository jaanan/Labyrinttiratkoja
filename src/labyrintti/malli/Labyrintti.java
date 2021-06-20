package labyrintti.malli;

import java.util.function.Consumer;
import labyrintti.algoritmit.generointi.PolkuPuu;
import labyrintti.algoritmit.generointi.PriminAlgoritmi;
import labyrintti.algoritmit.ratkaisu.Ratkoja;


import static java.lang.Integer.parseInt;
import static labyrintti.malli.Pala.Tyyppi.KULKU;
import static labyrintti.malli.Pala.Tyyppi.MUURI;

//  Luokka käsittelee labyrintin luomista ja hallintaa

public class Labyrintti {

    private int korkeus;
    private int leveys;
    private Pala[][] ruudukko;
    private Pala[][] priminruudukko;
    private PriminAlgoritmi prima;
    private PolkuPuu puu;

    // varmistetaan, ettei tehdä turhaa työtä

    private boolean onRatkottu = false;
    private boolean priminRatkottu = false;

    public Labyrintti(int korkeus, int leveys) {
        if (korkeus < 3 || leveys < 3) {
            throw new IllegalArgumentException(
                "Sekä leveys että korkeus tulee olla vähintään 3");
        }
        this.korkeus = korkeus;
        this.leveys = leveys;
        ruudukko = new Pala[korkeus][leveys];
        priminruudukko = new Pala[korkeus][leveys];
        sovitaRuudukkoon();
    }


    // jos labyrintti on neliö
  
    public Labyrintti(int koko) {
        this(koko, koko);
    }

    // luodaan yksinkertainen labyrintti ilman silmukoita tai erillisiä seiniä
  
    private void sovitaRuudukkoon() {
        sovitaVuorotellen();
        sovitaRakoihin();
        teeOvet();
        luoTiet();
    }

    // ilmeiesesti tuon tyypin saa sisällytettyä tänne näin, vaikka se ei ole Integer
    private void asetaPala(int rivi, int sarake, Pala.Tyyppi tyyppi) {
        ruudukko[rivi][sarake] = new Pala(rivi, sarake, tyyppi);
    }

    // Täyttää joka toisen palan käytävällä ja joka toisen muurilla

    private void sovitaVuorotellen() {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                if ((i & 1) == 0 || (j & 1) == 0) {
                    asetaPala(i, j, MUURI);
                } else {
                    asetaPala(i, j, KULKU);
                }
            }
        }
    }

    // Parillisen labyrinttikoon ongelmien eliminointi 
  
    private void sovitaRakoihin() {
        if (korkeus % 2 == 0) muuriaVikaRivi();
        if (leveys % 2 == 0) muuriaVikaSarake();
    }

    private void muuriaVikaSarake() {
        for (int i = 0; i < korkeus; i++)
            asetaPala(i, leveys - 1, MUURI);
    }

    private void muuriaVikaRivi() {
        for (int i = 0; i < leveys; i++)
            asetaPala(korkeus - 1, i, MUURI);
    }
    
    private int getPoistumisSarake() {
        return leveys - 3 + leveys % 2;
    }

    // asetellaan sisään- ja uloskäynnit
    
    private void teeOvet() {
        asetaPala(0, 1, KULKU);
        asetaPala(korkeus - 1, getPoistumisSarake(), KULKU);
        if (korkeus % 2 == 0)
            asetaPala(korkeus - 2, getPoistumisSarake(), KULKU);
    }

    // luodaan sattumanvaraisia kulkukäytäviä 
    // masterissa tämä metodi on luotu näin:
    //     private void luoTiet() {
    //    new PolkuPuu(korkeus, leveys).generoi().forEach(asetaPala());
    // }
  
    private void luoTiet() {
        PolkuPuu pp = new PolkuPuu(korkeus, leveys);
        this.puu = pp;
        var pppalat = pp.generoi();
        for (int i = 0; i < pppalat.length; i++) {
            var pala = pppalat[i];
            ruudukko[pala.getRivi()][pala.getSarake()]= pala;
        }

        PriminAlgoritmi pa = new PriminAlgoritmi(korkeus, leveys);
        this.prima = pa;
        var papalat = pa.generoi();
        for (int i = 0; i < papalat.length; i++) {
            var pala = papalat[i];
            priminruudukko[pala.getRivi()][pala.getSarake()]= pala;
        }
    }

    private Consumer<Pala> asetaPala() {
        return pala -> ruudukko[pala.getRivi()][pala.getSarake()] = pala;
    }

    public Consumer<Pala> asetaPrim() {
        return pala -> priminruudukko[pala.getRivi()][pala.getSarake()] = pala;
    }

    public String etsiUlos() { // tätä ei voinut muuttaa voidiks, herjaa
        if (!onRatkottu) {
            new Ratkoja(ruudukko, getAstu(), getUlos()).etsiUlos().forEach(asetaPala());
            onRatkottu = true;
        }
        return toString(true);
    }

    public void etsiPrim() {
        if (!priminRatkottu) {
            new Ratkoja(priminruudukko, getPrimaAstu(), getPrimaUlos()).etsiUlos().forEach(asetaPrim());
            priminRatkottu = true;
        }
        // return toString(true);
    }
    
    // palauttaa sisäänkäynnin
  
    public Pala getAstu() {
        return ruudukko[0][1];
    }

    // palauttaa Primin algoritmin sisäänkäynnin 
    public Pala getPrimaAstu() {
        return prima.getAstu();
    }
  
    // palauttaa uloskäynnin
  
    private Pala getUlos() {
        return ruudukko[korkeus - 1][getPoistumisSarake()];
    }


    // palauttaa Primin algoritmin uloskäynnin
    public Pala getPrimaUlos() {
        return prima.getUlos();
    }

    public PriminAlgoritmi getPrim() {
        return this.prima;
    }

    public Pala [][] getPriminruudukko() {
        return this.priminruudukko;
    }

    public String getAikeEro() {
        long priminaika = prima.getAika();
        long kruskalinaika = puu.getAika();
        if (priminaika < kruskalinaika){
            return "Prim";
        } else if (kruskalinaika < priminaika) {
            return "Kruskal";
        }
        return "Yhtä nopeita";
    }
  
    private String toString(boolean avaaReitti) {
        var sb = new StringBuilder();
        for (var rivi : ruudukko) {
            for (var pala : rivi) {
                if (pala.equals(getAstu())|| pala.equals(getUlos())) {
                    sb.append("  ");
                } else if (pala.onkoMuuri()) {
                    sb.append("II");
                } else if (avaaReitti && pala.onkoUlos()) {
                    sb.append("  ");
                } else {
                    sb.append("▓▓");
                }
            }
            sb.append('\n');
        }
        sb.append('\n');
        sb.append('\n');

        for (var rivi : priminruudukko) {
            for (var pala : rivi) {
                if (pala.equals(prima.getAstu())|| pala.equals(prima.getUlos())) {
                    sb.append("  ");
                } else if (pala.onkoMuuri()) {
                    sb.append("II");
                } else if (avaaReitti && pala.onkoUlos()) {
                    sb.append("  ");
                } else {
                    sb.append("▓▓");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toString(false);
    }

//    private Labyrintti(int korkeus, int leveys, Pala[][] ruudukko) {
//        this.korkeus = korkeus;
//        this.leveys = leveys;
//        this.ruudukko = ruudukko;
//    }
}