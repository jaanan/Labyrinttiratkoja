import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import labyrintti.algoritmit.generointi.PolkuPuu;
import labyrintti.algoritmit.generointi.PriminAlgoritmi;
import labyrintti.algoritmit.ratkaisu.Ratkoja;
import labyrintti.malli.Labyrintti;
import labyrintti.malli.Pala;
import org.junit.jupiter.api.Test;

public class KruskalTest {

    @Test
    void algoritmiPalauttaaVainKulkupaloja() {
        PolkuPuu puu = new PolkuPuu(10, 10);
        var labyrintti = puu.generoi();
        var eikulku = 0;
        for (int i = 0; i < labyrintti.length; i++) {
            if(labyrintti[i].onkoKulku()) {
                continue;
            } else {
                eikulku++;
            }
        }

        assertEquals(0, eikulku);
    }

    @Test
    void kaikkialleLabyrintissäPääsee() {
        Labyrintti maze = new Labyrintti(30, 30);
        var puu = maze.getPuu();
        Pala [][] ruudukko = maze.getRuudukko();
        boolean alku = false;
        boolean loppu = false;
        boolean molemmat = false;
        for (var rivi : ruudukko) {
            for (var pala : rivi) {
                if(pala.onkoKulku()) {
                    Ratkoja solver = new Ratkoja(ruudukko, maze.getAstu(), pala);
                    var polku = solver.etsiUlos();
                    for (Pala p : polku) {
                        if (pala.equals(maze.getAstu())) alku = true;
                        if (pala.equals(pala))loppu = true;
                    }
                }
            } if (alku && loppu) {
                molemmat = true;
            } else {
                molemmat = false;
                assertEquals(true, molemmat);
            }
        }
        assertEquals(true, molemmat);
    }

    @Test
    void alunJaLopunValillaOnReitti() {
        Labyrintti maze = new Labyrintti(30, 30);
        var puu = maze.getPuu();
        Pala [][] ruudukko = maze.getRuudukko();
        var palaset = puu.generoi();
        for (int i = 0; i < palaset.length; i++) {
            var pala = palaset[i];
            ruudukko[pala.getRivi()][pala.getSarake()]= pala;
        }
        var lista = new Ratkoja(ruudukko, maze.getAstu(), maze.getUlos()).etsiUlos();
        int alku = 0;
        int loppu = 0;
        var ekasarake = maze.getAstu().getSarake();
        var ekarivi = maze.getAstu().getRivi();
        var vikasarake = maze.getUlos().getSarake();
        var vikarivi = maze.getUlos().getRivi();
        for (Pala pala : lista) {
            var palansarake = pala.getSarake();
            var palanrivi = pala.getRivi();
            if (palansarake == ekasarake && palanrivi == ekarivi) {
                alku++;
            } else if (palansarake == vikasarake && palanrivi == vikarivi) {
                loppu++;
            }
        }
        int summa = alku+loppu;
        assertEquals(2, summa);
    }

    //@Test
  /*
  void labyrintinPalatOvatLabyrintinMittojenSisällä
  * luo labyrintti
  *
  * for (looppaa labyrintin läpi)
  * tarkasta, että rivit ja sarakkeet, ovat labyrintin koon sisällä
  */
}
