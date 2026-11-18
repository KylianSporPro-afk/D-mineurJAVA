import javax.swing.JDialog;
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

/**
 * La classe <code>FenetreFinPartie</code> affiche le résultat d'une partie
 * terminée : victoire ou défaite. Elle propose un bouton pour retourner
 * au menu principal.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class FenetreFinPartie extends JDialog implements ActionListener {

    /**
     * La fenêtre de jeu parente, à fermer lors du retour au menu.
     */
    private FenetreJeu fenetreJeu;

    /**
     * Construit et affiche la fenêtre de fin de partie.
     *
     * @param grille     la grille de la partie terminée
     * @param fenetreJeu la fenêtre de jeu parente
     */
    public FenetreFinPartie(Grille grille, FenetreJeu fenetreJeu) {
        super((java.awt.Frame) null, "Fin de partie", true);
        this.fenetreJeu = fenetreJeu;

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        JPanel panneau = new JPanel(new BorderLayout(0, 20));
        panneau.setBackground(new Color(245, 245, 240));
        panneau.setBorder(new EmptyBorder(36, 48, 36, 48));

        String message;
        Color couleur;
        if (grille.estPerdu()) {
            message = "Vous avez perdu !";
            couleur = new Color(180, 40, 40);
        } else {
            message = "Vous avez gagne !";
            couleur = new Color(34, 139, 80);
            Sauvegarde.supprimerSauvegarde();
        }

        JLabel labelResultat = new JLabel(message, SwingConstants.CENTER);
        labelResultat.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelResultat.setForeground(couleur);

        JButton btnRetour = new JButton("Retour au menu");
        btnRetour.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRetour.setBackground(new Color(34, 100, 180));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFocusPainted(false);
        btnRetour.setBorderPainted(false);
        btnRetour.setOpaque(true);
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetour.setActionCommand("MENU");
        btnRetour.addActionListener(this);

        panneau.add(labelResultat, BorderLayout.CENTER);
        panneau.add(btnRetour,     BorderLayout.SOUTH);

        setContentPane(panneau);
        pack();
        centrer();
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
     * Réagit au clic sur le bouton Retour au menu.
     *
     * @param e l'événement déclenché par le clic
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("MENU")) {
            dispose();
            fenetreJeu.dispose();
            new MenuPrincipal();
        }
    }
}