import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.*;
import labyrintti.algoritmit.generointi.PriminAlgoritmi;
import labyrintti.algoritmit.ratkaisu.Ratkoja;
import labyrintti.malli.Labyrintti;
import labyrintti.malli.Pala;
import org.junit.jupiter.api.Test;

class PrimTest {

  @Test
  void labyrinttiOnOikeanKokoinen() {
    PriminAlgoritmi algoritmi = new PriminAlgoritmi(10, 10);
    Pala[] labyrintti = algoritmi.generoi();

    assertEquals(100, labyrintti.length);
  }

  @Test
  void labyrintinPaloissaOnOikeaTieto() {
    PriminAlgoritmi algoritmi = new PriminAlgoritmi(10, 10);
    Pala[] labyrintti = algoritmi.generoi();
    int testattu = 0;
    int alaraja = 0;
    int yläraja = 10;
    for(Pala pala: labyrintti) {
      if (alaraja <= pala.getRivi() && pala.getRivi() < yläraja && alaraja <= pala.getSarake() && pala.getSarake() < yläraja) {
        if (pala.onkoKulku() || pala.onkoUlos() || pala.onkoMuuri()) {
          testattu++;
        }
      }
    }
    assertEquals(labyrintti.length, testattu);
  }

  @Test
  void kaikkialleLabyrintissäPääsee() {
    Labyrintti maze = new Labyrintti(3, 3);
    PriminAlgoritmi primin = maze.getPrim();
    Pala [][] priminruudukko = maze.getPriminruudukko();
    boolean alku = false;
    boolean loppu = false;
    boolean molemmat = false;
    for (var rivi : priminruudukko) {
      for (var pala : rivi) {
        if(pala.onkoKulku()) {
          Ratkoja solver = new Ratkoja(priminruudukko, maze.getPrimaAstu(), pala);
          var polku = solver.etsiUlos();
          for (Pala p : polku) {
            if (pala.equals(maze.getPrimaAstu())) alku = true;
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
  void AlkuJaLoppupisteenValillaReitti() {
    Pala [][] priminruudukko = new Pala[10][10];
    PriminAlgoritmi algoritmi = new PriminAlgoritmi(10, 10);
    var papalat = algoritmi.generoi();
    for (int i = 0; i < papalat.length; i++) {
      var pala = papalat[i];
      priminruudukko[pala.getRivi()][pala.getSarake()]= pala;
    }
    var lista = new Ratkoja(priminruudukko, algoritmi.getAstu(), algoritmi.getUlos()).etsiUlos();
    int alku = 0;
    int loppu = 0;
    var ekasarake = algoritmi.getAstu().getSarake();
    var ekarivi = algoritmi.getAstu().getRivi();
    var vikasarake = algoritmi.getUlos().getSarake();
    var vikarivi = algoritmi.getUlos().getRivi();
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

  @Test
  void reittiOnKulkuKaytavilla() {
    Pala [][] priminruudukko = new Pala[10][10];
    PriminAlgoritmi algoritmi = new PriminAlgoritmi(10, 10);
    var algoritminpalat = algoritmi.generoi();
    for (int i = 0; i < algoritminpalat.length; i++) {
      var pala = algoritminpalat[i];
      priminruudukko[pala.getRivi()][pala.getSarake()]= pala;
    }
    var reitti = new Ratkoja(priminruudukko, algoritmi.getAstu(), algoritmi.getUlos()).etsiUlos();
    List<Pala> kulkulista = new ArrayList<Pala>();
    for (Pala pala : algoritminpalat) {
      if (pala.onkoKulku()) {
        kulkulista.add(pala);
      }
    }
    int tarkistus = 0;
    for (Pala pala : reitti) {
      int sarake = pala.getSarake();
      int rivi = pala.getRivi();
      for (Pala kulkupala : kulkulista) {
        int kulkusarake = kulkupala.getSarake();
        int kulkurivi = kulkupala.getRivi();
        if(sarake == kulkusarake && rivi == kulkurivi) {
          tarkistus++;
          continue;
        }
      }
    }
    assertEquals(reitti.size(), tarkistus);
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
