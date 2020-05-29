package labyrintti.malli;

import labyrintti.algoritmit.generointi.PolkuPuu;
import labyrintti.algoritmit.ratkaisu.Ratkoja;

import java.util.function.Consumer;

import static java.lang.Integer.parseInt;
import static labyrintti.malli.Pala.Tyyppi.KULKU;
import static labyrintti.malli.Pala.Tyyppi.MUURI;

//  Luokka käsittelee labyrintin luomista ja hallintaa

public class Labyrintti {

    private int korkeus;
    private int leveys;
    private Pala[][] ruudukko;

    // varmistetaan, ettei tehdä turhaa työtä

    private boolean onRatkottu = false;

    public Labyrintti(int korkeus, int leveys) {
        if (korkeus < 3 || leveys < 3) {
            throw new IllegalArgumentException(
                "Sekä leveys että korkeus tulee olla vähintään 3");
        }
        this.korkeus = korkeus;
        this.leveys = leveys;
        ruudukko = new Pala[korkeus][leveys];
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
  
    private void luoTiet() {
        new PolkuPuu(korkeus, leveys).generoi().forEach(asetaPala());
    }


    private Consumer<Pala> asetaPala() {
        return pala -> ruudukko[pala.getRivi()][pala.getSarake()] = pala;
    }

    public String etsiUlos() {
        if (!onRatkottu) {
            new Ratkoja(ruudukko, getAstu(), getUlos()).etsiUlos().forEach(asetaPala());
            onRatkottu = true;
        }
        return toString(true);
    }
    
    // palauttaa sisäänkäynnin
  
    private Pala getAstu() {
        return ruudukko[0][1];
    }
  
    // palauttaa uloskäynnin
  
    private Pala getUlos() {
        return ruudukko[korkeus - 1][getPoistumisSarake()];
    } 
  
    private String toString(boolean avaaReitti) {
        var sb = new StringBuilder();
        for (var rivi : ruudukko) {
            for (var pala : rivi) {
                if (pala.onkoMuuri()) {
                    sb.append("▓▓");
                } else if (avaaReitti && pala.onkoUlos()) {
                    sb.append("  ");
                } else {
                    sb.append("██");
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

    public static Labyrintti load(String str) {
        try {
            var kokonaan = str.split("\n");
            var koko = kokonaan[0].split(" ");
            var korkeus = parseInt(koko[0]);
            var leveys = parseInt(koko[1]);
            var ruudukko = new Pala[korkeus][leveys];
            for (int i = 0; i < korkeus; i++) {
                var rivi = kokonaan[i + 1].split(" ");
                for (int j = 0; j < leveys; j++)
                    ruudukko[i][j] = new Pala(i, j, intToTyyppi(parseInt(rivi[j])));
            }
            return new Labyrintti(korkeus, leveys, ruudukko);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Valitettavasti ei pysty lataamaan labyrinttia."
            );
        }
    }

    private Labyrintti(int korkeus, int leveys, Pala[][] ruudukko) {
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.ruudukko = ruudukko;
    }

    private static Pala.Tyyppi intToTyyppi(int val) {
        return val == 1 ? MUURI : KULKU;
    }

    public String export() {
        var sb = new StringBuilder();
        sb.append(korkeus).append(' ').append(leveys).append('\n');
        for (var rivi : ruudukko) {
            for (var pala : rivi)
                sb.append(tyyppiToInt(pala)).append(' ');
            sb.append('\n');
        }
        return sb.toString();
    }

    private int tyyppiToInt(Pala pala) {
        return pala.onkoMuuri() ? 1 : 0;
    }
}
