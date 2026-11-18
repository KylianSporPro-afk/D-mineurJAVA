import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * La classe <code>Sauvegarde</code> gère la persistance d'une partie de démineur.
 * Elle permet de sauvegarder l'état complet d'une grille dans un fichier texte
 * et de le recharger lors d'une session ultérieure.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class Sauvegarde {

    /**
     * Nom du fichier de sauvegarde utilisé pour stocker l'état de la partie.
     */
    private static final String FICHIER = "sauvegarde.dat";

    /**
     * Sauvegarde l'état complet de la grille dans le fichier de sauvegarde.
     * Pour chaque case, on enregistre si elle est minée, révélée et son marqueur.
     *
     * @param grille la grille à sauvegarder
     */
    public static void sauvegarder(Grille grille) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER));
            writer.write(grille.getNbLignes()   + "\n");
            writer.write(grille.getNbColonnes() + "\n");
            writer.write(grille.getNbMines()    + "\n");
            for (int l = 0; l < grille.getNbLignes(); l++) {
                for (int c = 0; c < grille.getNbColonnes(); c++) {
                    Case ca = grille.getCase(l, c);
                    int minee   = ca.estMinee()   ? 1 : 0;
                    int revelee = ca.estRevelee() ? 1 : 0;
                    writer.write(minee + " " + revelee + " " + ca.getMarqueur() + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Erreur sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge une grille depuis le fichier de sauvegarde.
     *
     * @return la grille chargée, ou null en cas d'erreur de lecture
     */
    public static Grille charger() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FICHIER));
            int nbLignes   = Integer.parseInt(reader.readLine().trim());
            int nbColonnes = Integer.parseInt(reader.readLine().trim());
            int nbMines    = Integer.parseInt(reader.readLine().trim());
            Grille grille  = new Grille(nbLignes, nbColonnes, nbMines);
            for (int l = 0; l < nbLignes; l++) {
                for (int c = 0; c < nbColonnes; c++) {
                    String[] parts = reader.readLine().trim().split(" ");
                    Case ca = grille.getCase(l, c);
                    ca.setMinee(parts[0].equals("1"));
                    if (parts[1].equals("1")) { ca.reveler(); }
                    int marqueur = Integer.parseInt(parts[2]);
                    while (ca.getMarqueur() != marqueur) {
                        ca.changerMarqueur();
                    }
                }
            }
            reader.close();
            return grille;
        } catch (IOException e) {
            System.out.println("Erreur chargement : " + e.getMessage());
            return null;
        }
    }

    /**
     * Vérifie si un fichier de sauvegarde existe.
     *
     * @return true si une sauvegarde est disponible, false sinon
     */
    public static boolean existeSauvegarde() {
        return new File(FICHIER).exists();
    }

    /**
     * Supprime le fichier de sauvegarde.
     */
    public static void supprimerSauvegarde() {
        new File(FICHIER).delete();
    }
}