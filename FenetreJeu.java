import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

/**
 * La classe <code>FenetreJeu</code> est la fenêtre principale du jeu.
 * Elle affiche la grille, le compteur de mines restantes et le bouton
 * de sauvegarde. Elle gère également la fermeture de la fenêtre.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class FenetreJeu extends JFrame implements ActionListener, WindowListener {

    /**
     * La grille logique de la partie en cours.
     */
    private Grille grille;

    /**
     * Le panneau graphique affichant la grille.
     */
    private VueGrille vueGrille;

    /**
     * Le label affichant le nombre de mines restantes.
     */
    private JLabel labelCompteur;

    /**
     * Le bouton permettant de sauvegarder et quitter la partie.
     */
    private JButton btnSauverQuitter;

    /**
     * Construit et affiche la fenêtre de jeu pour la grille donnée.
     *
     * @param grille la grille de la partie à jouer
     */
    public FenetreJeu(Grille grille) {
        super("Démineur");
        this.grille = grille;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);

        vueGrille = new VueGrille(grille, this);

        JPanel panneauHaut = new JPanel(new BorderLayout());
        panneauHaut.setBackground(new Color(245, 245, 240));
        panneauHaut.setBorder(new EmptyBorder(8, 12, 8, 12));

        labelCompteur = new JLabel("", SwingConstants.LEFT);
        labelCompteur.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelCompteur.setForeground(new Color(30, 30, 30));

        btnSauverQuitter = new JButton("Sauver et Quitter");
        btnSauverQuitter.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnSauverQuitter.setFocusPainted(false);
        btnSauverQuitter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSauverQuitter.setActionCommand("SAUVER");
        btnSauverQuitter.addActionListener(this);

        panneauHaut.add(labelCompteur,    BorderLayout.WEST);
        panneauHaut.add(btnSauverQuitter, BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(panneauHaut, BorderLayout.NORTH);
        add(vueGrille,   BorderLayout.CENTER);

        mettreAJourCompteur();
        pack();
        centrer();
        setResizable(false);
        setVisible(true);
    }

    /**
     * Centre la fenêtre au milieu de l'écran.
     */
    private void centrer() {
        Dimension ecran   = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension fenetre = getSize();
        setLocation(
            (ecran.width  - fenetre.width)  / 2,
            (ecran.height - fenetre.height) / 2
        );
    }

    /**
     * Met à jour le label affichant le nombre de mines moins les marqueurs ★.
     */
    public void mettreAJourCompteur() {
        int restantes = grille.getNbMines() - grille.nbMarqueursMine();
        labelCompteur.setText("Mines : " + restantes);
    }

    /**
     * Désactive le bouton de sauvegarde et ouvre la fenêtre de fin de partie.
     */
    public void finDePartie() {
        btnSauverQuitter.setEnabled(false);
        vueGrille.repaint();
        new FenetreFinPartie(grille, this);
    }

    /**
     * Réagit au clic sur le bouton Sauver et Quitter.
     * Sauvegarde la partie si elle est encore en cours, puis retourne au menu.
     *
     * @param e l'événement déclenché par le clic
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("SAUVER")) {
            if (!grille.estPartieTerminee()) {
                Sauvegarde.sauvegarder(grille);
            }
            dispose();
            new MenuPrincipal();
        }
    }

    /**
     * Sauvegarde la partie si elle est en cours lors de la fermeture de la fenêtre.
     *
     * @param e l'événement de fermeture
     */
    public void windowClosing(WindowEvent e) {
        if (!grille.estPartieTerminee()) {
            Sauvegarde.sauvegarder(grille);
        }
        dispose();
        new MenuPrincipal();
    }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    public void windowOpened(WindowEvent e)      {}

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    public void windowClosed(WindowEvent e)      {}

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    public void windowIconified(WindowEvent e)   {}

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    public void windowDeiconified(WindowEvent e) {}

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    public void windowActivated(WindowEvent e)   {}

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    public void windowDeactivated(WindowEvent e) {}
}