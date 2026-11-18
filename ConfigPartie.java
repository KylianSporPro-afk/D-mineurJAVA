import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
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
 * La classe <code>ConfigPartie</code> affiche l'écran de configuration
 * d'une nouvelle partie. Le joueur y choisit le nombre de colonnes,
 * de lignes et de mines avant de lancer la partie.
 *
 * @version 1.0
 * @author Mehdy Ouissi
 * @author Kylian Spor
 */
public class ConfigPartie extends JFrame implements ChangeListener, ActionListener {

    /**
     * Couleur de fond principale (thème sombre).
     */
    private static final Color COULEUR_FOND      = new Color(30, 30, 35);

    /**
     * Couleur d'accent utilisée pour les titres et valeurs des sliders.
     */
    private static final Color COULEUR_ACCENT    = new Color(90, 160, 255);

    /**
     * Couleur du texte secondaire (labels des sliders).
     */
    private static final Color COULEUR_TEXTE_SEC = new Color(130, 130, 145);

    /**
     * La difficulté choisie par le joueur (EASY, MEDIUM ou HARD).
     */
    private String difficulte;

    /**
     * Le nombre maximum de cases autorisé pour cette difficulté.
     */
    private int maxCases;

    /**
     * Slider permettant de choisir le nombre de colonnes.
     */
    private JSlider sliderColonnes;

    /**
     * Slider permettant de choisir le nombre de lignes.
     */
    private JSlider sliderLignes;

    /**
     * Spinner permettant de choisir le nombre de mines.
     */
    private JSpinner spinnerMines;

    /**
     * Label affichant la valeur courante du slider colonnes.
     */
    private JLabel labelValCol;

    /**
     * Label affichant la valeur courante du slider lignes.
     */
    private JLabel labelValLig;

    /**
     * Label affichant le nombre maximum de mines possible.
     */
    private JLabel labelMaxMines;

    /**
     * Construit et affiche la fenêtre de configuration de partie.
     *
     * @param difficulte la difficulté choisie (EASY, MEDIUM ou HARD)
     * @param maxCases   le nombre maximum de cases autorisé
     */
    public ConfigPartie(String difficulte, int maxCases) {
        super("Demineur - Configuration");
        this.difficulte = difficulte;
        this.maxCases   = maxCases;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panneauPrincipal = new JPanel(new BorderLayout(0, 0));
        panneauPrincipal.setBackground(COULEUR_FOND);
        panneauPrincipal.add(construireEntete(), BorderLayout.NORTH);
        panneauPrincipal.add(construireCorps(),  BorderLayout.CENTER);
        panneauPrincipal.add(construireBas(),    BorderLayout.SOUTH);

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
     * Construit le panneau d'en-tête avec le titre et la difficulté.
     *
     * @return le panneau d'en-tête
     */
    private JPanel construireEntete() {
        JPanel panneau = new JPanel(new BorderLayout());
        panneau.setBackground(COULEUR_ACCENT);
        panneau.setBorder(new EmptyBorder(20, 28, 20, 28));

        String nomDiff = difficulte.substring(0, 1).toUpperCase()
                       + difficulte.substring(1).toLowerCase();

        JLabel titre = new JLabel("Nouvelle partie  -  " + nomDiff, SwingConstants.LEFT);
        titre.setFont(new Font("SansSerif", Font.BOLD, 18));
        titre.setForeground(Color.WHITE);

        JLabel sousTitre = new JLabel("Grille de 4 a " + maxCases + " cases", SwingConstants.LEFT);
        sousTitre.setFont(new Font("SansSerif", Font.PLAIN, 12));
        sousTitre.setForeground(new Color(200, 225, 255));

        JPanel textes = new JPanel(new GridLayout(2, 1, 0, 2));
        textes.setBackground(COULEUR_ACCENT);
        textes.add(titre);
        textes.add(sousTitre);
        panneau.add(textes, BorderLayout.WEST);
        return panneau;
    }

    /**
     * Construit le panneau central contenant les sliders et le spinner.
     *
     * @return le panneau de configuration
     */
    private JPanel construireCorps() {
        JPanel panneau = new JPanel(new GridLayout(3, 1, 0, 0));
        panneau.setBackground(COULEUR_FOND);
        panneau.setBorder(new EmptyBorder(24, 28, 8, 28));
        panneau.add(construireLigneSlider("Colonnes", true));
        panneau.add(construireLigneSlider("Lignes",   false));
        panneau.add(construireLigneMines());
        return panneau;
    }

    /**
     * Construit une ligne contenant un label et un slider.
     *
     * @param nom         le nom du paramètre affiché
     * @param estColonnes true pour le slider colonnes, false pour le slider lignes
     * @return le panneau de la ligne
     */
    private JPanel construireLigneSlider(String nom, boolean estColonnes) {
        JPanel panneau = new JPanel(new BorderLayout(12, 0));
        panneau.setBackground(COULEUR_FOND);
        panneau.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel label = new JLabel(nom);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(COULEUR_TEXTE_SEC);
        label.setPreferredSize(new Dimension(70, 20));

        JSlider slider = new JSlider(JSlider.HORIZONTAL, 4, maxCases, maxCases);
        slider.setMajorTickSpacing(Math.max(1, (maxCases - 4) / 4));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(COULEUR_FOND);
        slider.setForeground(COULEUR_TEXTE_SEC);
        slider.addChangeListener(this);

        JLabel valeur = new JLabel(maxCases + "", SwingConstants.RIGHT);
        valeur.setFont(new Font("SansSerif", Font.BOLD, 15));
        valeur.setForeground(COULEUR_ACCENT);
        valeur.setPreferredSize(new Dimension(32, 20));

        if (estColonnes) {
            sliderColonnes = slider;
            labelValCol    = valeur;
        } else {
            sliderLignes = slider;
            labelValLig  = valeur;
        }

        panneau.add(label,  BorderLayout.WEST);
        panneau.add(slider, BorderLayout.CENTER);
        panneau.add(valeur, BorderLayout.EAST);
        return panneau;
    }

    /**
     * Construit la ligne permettant de choisir le nombre de mines.
     *
     * @return le panneau de la ligne mines
     */
    private JPanel construireLigneMines() {
        JPanel panneau = new JPanel(new BorderLayout(12, 0));
        panneau.setBackground(COULEUR_FOND);
        panneau.setBorder(new EmptyBorder(0, 0, 16, 0));

        JLabel label = new JLabel("Mines");
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(COULEUR_TEXTE_SEC);
        label.setPreferredSize(new Dimension(70, 20));

        int minesDefaut = Math.max(1, (int) Math.round(maxCases * maxCases * 0.15));
        int minesMax    = maxCases * maxCases - 1;
        spinnerMines = new JSpinner(new SpinnerNumberModel(
            Integer.valueOf(minesDefaut),
            Integer.valueOf(1),
            Integer.valueOf(minesMax),
            Integer.valueOf(1)
        ));
        spinnerMines.setFont(new Font("SansSerif", Font.BOLD, 14));
        spinnerMines.setPreferredSize(new Dimension(80, 32));

        labelMaxMines = new JLabel("/ " + minesMax, SwingConstants.LEFT);
        labelMaxMines.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelMaxMines.setForeground(COULEUR_TEXTE_SEC);
        labelMaxMines.setBorder(new EmptyBorder(0, 8, 0, 0));

        JPanel droite = new JPanel(new BorderLayout());
        droite.setBackground(COULEUR_FOND);
        droite.add(spinnerMines,  BorderLayout.WEST);
        droite.add(labelMaxMines, BorderLayout.CENTER);

        panneau.add(label,  BorderLayout.WEST);
        panneau.add(droite, BorderLayout.CENTER);
        return panneau;
    }

    /**
     * Construit le panneau bas avec les boutons Retour et Lancer.
     *
     * @return le panneau des boutons d'action
     */
    private JPanel construireBas() {
        JPanel panneau = new JPanel(new BorderLayout(12, 0));
        panneau.setBackground(new Color(22, 22, 28));
        panneau.setBorder(new EmptyBorder(16, 28, 16, 28));

        JButton btnRetour = new JButton("Retour");
        btnRetour.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btnRetour.setForeground(COULEUR_TEXTE_SEC);
        btnRetour.setBackground(new Color(22, 22, 28));
        btnRetour.setBorderPainted(false);
        btnRetour.setFocusPainted(false);
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetour.addActionListener(this);
        btnRetour.setActionCommand("RETOUR");

        JButton btnLancer = new JButton("Lancer la partie");
        btnLancer.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLancer.setBackground(COULEUR_ACCENT);
        btnLancer.setForeground(Color.WHITE);
        btnLancer.setFocusPainted(false);
        btnLancer.setBorderPainted(false);
        btnLancer.setOpaque(true);
        btnLancer.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLancer.setPreferredSize(new Dimension(180, 40));
        btnLancer.addActionListener(this);
        btnLancer.setActionCommand("LANCER");

        panneau.add(btnRetour, BorderLayout.WEST);
        panneau.add(btnLancer, BorderLayout.EAST);
        return panneau;
    }

    /**
     * Met à jour les labels et la limite du spinner quand les sliders changent.
     */
    private void mettreAJour() {
        int cols        = sliderColonnes.getValue();
        int ligs        = sliderLignes.getValue();
        int minesMax    = cols * ligs - 1;
        int minesActuel = (Integer) spinnerMines.getValue();

        labelValCol.setText(cols + "");
        labelValLig.setText(ligs + "");

        SpinnerNumberModel model = (SpinnerNumberModel) spinnerMines.getModel();
        model.setMaximum(Integer.valueOf(minesMax));
        if (minesActuel > minesMax) {
            spinnerMines.setValue(Integer.valueOf(minesMax));
        }
        labelMaxMines.setText("/ " + minesMax);
    }

    /**
     * Réagit aux changements de valeur des sliders.
     *
     * @param e l'événement de changement
     */
    public void stateChanged(ChangeEvent e) {
        if (sliderColonnes != null && sliderLignes != null) {
            mettreAJour();
        }
    }

    /**
     * Réagit aux clics sur les boutons Retour et Lancer.
     *
     * @param e l'événement déclenché par le clic
     */
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("RETOUR")) {
            dispose();
            new MenuDifficulte();
        } else if (cmd.equals("LANCER")) {
            int cols  = sliderColonnes.getValue();
            int ligs  = sliderLignes.getValue();
            int mines = (Integer) spinnerMines.getValue();
            dispose();
            new FenetreJeu(new Grille(ligs, cols, mines));
        }
    }
}