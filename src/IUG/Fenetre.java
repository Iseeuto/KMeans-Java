package IUG;

import Kmeans.KMeanSimple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Fenetre extends JFrame {

    private final int tailleX = 800, tailleY = 600;

    private Graph graph;

    private void creerMenu(){
        MenuBar menuBar = new MenuBar();
        Menu fichier = new Menu("Fichier");

        // Nouvelle fenêtre
        MenuItem nouveau = new MenuItem("Nouveau");
        nouveau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Créer une nouvelle fenetre
                Fenetre newFenetre = new Fenetre();
            }
        });

        fichier.add(nouveau);
        fichier.add(new MenuItem("Ouvrir"));
        menuBar.add(fichier);
        this.setMenuBar(menuBar);
    }

    public Fenetre(){
        super("Visualisateur KMeans");

        this.setBounds(new Rectangle(tailleX, tailleY));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.creerMenu();

        this.graph = new Graph<KMeanSimple>(new KMeanSimple());
        this.add(this.graph);



        this.setVisible(true);
    }

    public static void main(String[] args) {
        Fenetre f = new Fenetre();
    }

}
