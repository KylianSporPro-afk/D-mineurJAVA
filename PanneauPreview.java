import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * La classe <code>PanneauPreview</code> affiche une prévisualisation
 * de la grille dans l'écran de configuration d'une nouvelle partie.
 * Les mines sont placées aléatoirement à titre indicatif.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class PanneauPreview extends JPanel {

    /**
     * Couleur des cases normales dans la prévisualisation.
     */
    private static final Color COULEUR_CASE = new Color(180, 210, 240);

    /**
     * Couleur des cases minées dans la prévisualisation.
     */
    private static final Color COULEUR_MINE = new Color(220, 80, 60);

    /**
     * Nombre de colonnes de la grille prévisualisée.
     */
    private int cols;

    /**
     * Nombre de lignes de la grille prévisualisée.
     */
    private int ligs;

    /**
     * Nombre de mines à afficher dans la prévisualisation.
     */
    private int mines;

    /**
     * Tableau indiquant quelles cases contiennent une mine dans la prévisualisation.
     */
    private boolean[][] estMine;

    /**
     * Construit un panneau de prévisualisation avec des valeurs par défaut.
     */
    public PanneauPreview() {
        setBackground(new Color(230, 230, 220));
        this.cols  = 5;
        this.ligs  = 5;
        this.mines = 3;
        genererMines();
    }

    /**
     * Met à jour les dimensions et le nombre de mines, puis régénère
     * aléatoirement les positions des mines.
     *
     * @param cols  le nombre de colonnes
     * @param ligs  le nombre de lignes
     * @param mines le nombre de mines
     */
    public void setDimensions(int cols, int ligs, int mines) {
        this.cols  = cols;
        this.ligs  = ligs;
        this.mines = mines;
        genererMines();
    }

    /**
     * Place aléatoirement les mines dans le tableau estMine.
     */
    private void genererMines() {
        estMine = new boolean[ligs][cols];
        int placees = 0;
        while (placees < mines) {
            int l = (int) (Math.random() * ligs);
            int c = (int) (Math.random() * cols);
            if (!estMine[l][c]) {
                estMine[l][c] = true;
                placees++;
            }
        }
    }

    /**
     * Dessine la prévisualisation de la grille avec les mines en rouge.
     *
     * @param g le contexte graphique fourni par Swing
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2  = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int W      = getWidth();
        int H      = getHeight();
        int taille = Math.min(Math.min((W - 20) / cols, (H - 20) / ligs), 28);
        int offX   = (W - cols * taille) / 2;
        int offY   = (H - ligs * taille) / 2;
        int pad    = 1;
        int arc    = 3;
        for (int l = 0; l < ligs; l++) {
            for (int c = 0; c < cols; c++) {
                int x = offX + c * taille;
                int y = offY + l * taille;
                g2.setColor(estMine[l][c] ? COULEUR_MINE : COULEUR_CASE);
                g2.fillRoundRect(x + pad, y + pad, taille - pad * 2, taille - pad * 2, arc, arc);
                g2.setColor(new Color(160, 190, 210));
                g2.drawRoundRect(x + pad, y + pad, taille - pad * 2, taille - pad * 2, arc, arc);
            }
        }
    }
}