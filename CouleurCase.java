import java.awt.Color;

/**
 * La classe <code>CouleurCase</code> centralise toutes les constantes de couleur
 * utilisées pour l'affichage des cases de la grille du démineur.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class CouleurCase {

    /**
     * Couleur de fond d'une case cachée.
     */
    public static final Color CACHE         = new Color(180, 180, 175);

    /**
     * Couleur du bord clair d'une case cachée (effet relief haut et gauche).
     */
    public static final Color CACHE_CLAIR   = new Color(220, 220, 215);

    /**
     * Couleur du bord sombre d'une case cachée (effet relief bas et droite).
     */
    public static final Color CACHE_SOMBRE  = new Color(100, 100, 95);

    /**
     * Couleur de fond d'une case révélée.
     */
    public static final Color REVELEE       = new Color(210, 205, 195);

    /**
     * Couleur du bord d'une case révélée.
     */
    public static final Color REVELEE_BORD  = new Color(150, 145, 140);

    /**
     * Couleur de fond de la case dont la mine a explosé.
     */
    public static final Color MINE_EXPLOSE  = new Color(220, 50, 50);

    /**
     * Couleur de fond d'une mine révélée en fin de partie.
     */
    public static final Color MINE_REVELEE  = new Color(60, 60, 60);

    /**
     * Couleur d'une case marquée ★ mais sans mine (marqueur faux).
     */
    public static final Color MARQUEUR_FAUX = new Color(200, 100, 50);

    /**
     * Couleur du chiffre 1.
     */
    private static final Color CHIFFRE_1 = new Color(0, 0, 200);

    /**
     * Couleur du chiffre 2.
     */
    private static final Color CHIFFRE_2 = new Color(0, 130, 0);

    /**
     * Couleur du chiffre 3.
     */
    private static final Color CHIFFRE_3 = new Color(200, 0, 0);

    /**
     * Couleur du chiffre 4.
     */
    private static final Color CHIFFRE_4 = new Color(0, 0, 130);

    /**
     * Couleur du chiffre 5.
     */
    private static final Color CHIFFRE_5 = new Color(130, 0, 0);

    /**
     * Couleur du chiffre 6.
     */
    private static final Color CHIFFRE_6 = new Color(0, 130, 130);

    /**
     * Couleur du chiffre 7.
     */
    private static final Color CHIFFRE_7 = new Color(80, 0, 80);

    /**
     * Couleur du chiffre 8.
     */
    private static final Color CHIFFRE_8 = new Color(80, 80, 80);

    /**
     * Retourne la couleur associée au chiffre de mines adjacentes donné.
     *
     * @param n le nombre de mines adjacentes, entre 1 et 8
     * @return la couleur correspondante
     */
    public static Color getChiffre(int n) {
        if (n == 1) { return CHIFFRE_1; }
        if (n == 2) { return CHIFFRE_2; }
        if (n == 3) { return CHIFFRE_3; }
        if (n == 4) { return CHIFFRE_4; }
        if (n == 5) { return CHIFFRE_5; }
        if (n == 6) { return CHIFFRE_6; }
        if (n == 7) { return CHIFFRE_7; }
        return CHIFFRE_8;
    }
}