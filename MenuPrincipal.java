import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * La classe <code>MenuPrincipal</code> affiche l'écran d'accueil du démineur.
 * Elle propose trois options : commencer une nouvelle partie, reprendre la partie
 * précédente (si une sauvegarde existe) ou quitter l'application.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class MenuPrincipal extends JFrame implements ActionListener {

    /**
     * Couleur de fond du panneau principal.
     */
    private static final Color COULEUR_FOND      = new Color(245, 245, 240);

    /**
     * Couleur du bouton Nouvelle partie (vert).
     */
    private static final Color COULEUR_NOUVELLE  = new Color(34, 139, 80);

    /**
     * Couleur du bouton Reprendre la partie (gris foncé).
     */
    private static final Color COULEUR_REPRENDRE = new Color(90, 90, 90);

    /**
     * Couleur du bouton Quitter (rouge).
     */
    private static final Color COULEUR_QUITTER   = new Color(180, 40, 40);

    /**
     * Construit et affiche le menu principal.
     */
    public MenuPrincipal() {
        super("Démineur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panneauPrincipal = new JPanel(new BorderLayout(0, 28));
        panneauPrincipal.setBackground(COULEUR_FOND);
        panneauPrincipal.setBorder(new EmptyBorder(44, 52, 44, 52));
        panneauPrincipal.add(construireEntete(),  BorderLayout.NORTH);
        panneauPrincipal.add(construireBoutons(), BorderLayout.CENTER);

        setContentPane(panneauPrincipal);
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
     * Construit le panneau d'en-tête avec le titre et le sous-titre.
     *
     * @return le panneau d'en-tête
     */
    private JPanel construireEntete() {
        JPanel panneau = new JPanel(new GridLayout(2, 1, 0, 6));
        panneau.setBackground(COULEUR_FOND);

        JLabel titre = new JLabel("Démineur", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 30));
        titre.setForeground(new Color(30, 30, 30));

        JLabel sousTitre = new JLabel("Que voulez-vous faire ?", SwingConstants.CENTER);
        sousTitre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        sousTitre.setForeground(new Color(110, 110, 110));

        panneau.add(titre);
        panneau.add(sousTitre);
        return panneau;
    }

    /**
     * Construit le panneau contenant les trois boutons du menu.
     *
     * @return le panneau des boutons
     */
    private JPanel construireBoutons() {
        JPanel panneau = new JPanel(new GridLayout(3, 1, 0, 12));
        panneau.setBackground(COULEUR_FOND);

        boolean partieEnAttente = Sauvegarde.existeSauvegarde();

        JButton btnNouvelle  = creerBouton("Nouvelle partie",     COULEUR_NOUVELLE,  "NOUVELLE",  true);
        JButton btnReprendre = creerBouton("Reprendre la partie", COULEUR_REPRENDRE, "REPRENDRE", partieEnAttente);
        JButton btnQuitter   = creerBouton("Quitter",             COULEUR_QUITTER,   "QUITTER",   true);

        panneau.add(btnNouvelle);
        panneau.add(btnReprendre);
        panneau.add(btnQuitter);
        return panneau;
    }

    /**
     * Crée un bouton stylisé pour le menu principal.
     *
     * @param label     le texte affiché sur le bouton
     * @param couleur   la couleur de fond du bouton
     * @param actionCmd la commande d'action associée
     * @param actif     true si le bouton est cliquable, false sinon
     * @return le bouton configuré
     */
    private JButton creerBouton(String label, Color couleur, String actionCmd, boolean actif) {
        JButton btn = new JButton(label);
        btn.setActionCommand(actionCmd);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(320, 56));
        btn.setOpaque(true);

        if (actif) {
            btn.addActionListener(this);
            btn.setBackground(couleur);
            btn.setForeground(Color.WHITE);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new SurvoleurbBouton(btn, couleur));
        } else {
            btn.setEnabled(false);
            btn.setBackground(new Color(180, 180, 175));
            btn.setForeground(new Color(120, 120, 115));
        }
        return btn;
    }

    /**
     * Réagit aux clics sur les boutons du menu.
     *
     * @param e l'événement déclenché par le clic
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("NOUVELLE")) {
            dispose();
            new MenuDifficulte();
        } else if (cmd.equals("REPRENDRE")) {
            Grille grille = Sauvegarde.charger();
            if (grille != null) {
                dispose();
                new FenetreJeu(grille);
            }
        } else if (cmd.equals("QUITTER")) {
            System.exit(0);
        }
    }
}