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

    public PolkuPuu(int korkeus, int leveys) {
        this.korkeus = (korkeus-1)/2;
        this.leveys = (leveys-1)/2;
    }

// Luodaan random lista palasia, joista muodostuu labyrintti
// Saako tässä olla List? Entä Collections.shuffle?
// Tuo Collections Shuffle sufflaa reunoja, jotka on tallennettu ArrayListiin, joten ehkä ensin pitää onnistua jotenkin tallentamaan Rajat taulukkoon arraylistin sijaan...
// Korvattu Collections.shuffle(reunat); metodilla sekoitaTaulukko  
    
    public Pala[] generoi() {
        var reunat = luoReunat();     
        sekoitaTaulukko(reunat);
        var puu = luoViritettyPuu(reunat);
        return luoPolut(puu);
    }
//2021 mietitään, miten tuon ylläolevan voisi toteuttaa taulukkona
// Miten tuon Pala-listan voi toteuttaa hyödyntäen vain taulukoita? Voiko taulukoihin tallentaa Paloja?

// Luodaan rajat labyrintille
// Ongelmallinen ArrayList käytössä

    private Raja[] luoReunat() {
        var rajat = new Raja[leveys+korkeus+korkeus*leveys*2];
        for (int sarake = 1; sarake < leveys; sarake++) {
            rajat[sarake-1]=(new Raja(indeksiin(0, sarake), indeksiin(0, sarake-1)));
        }
        
        for (int rivi = 1; rivi < korkeus; rivi++) {
            rajat[leveys+rivi-1]=(new Raja(indeksiin(rivi, 0), indeksiin(rivi - 1, 0)));
        }
        
        for (int rivi = 1; rivi < korkeus; rivi++) {
            for (int sarake = 1; sarake < leveys; sarake++) {
                rajat[leveys+korkeus+sarake-1]=(new Raja(indeksiin(rivi, sarake), indeksiin(rivi, sarake-1)));
                rajat[leveys+korkeus+leveys+sarake-1]=(new Raja(indeksiin(rivi, sarake), indeksiin(rivi-1, sarake)));
            }
        }
        return rajat;
    }
    
    // Muokataan 2-ulotteiset indeksit 1-ulotteiseen listaan sopiviksi
    
    private int indeksiin(int rivi, int sarake) {
        return rivi * leveys + sarake;
    }
    
    // luodaan metodi, joka tekee saman, kuin Collections.shuffle, mutta taulukolle
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

    // Luodaan lista reunoista, jotka yhdistävät polkuja Kruskalin algoritmiä hyödyntäen.
    // Mitä tässä tuo stream ja filter ja collect toList tekevät? raja on ilmeisesti järjestyksessä otettava olio listasta rajat? 
    // connects on tässä tiedostossa myöhemmin esitettävä funktio ja erotteleOsat yllä annettu muuttuja.

    private Raja[] luoViritettyPuu(Raja[] rajat) {
        var erotteleOsat = new ErotetutOsat(leveys * korkeus);
    //    return rajat.stream().filter(raja -> connects(raja, erotteleOsat)).collect(toList());
    // mites tän truerajat taulukon koko, onko tällä väliä, jos jää liian suureksi? Miten tän sais just oikeen kokoiseksi?
        Raja[] truerajat = new Raja[rajat.length];
        int apu = 0;
        for (int i = 0; i < rajat.length; i++) {
            if (connects(rajat[i], erotteleOsat)){
                // miten vois välttää, että tänne ei jää tyhjiä väliin? apumuuttujalla?
                truerajat[i]=rajat[i];
                apu++;
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
        return puunrajat;
    }

    // Testataan, yhdistääkö raja polkuja

    private boolean connects(Raja raja, ErotetutOsat erotetutOsat) {
        return erotetutOsat.yhdistys(raja.getEkaPala(), raja.getTokaPala());
    }

    // Lista paloista, jotka yhdistävät polkuja
    
    private Pala[] luoPolut(Raja[] viritettyPuu) {
        return viritettyPuu.stream().map(raja -> {
                var eka = indeksista(raja.getEkaPala());
                var toka = indeksista(raja.getTokaPala());
                return getPolku(eka, toka);
            }).collect(toList());
    }

    // Muutetaan koordinaatit takaisin 2-ulotteiseen maailmaan sopiviksi

    private Pala indeksista(int indeksi) {
        var rivi = indeksi / leveys;
        var sarake = indeksi % leveys;
        return new Pala(rivi, sarake, KULKU);
    }

    //  Palauttaa reitistä palan koordinaatteineen

    private Pala getPolku(Pala eka, Pala toka) {
        var rivi = eka.getRivi() + toka.getRivi()+1;
        var sarake = eka.getSarake() + toka.getSarake()+1;
        return new Pala(rivi, sarake, KULKU);
    }
}
