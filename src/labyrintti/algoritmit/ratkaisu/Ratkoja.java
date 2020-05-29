package labyrintti.algoritmit.ratkaisu;

import labyrintti.malli.Pala;

//muut importit

// tämä luokka etsii reitin labyrintin sisäänkäynniltä uloskäynnille.

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import static java.util.Comparator.comparingInt;
import static maze.model.Cell.Type.ESCAPE;

public class Ratkoja {

    // liikkuu Palasta ylös, alas ja molemmille sivuille 
    private static final int[][] SUISTOT = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    // labyrintin koko
    private int korkeus;
    private int leveys;

    // kaksi ulotteinen taulukko, joka edustaa labyrinttia
    private Solmu[][] ruudukko;

    //sisään labyrinttiin
    private Solmu alku;
    //ulos labyrintista
    private Solmu loppu;

    // Priority queue joka esittää valikoiman solmuista, joiden kautta ratkaisu olisi nopein.
    private PriorityQueue<Solmu> avoin = new PriorityQueue<>(comparingInt(Solmu::getRatkaisu));

    // Jo läpikäydyt solmut
    private Set<Solmu> suljettu = new HashSet<>();

    // luodaan ruudukko solmuja
    public Ratkoja(Pala[][] ruudut, Pala alku, Pala loppu) {
        this.korkeus = ruudut.length;
        this.leveys = ruudut[0].length;
        this.ruudukko = new Solmu[korkeus][leveys];
        this.alku = new Solmu(alku.getRivi(), alku.getSarake(), false);
        this.loppu = new Solmu(loppu.getRivi(), loppu.getSarake(), false);
        luoSolmut(ruudut);
    }
    
  // lasketaan arvioitu matkan pituus
    private void luoSolmut(Pala[][] ruudut) {
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                var solmu = new Solmu(i, j, ruudut[i][j].onkoMuuri());
                solmu.laskeArvio(loppu);
                this.ruudukko[i][j] = solmu;
            }
        }
    }

    public List<Pala> etsiUlos() {
        avoin.add(alku);
        while (!avoin.isEmpty()) {
            var nyky = avoin.poll();
            if (onkoUlos(nyky))
                return teeUusiReitti(nyky);
            suljettu.add(nyky);
            muokkaaNaapurit(nyky);
        }
        return new ArrayList<>(); //palauta lista paloista, jotka johtaa alusta loppuun.
    }

    private boolean onkoUlos(Solmu solmu) {
        return solmu.equals(loppu);
    }
    
    // Tekee annetusta Palasta uuden reitin alkupalaan
    private List<Pala> teeUusiReitti(Solmu solmu) {
        var polku = new LinkedList<Pala>();
        polku.add(palaan(nyky));
        while (nyky.getVanhempi() != nyky) {
            var vanhempi = nyky.getVanhempi();
            polku.addFirst(palaan(Vanhempi));
            nyky = vanhempi;
        }
        return polku;
    }

    // Muuttaa solmun takaisin palaksi
    private Pala palaan(Solmu solmu) {
        return new Pala(solmu.getRivi(), solmu.getSarake(), ULOS);
    }

    // muokkaa arviota matkan pituudesta A* algoritmin mukaan.
    private void muokkaaNaapurit(Solmu nyky) {
        for (var suisto : SUISTOT) {
            var rivi = nyky.getRivi() + suisto[0];
            var sarake = nyky.getSarake() + suisto[1];
            if (rajoissa(rivi, sarake)) {
                var solmu = ruudukko[rivi][sarake];
                if (!solmu.onkoMuuri() && !suljettu.contains(solmu)) {
                    if (avoin.contains(solmu)) {
                        if (solmu.onkoParempi(nyky)) {
                            avoin.remove(solmu);
                        } else {
                            continue;
                        }
                    }
                    solmu.muokkaaMatka(nyky);
                    avoin.add(solmu);
                }
            }
        }
    }
    
    // Tarkistetaan ovatko annetut palaindeksit kaksiulotteisen taulukon rajojen sisäpuolella
    private boolean rajoissa(int rivi, int sarake) {
        return rivi >= 0 && rivi < korkeus
            && sarake >= 0 && sarake < leveys;
    }
}

