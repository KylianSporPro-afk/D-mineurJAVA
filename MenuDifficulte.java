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
 * La classe <code>MenuDifficulte</code> affiche l'écran de sélection
 * de la difficulté du démineur : Easy, Medium ou Hard.
 * Chaque niveau impose une taille maximale de grille différente.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class MenuDifficulte extends JFrame implements ActionListener {

    /**
     * Couleur de fond du panneau principal.
     */
    private static final Color COULEUR_FOND   = new Color(245, 245, 240);

    /**
     * Couleur du bouton Easy (vert).
     */
    private static final Color COULEUR_EASY   = new Color(34, 139, 80);

    /**
     * Couleur du bouton Medium (orange).
     */
    private static final Color COULEUR_MEDIUM = new Color(210, 120, 20);

    /**
     * Couleur du bouton Hard (rouge).
     */
    private static final Color COULEUR_HARD   = new Color(180, 40, 40);

    /**
     * Construit et affiche le menu de sélection de difficulté.
     */
    public MenuDifficulte() {
        super("Démineur — Difficulté");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panneauPrincipal = new JPanel(new BorderLayout(0, 24));
        panneauPrincipal.setBackground(COULEUR_FOND);
        panneauPrincipal.setBorder(new EmptyBorder(40, 48, 40, 48));
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
        titre.setFont(new Font("SansSerif", Font.BOLD, 28));
        titre.setForeground(new Color(30, 30, 30));

        JLabel sousTitre = new JLabel("Choisissez une difficulté", SwingConstants.CENTER);
        sousTitre.setFont(new Font("SansSerif", Font.PLAIN, 15));
        sousTitre.setForeground(new Color(100, 100, 100));

        panneau.add(titre);
        panneau.add(sousTitre);
        return panneau;
    }

    /**
     * Construit le panneau contenant les trois boutons de difficulté.
     *
     * @return le panneau des boutons
     */
    private JPanel construireBoutons() {
        JPanel panneau = new JPanel(new GridLayout(3, 1, 0, 14));
        panneau.setBackground(COULEUR_FOND);

        panneau.add(creerBouton("Easy   —   4 a 5 cases",  COULEUR_EASY,   "EASY"));
        panneau.add(creerBouton("Medium —   4 a 15 cases", COULEUR_MEDIUM, "MEDIUM"));
        panneau.add(creerBouton("Hard   —   4 a 30 cases", COULEUR_HARD,   "HARD"));
        return panneau;
    }

    /**
     * Crée un bouton de difficulté stylisé.
     *
     * @param label     le texte affiché sur le bouton
     * @param couleur   la couleur de fond du bouton
     * @param actionCmd la commande d'action associée
     * @return le bouton configuré
     */
    private JButton creerBouton(String label, Color couleur, String actionCmd) {
        JButton btn = new JButton(label);
        btn.setActionCommand(actionCmd);
        btn.addActionListener(this);
        btn.setBackground(couleur);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(320, 56));
        btn.setOpaque(true);
        btn.addMouseListener(new SurvoleurbBouton(btn, couleur));
        return btn;
    }

    /**
     * Réagit aux clics sur les boutons de difficulté et ouvre la configuration.
     *
     * @param e l'événement déclenché par le clic
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        int maxCases;
        if (cmd.equals("EASY")) {
            maxCases = 5;
        } else if (cmd.equals("MEDIUM")) {
            maxCases = 15;
        } else {
            maxCases = 30;
        }
        dispose();
        new ConfigPartie(cmd, maxCases);
    }
}