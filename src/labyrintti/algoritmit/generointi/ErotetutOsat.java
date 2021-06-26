package labyrintti.algoritmit.generointi;

import static java.util.stream.IntStream.range;


public class ErotetutOsat {

    private int[] vanhempi;
    private int[] sija;
    private int koko;

    // Kruskalin algoritmissä yhdistetään ne osat puusta, jotka eivät vielä olleet yhdistettyinä toisiinsa.

    public ErotetutOsat(int koko) {
        vanhempi = new int[koko];
        sija = new int[koko];
        for(int i = 0; i < koko; i++) {
            teeOsa(i);
        }
    }

    // alustuksessa vanhemman sija saa aina arvon 0, se on puun juuri

    private void teeOsa(int i) {
        vanhempi[i] = i;
        sija[i] = 0;
    }

    public int getKoko() {
        return koko;
    }

    public int etsi(int i) {
        if (i != vanhempi[i])
            vanhempi[i] = etsi(vanhempi[i]);
        return vanhempi[i];
    }

    // Kruskalin algoritmissä etsitään puita, joita ei ole vielä yhdistetty toisiinsa. Tällaisen löytyessä puut yhdistetään.
    
    public boolean yhdistys(int i, int j) {
        var iJuuri = etsi(i);
        var jJuuri = etsi(j);
        if (iJuuri == jJuuri)
            return false;
        if (sija[iJuuri] < sija[jJuuri]) {
            vanhempi[iJuuri] = jJuuri;
        } else {
            vanhempi[jJuuri] = iJuuri;
            if (sija[iJuuri] == sija[jJuuri])
                sija[iJuuri]++;
        }
        koko--;
        return true;
    }
}
