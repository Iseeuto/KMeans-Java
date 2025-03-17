package IUG;

import Kmeans.KMeanSimple;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.Scanner;

public class Fenetre extends JFrame {

    private final int tailleX = 800, tailleY = 600;

    private Graphe graph;

    private void creerMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fichier = new JMenu("Fichier");

        // Nouvelle fenêtre
        JMenuItem nouveau = new JMenuItem("Nouveau");
        nouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer une nouvelle fenetre
                Fenetre newFenetre = new Fenetre();
            }
        });

        // Ouvrir un fichier tableur
        JMenuItem ouvrir = new JMenuItem("Ouvrir");
        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choice = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Fichiers tableur",
                        "xls", "csv"
                );
                choice.setFileFilter(filter);
                int option = choice.showOpenDialog(getParent());
                if(option == JFileChooser.APPROVE_OPTION) {
                    try{ Scanner scan = new Scanner(new FileReader(choice.getSelectedFile().getPath()));
                    } catch (Exception ex) { throw new RuntimeException(ex); }
                }
            }
        });

        fichier.add(nouveau);
        fichier.add(ouvrir);
        menuBar.add(fichier);
        this.setJMenuBar(menuBar);
    }

    private void creerFooter(){
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setBackground(Color.DARK_GRAY);

        JButton precedent = new JButton("Précédent");
        JButton suivant = new JButton("Suivant");

        footer.add(precedent, BorderLayout.WEST);
        footer.add(suivant, BorderLayout.WEST);

        this.add(footer, BorderLayout.SOUTH);
    }

    public Fenetre(){
        super("Visualisateur KMeans");

        this.setBounds(new Rectangle(tailleX, tailleY));
        this.setIconImage(new ImageIcon("src/icon.png").getImage());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.creerMenu();

        this.graph = new Graphe<KMeanSimple>(new KMeanSimple());
        this.add(this.graph);

        this.creerFooter();

        this.setVisible(true);
    }

    public static void main(String[] args) {
        Fenetre f = new Fenetre();
    }

}
