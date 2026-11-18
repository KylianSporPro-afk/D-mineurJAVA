import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

/**
 * La classe <code>SurvoleurbBouton</code> gère l'effet visuel de survol
 * sur un bouton Swing en changeant sa couleur de fond au passage de la souris.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class SurvoleurbBouton implements MouseListener {

    /**
     * Le bouton sur lequel l'effet de survol est appliqué.
     */
    private JButton bouton;

    /**
     * La couleur normale du bouton, affichée quand la souris ne le survole pas.
     */
    private Color couleurNormale;

    /**
     * La couleur affichée quand la souris survole le bouton.
     */
    private Color couleurHover;

    /**
     * Construit un écouteur de survol pour le bouton donné.
     *
     * @param bouton         le bouton à surveiller
     * @param couleurNormale la couleur normale du bouton
     */
    public SurvoleurbBouton(JButton bouton, Color couleurNormale) {
        this.bouton         = bouton;
        this.couleurNormale = couleurNormale;
        this.couleurHover   = couleurNormale.darker();
    }

    /**
     * Applique la couleur de survol quand la souris entre sur le bouton.
     *
     * @param e l'événement souris
     */
    public void mouseEntered(MouseEvent e) { bouton.setBackground(couleurHover); }

    /**
     * Restaure la couleur normale quand la souris quitte le bouton.
     *
     * @param e l'événement souris
     */
    public void mouseExited(MouseEvent e)  { bouton.setBackground(couleurNormale); }

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
    public void mousePressed(MouseEvent e)  {}

    /**
     * Non utilisé.
     *
     * @param e l'événement souris
     */
    public void mouseReleased(MouseEvent e) {}
}