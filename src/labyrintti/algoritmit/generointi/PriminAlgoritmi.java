package labyrintti.algoritmit.generointi;
import java.util.*;

public class PriminAlgoritmi {
    private int korkeus;
    private int leveys;
    private char [][] maz;
    private StringBuilder tulosta;

    public PriminAlgoritmi(int korkeus, int leveys) {
        // dimensions of generated maze
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.maz = new char[korkeus][leveys];
        generoi();
    }    
    public void generoi() {
          // build maze and initialize with only walls
        StringBuilder sb = new StringBuilder(leveys);
        for (int x = 0; x < leveys; x++)
        sb.append('*');
        for (int x = 0; x < korkeus; x++) maz[x] = sb.toString().toCharArray();
        prim();
    }    

  // select random point and open as start node
    public void prim() {
        // select random point and open as start node
        int alku = 0;
        int piste = 1;
        Point st = new Point(alku, piste, null);
//        maz[st.r][st.c] = 'S';

        // iterate through direct neighbors of node
        ArrayList <Point> frontier = new ArrayList <Point> ();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0)
                    continue;
                    try {
                        if (maz[st.r + x][st.c + y] == '.') continue;
                    } catch (Exception e) { // ignore ArrayIndexOutOfBounds
                        continue;
                    }
        // add eligible points to frontier
        frontier.add(new Point(st.r + x, st.c + y, st));
        }

        while (!frontier.isEmpty()) {

        // pick current node at random
        Point cu = frontier.remove((int)(Math.random() * frontier.size()));
        Point op = cu.opposite();
            try {
                // if both node and its opposite are walls
                if (maz[cu.r][cu.c] == '*') { // mites näihin if lauseisiin sai sen tai merkin? | ehkä
                    if (maz[op.r][op.c] == '*') {

                        // open path between the nodes
                        maz[cu.r][cu.c] = '.';
                        maz[op.r][op.c] = '.';

                        // iterate through direct neighbors of node, same as earlier
                        for (int z = -1; z <= 1; z++) {
                            for (int q = -1; q <= 1; q++) {
                                if (z == 0 && q == 0 || z != 0 && q != 0) {
                                    continue;
                                } try {
                                    if (maz[op.r + z][op.c + q] == '.') continue;
                                } catch (Exception e) {
                                        continue;
                                }    
                                frontier.add(new Point(op.r + z, op.c + q, op));
                            }
                        }    
                    }
                } 
            } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
            }
        }
        if (frontier.isEmpty() && maz[korkeus-1][leveys-2] == '*') {
            maz[korkeus-1][leveys-2] = '.';
            if (maz[korkeus-1][leveys-3] == '*'&& maz[korkeus-2][leveys-2] == '*' && maz[korkeus-1][leveys-1] == '*' ) {
                if (maz[korkeus-2][leveys-3] == '.') {
                    maz[korkeus-1][leveys-3] = '.';
                } else if (maz[korkeus-2][leveys-1] == '.') {
                    maz[korkeus-1][leveys-1] = '.';    
                } else {
                    maz[korkeus-2][leveys-1] = '.';      
                }
            }
        }        
    }
}

    // print final maze
    public String toString(){
        StringBuilder ylin = new StringBuilder(leveys);
        StringBuilder alin = new StringBuilder(korkeus*leveys);
        this.tulosta = new StringBuilder(korkeus*leveys);
        for (int i = 0; i < korkeus; i++) {
            for (int j = 0; j < leveys; j++) {
                tulosta.append(maz[i][j]);                    
            }
            tulosta.append("\n");
        }
        for (int i = 0; i < leveys; i++) {
            if (i == 1) {
                ylin.append("S");
            } else {
                ylin.append("*");
            }
        }
        ylin.append("\n");
        for (int i = 0; i < leveys; i++) {
            if (i == leveys-2) {
                alin.append("E");
            } else {
                alin.append("*");
            }
        }
        alin.append("\n");    
        return ylin+tulosta.toString()+alin;
    }      

    static class Point {
        Integer r;
        Integer c;
        Point parent;
        public Point(int x, int y, Point p) {
            r = x;
            c = y;
            parent = p;
            }
    // compute opposite node given that it is in the other direction from the parent
        public Point opposite() {
            if (this.r.compareTo(parent.r) != 0)
                return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
            if (this.c.compareTo(parent.c) != 0)
                return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
            return null;
        }
    }   
}