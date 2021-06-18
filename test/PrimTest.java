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



  //@Test
  /*
  void labyrintinPalatOvatLabyrintinMittojenSisällä
  * luo labyrintti
  *
  * for (looppaa labyrintin läpi)
  * tarkasta, että rivit ja sarakkeet, ovat labyrintin koon sisällä
  */



}
