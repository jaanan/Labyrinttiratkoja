package labyrintti.algoritmit.generointi;

// importtaukset


public class ErotetutOsat {

    private int[] vanhempi;
    private int[] sija;
    private int koko;

    // mahdollisesti ongelmallinen range(0, koko).forEach(this::teeOsa);

    public ErotetutOsat(int koko) {
        this.koko = koko;
        vanhempi = new int[koko];
        sija = new int[koko];
        range(0, koko).forEach(this::teeOsa);
    }

    //alustus

    private void teeOsa(int i) {
        vanhempi[i] = i;
        sija[i] = 0;
    }

    public int getSize() {
        return size;
    }

    public int etsi(int i) {
        if (i != vanhempi[i])
            vanhempi[i] = etsi(vanhempi[i]);
        return vanhempi[i];
    }

    // yhdistää erilliset osat, jos ne eivät vielä kuulu samaan osaan
    
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
