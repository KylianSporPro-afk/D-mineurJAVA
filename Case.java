/**
 * La classe <code>Case</code> représente une case de la grille du démineur.
 * Une case peut être révélée ou cachée, minée ou non, et peut porter un marqueur.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class Case {

    /**
     * Valeur du marqueur indiquant qu'aucun marqueur n'est posé sur la case.
     */
    public static final int MARQUEUR_VIDE = 0;

    /**
     * Valeur du marqueur indiquant que le joueur est certain que la case est minée (★).
     */
    public static final int MARQUEUR_MINE = 1;

    /**
     * Valeur du marqueur indiquant que le joueur a un simple soupçon (?).
     */
    public static final int MARQUEUR_SOUPCON = 2;

    /**
     * Indique si la case contient une mine.
     */
    private boolean minee;

    /**
     * Indique si la case a été révélée par le joueur.
     */
    private boolean revelee;

    /**
     * Marqueur posé par le joueur. Vaut MARQUEUR_VIDE, MARQUEUR_MINE ou MARQUEUR_SOUPCON.
     */
    private int marqueur;

    /**
     * Nombre de mines dans les cases adjacentes, compris entre 0 et 8.
     */
    private int nbMinesAdjacentes;

    /**
     * Construit une case initiale : cachée, sans mine, sans marqueur, zéro mine adjacente.
     */
    public Case() {
        this.minee             = false;
        this.revelee           = false;
        this.marqueur          = MARQUEUR_VIDE;
        this.nbMinesAdjacentes = 0;
    }

    /**
     * Indique si cette case contient une mine.
     *
     * @return true si la case est minée, false sinon
     */
    public boolean estMinee() {
        return minee;
    }

    /**
     * Définit si cette case contient une mine.
     *
     * @param minee true pour poser une mine, false pour la retirer
     */
    public void setMinee(boolean minee) {
        this.minee = minee;
    }

    /**
     * Indique si cette case a été révélée.
     *
     * @return true si la case est révélée, false sinon
     */
    public boolean estRevelee() {
        return revelee;
    }

    /**
     * Révèle cette case.
     */
    public void reveler() {
        this.revelee = true;
    }

    /**
     * Retourne le marqueur actuellement posé sur cette case.
     *
     * @return MARQUEUR_VIDE, MARQUEUR_MINE ou MARQUEUR_SOUPCON
     */
    public int getMarqueur() {
        return marqueur;
    }

    /**
     * Fait tourner le marqueur dans l'ordre : vide → ★ → ? → vide.
     * Sans effet si la case est révélée.
     */
    public void changerMarqueur() {
        if (!revelee) {
            if (marqueur == MARQUEUR_VIDE) {
                marqueur = MARQUEUR_MINE;
            } else if (marqueur == MARQUEUR_MINE) {
                marqueur = MARQUEUR_SOUPCON;
            } else {
                marqueur = MARQUEUR_VIDE;
            }
        }
    }

    /**
     * Retourne le nombre de mines dans les cases adjacentes.
     *
     * @return un entier compris entre 0 et 8
     */
    public int getNbMinesAdjacentes() {
        return nbMinesAdjacentes;
    }

    /**
     * Définit le nombre de mines dans les cases adjacentes.
     *
     * @param nb le nombre de mines adjacentes, compris entre 0 et 8
     */
    public void setNbMinesAdjacentes(int nb) {
        this.nbMinesAdjacentes = nb;
    }
}