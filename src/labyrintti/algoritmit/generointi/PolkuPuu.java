package labyrintti.algoritmit.generointi;

import labyrintti.malli.Pala;

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
    
    public List<Pala> generoi() {
        var reunat = luoReunat();
        Collections.shuffle(reunat);
        var puu = luoViritettyPuu(reunat);
        return luoPolut(puu);
    }
//2021 mietitään, miten tuon ylläolevan voisi toteuttaa taulukkona
// Miten tuon Pala-listan voi toteuttaa hyödyntäen vain taulukoita? Voiko taulukoihin tallentaa Paloja?

// Luodaan rajat labyrintille
// Ongelmallinen ArrayList käytössä

    private List<Raja> luoReunat() {
        var rajat = new ArrayList<Raja>();
        for (int sarake = 1; sarake < leveys; sarake++) {
            rajat.add(new Raja(indeksiin(0, sarake), indeksiin(0, sarake-1)));
        }
        
        for (int rivi = 1; rivi < korkeus; rivi++) {
            rajat.add(new Raja(indeksiin(rivi, 0), indeksiin(rivi - 1, 0)));
        }
        
        for (int rivi = 1; rivi < korkeus; rivi++) {
            for (int sarake = 1; sarake < leveys; sarake++) {
                rajat.add(new Raja(indeksiin(rivi, sarake), indeksiin(rivi, sarake-1)));
                rajat.add(new Raja(indeksiin(rivi, sarake), indeksiin(rivi-1, sarake)));
            }
        }
        return rajat;
    }
    
    // Muokataan 2-ulotteiset indeksit 1-ulotteiseen listaan sopiviksi
    
    private int indeksiin(int rivi, int sarake) {
        return rivi * leveys + sarake;
    }

    // Luodaan lista reunoista, jotka yhdistävät polkuja Kruskalin algoritmiä hyödyntäen.

    private List<Raja> luoViritettyPuu(List<Raja> rajat) {
        var erotteleOsat = new ErotetutOsat(leveys * korkeus);
        return rajat.stream().filter(raja -> connects(raja, erotteleOsat)).collect(toList());
    }

    // Testataan, yhdistääkö raja polkuja

    private boolean connects(Raja raja, ErotetutOsat erotetutOsat) {
        return erotetutOsat.yhdistys(raja.getEkaPala(), raja.getTokaPala());
    }

    // Lista paloista, jotka yhdistävät polkuja
    
    private List<Pala> luoPolut(List<Raja> viritettyPuu) {
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
