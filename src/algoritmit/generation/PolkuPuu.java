package maze.algorithms.generation;

// tässä luokassa luodaan randomeja polkuja labyrinttiin


public class PolkuPuu {

    private int korkeus;
    private int leveys;

    public PolkuPuu(int korkeus, int leveys) {
        this.korkeus = (korkeus-1)/2;
        this.leveys = (leveys-1)/2;
    }
}
