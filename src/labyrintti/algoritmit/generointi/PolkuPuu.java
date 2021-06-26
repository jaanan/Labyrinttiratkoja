package labyrintti.algoritmit.generointi;

import labyrintti.malli.Pala;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static labyrintti.malli.Pala.Tyyppi.KULKU;


public class PolkuPuu {

    private int korkeus;
    private int leveys;
    private long timeElapsed;

    public PolkuPuu(int korkeus, int leveys) {
        this.korkeus = (korkeus-1)/2;
        this.leveys = (leveys-1)/2;
    }

// Luodaan labyrintin kaaret eli reunat, rajat, joiden avulla labyrintti voidaan luoda. Sekoitetaan ne satunnaiseen järjestykseen ja luodaan polut, joissa labyrintissa voi kulkea.
    
    public Pala[] generoi() {
        var reunat = luoReunat();     
        sekoitaTaulukko(reunat);
        var puu = luoViritettyPuu(reunat);
        return luoPolut(puu);
    }

// Luodaan taulukkos, jossa on kaikki labyrintin kaaret.

    private Raja[] luoReunat() {
        var rajat = new Raja[leveys+korkeus+korkeus*leveys*2];
        for (int sarake = 1; sarake < leveys; sarake++) {
            rajat[sarake-1]=(new Raja(indeksiin(0, sarake), indeksiin(0, sarake-1)));
        }
        
        for (int rivi = 1; rivi < korkeus; rivi++) {
            rajat[leveys+rivi-1]=(new Raja(indeksiin(rivi, 0), indeksiin(rivi - 1, 0)));
        }
        
        int apu = 0;
        for (int rivi = 1; rivi < korkeus; rivi++) {
            for (int sarake = 1; sarake < leveys; sarake++) {
                rajat[leveys+korkeus+apu]=(new Raja(indeksiin(rivi, sarake), indeksiin(rivi, sarake-1)));
                apu++;
                rajat[leveys+korkeus+apu]=(new Raja(indeksiin(rivi, sarake), indeksiin(rivi-1, sarake)));
                apu++;
            }
        }
        return rajat;
    }
    
    // Muokataan 2-ulotteiset indeksit 1-ulotteiseen taulukkoon sopiviksi
    
    private int indeksiin(int rivi, int sarake) {
        return rivi * leveys + sarake;
    }
    
    // sekoitetaan labyrintin kaaret satunnaiseen järjestykseen

    private Raja[] sekoitaTaulukko(Raja[] rajat) {
		Random rand = new Random();
		for (int i = 0; i < rajat.length; i++) {
			int randomIndexToSwap = rand.nextInt(rajat.length);
			Raja temp = rajat[randomIndexToSwap];
			rajat[randomIndexToSwap] = rajat[i];
			rajat[i] = temp;
		}
        return rajat;
    }

    // Luodaan lista kaarista, joista muodostuu labyrintin yhdistävä polku Kruskalin algoritmiä hyödyntäen.

    private Raja[] luoViritettyPuu(Raja[] rajat) {
        long start = System.nanoTime();
        ErotetutOsat erotteleOsat = new ErotetutOsat(leveys * korkeus);
        Raja[] truerajat = new Raja[rajat.length];
        int apu = 0;
        for (int i = 0; i < rajat.length; i++) {
            var raja = rajat[i];
            if (raja != null) {
                if (connects(raja, erotteleOsat)){
                    truerajat[i]=rajat[i];
                    apu++;
                } else {
                    i++;
                }
            }    
		}
        Raja[] puunrajat = new Raja[apu];
        int index = 0;
        for (int i = 0; i < truerajat.length; i++) {
            if (truerajat[i]!=null){
                puunrajat[index]=truerajat[i];
                index++;
            }
		}
        long finish = System.nanoTime();
        this.timeElapsed = finish - start;
        return puunrajat;
    }

    // Testataan, yhdistääkö tietty kaari jo polkuja. Jos ei niin ne voidaan yhdistää.

    private boolean connects(Raja raja, ErotetutOsat erotetutOsat) {
        var eka = raja.getEkaPala();
        var toka = raja.getTokaPala();
        return erotetutOsat.yhdistys(eka, toka);
    }

    // luodaan lista paloista, jotka kuuluvat Kruskalin algoritmin luomaan labyrinttiin. Ne ovat kaikki tyyppiä KULKU erotukseksi MUURI tyypeistä, joita pitkin ei voi kulkea labyrintin läpi.

    private Pala[] luoPolut(Raja[] viritettyPuu) {
        var kulkupalat = new Pala[viritettyPuu.length];
        for (int i = 0; i < viritettyPuu.length; i++) {
            Pala eka = indeksista(viritettyPuu[i].getEkaPala());
            Pala toka = indeksista(viritettyPuu[i].getTokaPala());
            kulkupalat[i] = getPolku(eka, toka);
        }
        return kulkupalat;
    }

    // Muutetaan koordinaatit takaisin 2-ulotteiseen maailmaan sopiviksi

    private Pala indeksista(int indeksi) {
        var rivi = indeksi / leveys;
        var sarake = indeksi % leveys;
        return new Pala(rivi, sarake, KULKU);
    }

    //  Palauttaa reitistä palan koordinaatteineen ja tyyppeineen

    private Pala getPolku(Pala eka, Pala toka) {
        var rivi = eka.getRivi() + toka.getRivi()+1;
        var sarake = eka.getSarake() + toka.getSarake()+1;
        return new Pala(rivi, sarake, KULKU);
    }

    // algoritmien tehokkuusvertailua varten.

    public long getAika() {
        return this.timeElapsed;
    }
}
