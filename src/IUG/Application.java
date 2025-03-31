package IUG;

import Kmeans.KMean;
import Kmeans.KMeanSimple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe principale représentant l'application de visualisation K-Means.
 * Cette application permet de charger et visualiser des points en appliquant l'algorithme K-Means.
 */
public class Application extends JFrame {

    /**
     * Dimensions par défaut de la fenêtre.
     */
    private final int tailleX = 800, tailleY = 600;

    /**
     * Composant graphique affichant les points et le clustering.
     */
    private Graphe<?> graph;

    /**
     * Fenêtre d'importation des données.
     */
    private final FenetreImport fenetreImport;

    protected void switchGraph(Graphe newGraph){
        if(this.graph != null){ this.remove(graph); this.graph = null; }
        this.graph = newGraph;
        this.add(newGraph);
        this.revalidate();
        this.repaint();
    }

    /**
     * Crée le menu de l'application avec les options "Nouveau" et "Ouvrir".
     */
    private void creerMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fichier = new JMenu("Fichier");

        // Option "Nouveau" pour ouvrir une nouvelle fenêtre
        JMenuItem nouveau = new JMenuItem("Nouveau");
        nouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer une nouvelle fenêtre d'application
                Application newApp = new Application();
                newApp.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            }
        });

        // Option "Ouvrir" pour afficher la fenêtre d'importation
        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        Application app = this;
        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (app.fenetreImport.isVisible()) {
                    fenetreImport.toFront();
                } else {
                    app.fenetreImport.setVisible(true);
                }
            }
        });

        JMenuItem fermer = new JMenuItem("Fermer");
        fermer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.remove(app.graph);
                app.revalidate();
                app.repaint();
            }
        });

        fichier.add(nouveau);
        fichier.add(ouvrir);
        fichier.add(fermer);
        menuBar.add(fichier);
        this.setJMenuBar(menuBar);
    }

    /**
     * Crée le pied de page avec les boutons "Précédent" et "Suivant".
     */
    private void creerFooter() {
        Application app = this;

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setBackground(Color.DARK_GRAY);

        JButton precedent = new JButton("Précédent");
        precedent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.graph.precedent();
            }
        });

        JButton suivant = new JButton("Suivant");
        suivant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.graph.suivant();
            }
        });

        footer.add(precedent, BorderLayout.WEST);
        footer.add(suivant, BorderLayout.WEST);

        this.add(footer, BorderLayout.SOUTH);
    }

    /**
     * Constructeur de la classe {@code Application}.
     * Initialise la fenêtre, le menu, le graphique et la fenêtre d'importation.
     */
    public Application() {
        super("Visualisateur KMeans");

        // Définition des propriétés de la fenêtre
        this.setBounds(new Rectangle(tailleX, tailleY));
        this.setIconImage(new ImageIcon("src/icon.png").getImage());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Fermer la fenêtre d'import lors de la fermeture de l'application
        Application app = this;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { app.fenetreImport.dispose(); }
        });

        this.creerMenu();

        // Initialisation de la fenêtre d'importation
        this.fenetreImport = new FenetreImport(this);

        this.creerFooter();

        // Affichage de la fenêtre principale
        this.setVisible(true);
    }

    /**
     * Méthode principale lançant l'application.
     *
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        new Application();
    }
}
