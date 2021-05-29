package labyrintti.palvelu;

import labyrintti.malli.Labyrintti;
import labyrintti.algoritmit.generointi.PriminAlgoritmi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

// Tämä luokka mahdollistaa käyttäjän ja sovelluksen vuorovaikutuksen.
// Se lukee käyttäjän syötteitä ja printtaa niiden mukaan näkymään konsoliin ja tallentaa labyrintin sisäiseisti. 

public class Konsoli {
  
    private Scanner scanner;
    private Labyrintti labyrintti;
    private boolean onkolabyrinttiSaatavilla = false;
    private int korkeus;
    private int leveys;


    // konsolin käynnistys
    public void start() {
        scanner = new Scanner(System.in);
        while (true) {
            valikko();
            try {
                var valinta = scanner.nextInt();
                scanner.nextLine();
                switch (valinta) {
                    case 0:
                        lopeta();
                        return;
                    case 1:
                        luo();
                        break;
                    case 2:
                        etsiPakoreitti(); 
                        break;
                    default:
                        System.out.println("Virheellinen valinta. Ole mieliksi ja koita uudelleen.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Virheellinen valinta. Ole mieliksi ja koita uudelleen.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Tuntematon erhe");
            }
        }
    }

    // Valikko esittelee käyttäjälle hänen vaihtoehtonsa
    private void valikko() {
        System.out.println("--- Valikko ---");
        System.out.println("1. Luo labyrintti");
        if (onkolabyrinttiSaatavilla) {
            System.out.println("2. Löydä pakotie");
        }
        System.out.println("0. Lopeta");
    }

    private void lopeta() {
        scanner.close();
        System.out.println("Kiitti moi!");
    }

    //Kysytään käyttäjältä kokotiedot labyrintin luomiseksi ja luodaan halutun kokoinen labyrintti
    private void luo() {
        System.out.println("Anna labyrintille koko ([koko] tai [korkeus leveys] muodossa)");
        var toive = scanner.nextLine();
        var split = toive.split(" ");
        if (split.length == 1) {
            var koko = parseInt(split[0]);
            labyrintti = new Labyrintti(koko);
            this.korkeus = koko;
            this.leveys = koko;
        } else if (split.length == 2) {
            var korkeus = parseInt(split[0]);
            var leveys = parseInt(split[1]);
            labyrintti = new Labyrintti(korkeus, leveys);
            this.korkeus = korkeus;
            this.leveys = leveys;
        } else {
            System.out.println("Antamasi koko ei kelpaa.");
        }
        onkolabyrinttiSaatavilla = true;
        tulosta();
    }
  
      // Tulostaa labyrintin
    private void tulosta() {
        System.out.println(labyrintti);
        new PriminAlgoritmi(korkeus, leveys);
    }

    // Näyttää pakoreitin ulos labyrintistä
    private void etsiPakoreitti() {
        System.out.println(labyrintti.etsiUlos());
    }


}
