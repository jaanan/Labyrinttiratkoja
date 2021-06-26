package labyrintti.algoritmit.generointi;

// Luokka kaarille, joiden sisällä Kruskalin algoritmin PolkuPuu kulkee polkuja luodessaan.
// Kaari. eli tässä Raja, yhdistää kaksi palaa.

class Raja {

    private final int ekaPala;
    private final int tokaPala;

    Raja(int ekaPala, int tokaPala) {
        this.ekaPala = ekaPala;
        this.tokaPala = tokaPala;
    }

    int getEkaPala() {
        return ekaPala;
    }

    int getTokaPala() {
        return tokaPala;
    }
}
