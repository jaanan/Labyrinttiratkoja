package labyrintti.algoritmit.generointi;
import java.util.*;

public class PriminAlgoritmi {
    private int korkeus;
    private int leveys;
    private char [][] maz;

    public PriminAlgoritmi(int korkeus, int leveys) {
        // dimensions of generated maze
        this.korkeus = korkeus;
        this.leveys = leveys;
        this.maz = new char[korkeus][leveys];
    }    
    public void generoi() {
          // build maze and initialize with only walls
        StringBuilder sb = new StringBuilder(leveys);
        for (int x = 0; x < leveys; x++)
        sb.append('*');
        for (int x = 0; x < korkeus; x++) maz[korkeus] = sb.toString().toCharArray();
    }    

  // select random point and open as start node
    public void prim() {
        Point st = new Point((int)(Math.random() * korkeus), (int)(Math.random() * leveys), null);
        maz[st.r][st.c] = 'S';

        // iterate through direct neighbors of node
        ArrayList <Point> frontier = new ArrayList <Point>();
        for (int x = -1; x <= 1; x++)
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

        Point last = null;
        while (!frontier.isEmpty()) {
      
         // pick current node at random
         Point cu = frontier.remove((int)(Math.random() * frontier.size()));
         Point op = cu.opposite();
         try {
          // if both node and its opposite are walls
          if (maz[cu.r][cu.c] == '*') {
           if (maz[op.r][op.c] == '*') {
      
            // open path between the nodes
            maz[cu.r][cu.c] = '.';
            maz[op.r][op.c] = '.';
      
            // store last node in order to mark it later
            last = op;
      
            // iterate through direct neighbors of node, same as earlier
            for (int z = -1; z <= 1; z++)
             for (int q = -1; q <= 1; q++) {
              if (z == 0 && q == 0 || z != 0 && q != 0)
               continue;
              try {
               if (maz[op.r + z][op.c + q] == '.') continue;
              } catch (Exception e) {
               continue;
              }
              frontier.add(new Point(op.r + z, op.c + q, op));
             }
           }
          }
         } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
         }
      
         // if algorithm has resolved, mark end node
         if (frontier.isEmpty())
          maz[last.r][last.c] = 'E';
        }
      
        // print final maze
        for (int i = 0; i < korkeus; i++) {
         for (int j = 0; j < leveys; j++)
          System.out.print(maz[i][j]);
         System.out.println();
        }
       }
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

