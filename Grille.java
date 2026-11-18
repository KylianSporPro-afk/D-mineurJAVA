import java.util.Random;

/**
 * La classe <code>Grille</code> représente le plateau de jeu du démineur.
 * Elle gère le placement des mines, le calcul des mines adjacentes,
 * la révélation des cases et la détection de victoire ou de défaite.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class Grille {

    /**
     * Taille minimale autorisée pour le nombre de lignes ou de colonnes.
     */
    public static final int TAILLE_MIN = 4;

    /**
     * Taille maximale autorisée pour le nombre de lignes ou de colonnes.
     */
    public static final int TAILLE_MAX = 30;

    /**
     * Nombre de lignes de la grille.
     */
    private int nbLignes;

    /**
     * Nombre de colonnes de la grille.
     */
    private int nbColonnes;

    /**
     * Nombre total de mines placées dans la grille.
     */
    private int nbMines;

    /**
     * Tableau bidimensionnel contenant les cases de la grille.
     */
    private Case[][] cases;

    /**
     * Indique si la partie est terminée (victoire ou défaite).
     */
    private boolean partieTerminee;

    /**
     * Indique si le joueur a perdu.
     */
    private boolean perdu;

    /**
     * Construit une grille avec les dimensions et le nombre de mines donnés,
     * place les mines aléatoirement et calcule les mines adjacentes.
     *
     * @param nbLignes   le nombre de lignes, entre TAILLE_MIN et TAILLE_MAX
     * @param nbColonnes le nombre de colonnes, entre TAILLE_MIN et TAILLE_MAX
     * @param nbMines    le nombre de mines, entre 1 et nbLignes * nbColonnes - 1
     */
    public Grille(int nbLignes, int nbColonnes, int nbMines) {
        if (nbLignes < TAILLE_MIN || nbLignes > TAILLE_MAX) {
            throw new IllegalArgumentException("Nombre de lignes invalide : " + nbLignes);
        }
        if (nbColonnes < TAILLE_MIN || nbColonnes > TAILLE_MAX) {
            throw new IllegalArgumentException("Nombre de colonnes invalide : " + nbColonnes);
        }
        int nbCases = nbLignes * nbColonnes;
        if (nbMines < 1 || nbMines >= nbCases) {
            throw new IllegalArgumentException("Nombre de mines invalide : " + nbMines);
        }
        this.nbLignes       = nbLignes;
        this.nbColonnes     = nbColonnes;
        this.nbMines        = nbMines;
        this.partieTerminee = false;
        this.perdu          = false;
        initialiserCases();
        placerMines();
        calculerMinesAdjacentes();
    }

    /**
     * Initialise le tableau de cases avec des cases vierges.
     */
    private void initialiserCases() {
        cases = new Case[nbLignes][nbColonnes];
        for (int l = 0; l < nbLignes; l++) {
            for (int c = 0; c < nbColonnes; c++) {
                cases[l][c] = new Case();
            }
        }
    }

    /**
     * Place les mines aléatoirement dans la grille.
     */
    private void placerMines() {
        Random rand = new Random();
        int minesPlacees = 0;
        while (minesPlacees < nbMines) {
            int l = rand.nextInt(nbLignes);
            int c = rand.nextInt(nbColonnes);
            if (!cases[l][c].estMinee()) {
                cases[l][c].setMinee(true);
                minesPlacees++;
            }
        }
    }

    /**
     * Calcule pour chaque case le nombre de mines dans son voisinage.
     */
    private void calculerMinesAdjacentes() {
        for (int l = 0; l < nbLignes; l++) {
            for (int c = 0; c < nbColonnes; c++) {
                cases[l][c].setNbMinesAdjacentes(compterMinesAutour(l, c));
            }
        }
    }

    /**
     * Compte les mines dans les cases voisines de la case (l, c).
     *
     * @param l la ligne de la case centrale
     * @param c la colonne de la case centrale
     * @return le nombre de mines adjacentes, entre 0 et 8
     */
    private int compterMinesAutour(int l, int c) {
        int compteur = 0;
        for (int dl = -1; dl <= 1; dl++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dl == 0 && dc == 0) { continue; }
                int vl = l + dl;
                int vc = c + dc;
                if (estDansGrille(vl, vc) && cases[vl][vc].estMinee()) {
                    compteur++;
                }
            }
        }
        return compteur;
    }

    /**
     * Vérifie si les coordonnées (l, c) correspondent à une case existante.
     *
     * @param l la ligne à tester
     * @param c la colonne à tester
     * @return true si la case existe dans la grille, false sinon
     */
    public boolean estDansGrille(int l, int c) {
        return l >= 0 && l < nbLignes && c >= 0 && c < nbColonnes;
    }

    /**
     * Traite un clic gauche sur la case (l, c).
     * Sans effet si la partie est terminée, la case révélée ou marquée.
     * Si la case est minée, la partie est perdue.
     * Sinon la case est révélée, avec propagation si aucune mine adjacente.
     *
     * @param l la ligne de la case cliquée
     * @param c la colonne de la case cliquée
     */
    public void clicGauche(int l, int c) {
        if (partieTerminee)       { return; }
        if (!estDansGrille(l, c)) { return; }
        Case ca = cases[l][c];
        if (ca.estRevelee())                          { return; }
        if (ca.getMarqueur() != Case.MARQUEUR_VIDE)   { return; }
        if (ca.estMinee()) {
            ca.reveler();
            partieTerminee = true;
            perdu          = true;
            return;
        }
        revelerCaseEnChaine(l, c);
        if (estVictoire()) {
            partieTerminee = true;
        }
    }

    /**
     * Révèle la case (l, c) et propage aux voisines si aucune mine adjacente.
     *
     * @param l la ligne de la case à révéler
     * @param c la colonne de la case à révéler
     */
    private void revelerCaseEnChaine(int l, int c) {
        if (!estDansGrille(l, c)) { return; }
        Case ca = cases[l][c];
        if (ca.estRevelee()) { return; }
        if (ca.estMinee())   { return; }
        ca.reveler();
        if (ca.getNbMinesAdjacentes() == 0) {
            for (int dl = -1; dl <= 1; dl++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dl == 0 && dc == 0) { continue; }
                    revelerCaseEnChaine(l + dl, c + dc);
                }
            }
        }
    }

    /**
     * Traite un clic droit sur la case (l, c).
     * Fait tourner le marqueur : vide → ★ → ? → vide.
     * Sans effet si la partie est terminée ou la case révélée.
     *
     * @param l la ligne de la case cliquée
     * @param c la colonne de la case cliquée
     */
    public void clicDroit(int l, int c) {
        if (partieTerminee)       { return; }
        if (!estDansGrille(l, c)) { return; }
        if (!cases[l][c].estRevelee()) {
            cases[l][c].changerMarqueur();
        }
    }

    /**
     * Vérifie si le joueur a gagné, c'est-à-dire si toutes les cases
     * non minées ont été révélées.
     *
     * @return true si le joueur a gagné, false sinon
     */
    public boolean estVictoire() {
        for (int l = 0; l < nbLignes; l++) {
            for (int c = 0; c < nbColonnes; c++) {
                if (!cases[l][c].estMinee() && !cases[l][c].estRevelee()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Retourne le nombre de marqueurs ★ posés sur la grille.
     *
     * @return le nombre de cases portant le marqueur MARQUEUR_MINE
     */
    public int nbMarqueursMine() {
        int compteur = 0;
        for (int l = 0; l < nbLignes; l++) {
            for (int c = 0; c < nbColonnes; c++) {
                if (cases[l][c].getMarqueur() == Case.MARQUEUR_MINE) {
                    compteur++;
                }
            }
        }
        return compteur;
    }

    /**
     * Retourne la case située à la ligne l et à la colonne c.
     *
     * @param l la ligne
     * @param c la colonne
     * @return la case correspondante
     */
    public Case getCase(int l, int c) {
        return cases[l][c];
    }

    /**
     * Retourne le nombre de lignes de la grille.
     *
     * @return le nombre de lignes
     */
    public int getNbLignes() { return nbLignes; }

    /**
     * Retourne le nombre de colonnes de la grille.
     *
     * @return le nombre de colonnes
     */
    public int getNbColonnes() { return nbColonnes; }

    /**
     * Retourne le nombre total de mines de la grille.
     *
     * @return le nombre de mines
     */
    public int getNbMines() { return nbMines; }

    /**
     * Indique si la partie est terminée.
     *
     * @return true si la partie est terminée, false sinon
     */
    public boolean estPartieTerminee() { return partieTerminee; }

    /**
     * Indique si le joueur a perdu.
     *
     * @return true si une mine a explosé, false sinon
     */
    public boolean estPerdu() { return perdu; }
}