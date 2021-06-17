import static org.junit.jupiter.api.Assertions.assertEquals;

import labyrintti.algoritmit.generointi.PriminAlgoritmi;
import labyrintti.malli.Pala;
import org.junit.jupiter.api.Test;

class PrimTest {

  @Test
  void labyrinttiOnOikeanKokoinen() {
    PriminAlgoritmi algoritmi = new PriminAlgoritmi(10, 10);
    Pala[] labyrintti = algoritmi.generoi();

    assertEquals(100, labyrintti.length);
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
