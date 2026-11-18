import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * La classe <code>VueGrille</code> est le panneau graphique qui affiche
 * la grille du démineur et gère les interactions souris du joueur.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class VueGrille extends JPanel implements MouseListener {

    /**
     * Taille en pixels d'une cellule de la grille.
     */
    private static final int TAILLE_CASE = 36;

    /**
     * Épaisseur en pixels de l'effet de relief des cases cachées.
     */
    private static final int RELIEF = 3;

    /**
     * La grille logique à afficher.
     */
    private Grille grille;

    /**
     * La fenêtre de jeu parente, utilisée pour notifier les mises à jour.
     */
    private FenetreJeu fenetreJeu;

    /**
     * Ligne de la case ayant explosé, ou -1 si aucune.
     */
    private int ligneExplosion;

    /**
     * Colonne de la case ayant explosé, ou -1 si aucune.
     */
    private int colonneExplosion;

    /**
     * Construit la vue graphique pour la grille donnée.
     *
     * @param grille     la grille logique à afficher
     * @param fenetreJeu la fenêtre de jeu parente
     */
    public VueGrille(Grille grille, FenetreJeu fenetreJeu) {
        this.grille           = grille;
        this.fenetreJeu       = fenetreJeu;
        this.ligneExplosion   = -1;
        this.colonneExplosion = -1;
        setPreferredSize(new Dimension(
            grille.getNbColonnes() * TAILLE_CASE,
            grille.getNbLignes()   * TAILLE_CASE
        ));
        addMouseListener(this);
    }

    /**
     * Dessine l'ensemble de la grille case par case.
     *
     * @param g le contexte graphique fourni par Swing
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int l = 0; l < grille.getNbLignes(); l++) {
            for (int c = 0; c < grille.getNbColonnes(); c++) {
                dessinerCase(g, l, c);
            }
        }
    }

    /**
     * Dessine la case située à la ligne l et à la colonne c.
     *
     * @param g le contexte graphique
     * @param l la ligne de la case
     * @param c la colonne de la case
     */
    private void dessinerCase(Graphics g, int l, int c) {
        Case ca = grille.getCase(l, c);
        int x   = c * TAILLE_CASE;
        int y   = l * TAILLE_CASE;
        if (ca.estRevelee()) {
            dessinerCaseRevelee(g, ca, l, c, x, y);
        } else {
            dessinerCaseCachee(g, ca, l, c, x, y);
        }
    }

    /**
     * Dessine une case révélée avec son chiffre ou le symbole de mine.
     *
     * @param g  le contexte graphique
     * @param ca la case à dessiner
     * @param l  la ligne
     * @param c  la colonne
     * @param x  la coordonnée pixel x
     * @param y  la coordonnée pixel y
     */
    private void dessinerCaseRevelee(Graphics g, Case ca, int l, int c, int x, int y) {
        if (ca.estMinee()) {
            if (l == ligneExplosion && c == colonneExplosion) {
                g.setColor(CouleurCase.MINE_EXPLOSE);
            } else {
                g.setColor(CouleurCase.MINE_REVELEE);
            }
            g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
            g.setColor(CouleurCase.REVELEE_BORD);
            g.drawRect(x, y, TAILLE_CASE - 1, TAILLE_CASE - 1);
            dessinerSymbole(g, "M", x, y, Color.WHITE);
        } else {
            g.setColor(CouleurCase.REVELEE);
            g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
            g.setColor(CouleurCase.REVELEE_BORD);
            g.drawRect(x, y, TAILLE_CASE - 1, TAILLE_CASE - 1);
            if (ca.getNbMinesAdjacentes() > 0) {
                dessinerSymbole(g,
                    String.valueOf(ca.getNbMinesAdjacentes()), x, y,
                    CouleurCase.getChiffre(ca.getNbMinesAdjacentes()));
            }
        }
    }

    /**
     * Dessine une case cachée avec son effet de relief et son marqueur éventuel.
     * En fin de partie perdue, révèle les mines non marquées et les marqueurs faux.
     *
     * @param g  le contexte graphique
     * @param ca la case à dessiner
     * @param l  la ligne
     * @param c  la colonne
     * @param x  la coordonnée pixel x
     * @param y  la coordonnée pixel y
     */
    private void dessinerCaseCachee(Graphics g, Case ca, int l, int c, int x, int y) {
        boolean partiePerdue = grille.estPartieTerminee() && grille.estPerdu();
        if (partiePerdue && ca.estMinee() && ca.getMarqueur() != Case.MARQUEUR_MINE) {
            g.setColor(CouleurCase.MINE_REVELEE);
            g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
            g.setColor(CouleurCase.REVELEE_BORD);
            g.drawRect(x, y, TAILLE_CASE - 1, TAILLE_CASE - 1);
            dessinerSymbole(g, "M", x, y, Color.WHITE);
            return;
        }
        if (partiePerdue && !ca.estMinee() && ca.getMarqueur() == Case.MARQUEUR_MINE) {
            g.setColor(CouleurCase.MARQUEUR_FAUX);
            g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
            g.setColor(CouleurCase.REVELEE_BORD);
            g.drawRect(x, y, TAILLE_CASE - 1, TAILLE_CASE - 1);
            dessinerSymbole(g, "X", x, y, Color.WHITE);
            return;
        }
        g.setColor(CouleurCase.CACHE);
        g.fillRect(x, y, TAILLE_CASE, TAILLE_CASE);
        g.setColor(CouleurCase.CACHE_CLAIR);
        g.fillRect(x, y, TAILLE_CASE, RELIEF);
        g.fillRect(x, y, RELIEF, TAILLE_CASE);
        g.setColor(CouleurCase.CACHE_SOMBRE);
        g.fillRect(x + TAILLE_CASE - RELIEF, y, RELIEF, TAILLE_CASE);
        g.fillRect(x, y + TAILLE_CASE - RELIEF, TAILLE_CASE, RELIEF);
        if (ca.getMarqueur() == Case.MARQUEUR_MINE) {
            dessinerSymbole(g, "★", x, y, Color.RED);
        } else if (ca.getMarqueur() == Case.MARQUEUR_SOUPCON) {
            dessinerSymbole(g, "?", x, y, Color.DARK_GRAY);
        }
    }

    /**
     * Centre et dessine un symbole texte dans la cellule.
     *
     * @param g       le contexte graphique
     * @param texte   le symbole à afficher
     * @param x       la coordonnée pixel x de la cellule
     * @param y       la coordonnée pixel y de la cellule
     * @param couleur la couleur du texte
     */
    private void dessinerSymbole(Graphics g, String texte, int x, int y, Color couleur) {
        g.setFont(new Font("SansSerif", Font.BOLD, TAILLE_CASE / 2));
        FontMetrics fm = g.getFontMetrics();
        int tx = x + (TAILLE_CASE - fm.stringWidth(texte)) / 2;
        int ty = y + (TAILLE_CASE - fm.getHeight()) / 2 + fm.getAscent();
        g.setColor(couleur);
        g.drawString(texte, tx, ty);
    }

    /**
     * Mémorise la position de la case ayant explosé pour l'afficher en rouge.
     *
     * @param l la ligne de la case explosée
     * @param c la colonne de la case explosée
     */
    public void setExplosion(int l, int c) {
        this.ligneExplosion   = l;
        this.colonneExplosion = c;
    }

    /**
     * Convertit une coordonnée pixel en indice de ligne ou colonne.
     *
     * @param pixel la coordonnée en pixels
     * @return l'indice de la case correspondante
     */
    private int pixelVersIndice(int pixel) {
        return pixel / TAILLE_CASE;
    }

    /**
     * Traite l'appui sur un bouton de la souris.
     * Clic gauche révèle la case, clic droit change le marqueur.
     *
     * @param e l'événement souris
     */
    public void mousePressed(MouseEvent e) {
        if (grille.estPartieTerminee()) { return; }
        int l = pixelVersIndice(e.getY());
        int c = pixelVersIndice(e.getX());
        if (!grille.estDansGrille(l, c)) { return; }
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (grille.getCase(l, c).estMinee()
                    && grille.getCase(l, c).getMarqueur() == Case.MARQUEUR_VIDE) {
                setExplosion(l, c);
            }
            grille.clicGauche(l, c);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            grille.clicDroit(l, c);
        }
        repaint();
        fenetreJeu.mettreAJourCompteur();
        if (grille.estPartieTerminee()) {
            fenetreJeu.finDePartie();
        }
    }

    /**
     * Non utilisé.
     *
     * @param e l'événement souris
     */
    public void mouseClicked(MouseEvent e)  {}

    /**
     * Non utilisé.
     *
     * @param e l'événement souris
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * Non utilisé.
     *
     * @param e l'événement souris
     */
    public void mouseEntered(MouseEvent e)  {}

    /**
     * Non utilisé.
     *
     * @param e l'événement souris
     */
    public void mouseExited(MouseEvent e)   {}
}