package labyrintti.algoritmit.generointi;

// Luokka rajoille, joiden sisällä PolkuPuu kulkee polkuja luodessaan.
class Raja {

    private int ekaPala;
    private int tokaPala;

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
