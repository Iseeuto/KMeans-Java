package IUG;

import Kmeans.KMean;
import Kmeans.KMeanSimple;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Classe représentant une fenêtre permettant d'importer des fichiers de données.
 * Cette fenêtre permet de sélectionner un fichier CSV et de choisir
 * un type d'algorithme K-Means avant d'importer les données.
 */
public class FenetreImport extends JFrame {

    /**
     * Scanner utilisé pour lire le fichier importé.
     */
    private Scanner scanner = null;

    /**
     * Application parente de la fenêtre
     */
    private Application app;

    private void loadFile(String type){
        ArrayList<String> lines = new ArrayList();
        int numline = 0;
        while(this.scanner.hasNextLine()){ lines.add(this.scanner.nextLine()); }

        String[] labels = lines.get(0).split(";");
        HashSet<Formes.Point> points = new HashSet<>();
        for(int i=1; i<lines.size(); i++){
            // TODO: faire pour n dimensions
            String[] s = lines.get(i).split(";");
            Formes.Point p = new Formes.Point(Float.parseFloat(s[0].replace(',', '.')), Float.parseFloat(s[1].replace(',', '.')));
            points.add(p);
        }

        Graphe<?> graph = null;

        switch (type){
            case "Simple":
                graph = new Graphe<KMeanSimple>(new KMeanSimple(2, points));
                break;
            case "Elongated":
                break;
            case "Formes":
                break;
        }

        app.switchGraph(graph);
    }

    /**
     * Initialise et organise les composants graphiques de la fenêtre d'importation.
     */
    private void creerContenu() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Panneau pour le sélecteur de fichiers
        JPanel fileImport = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(fileImport);

        // Champ de texte affichant le nom du fichier sélectionné
        TextField filename = new TextField();
        filename.setPreferredSize(new Dimension(150, 25));
        filename.setEditable(false);
        fileImport.add(filename);

        // Bouton permettant d'ouvrir un sélecteur de fichiers
        JButton importButton = new JButton("...");
        fileImport.add(importButton);

        FenetreImport parent = this; // Référence à la fenêtre actuelle
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choice = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Fichiers tableur (.csv)", "csv"
                );
                choice.setFileFilter(filter);

                int option = choice.showOpenDialog(getParent());
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        parent.scanner = new Scanner(new FileReader(choice.getSelectedFile().getPath()));
                        filename.setText(choice.getSelectedFile().getName());
                        filename.repaint();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        // Choix du type d'algorithme K-Means
        String[] choix = {"Simple", "Elongated", "Formes"};
        JComboBox<String> boite = new JComboBox<>(choix);
        boite.setSelectedIndex(0);
        panel.add(boite);

        // Bouton d'importation des données
        JButton importer = new JButton("Importer");
        importer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(filename.getText() != ""){
                    parent.loadFile(String.valueOf(boite.getSelectedItem()));
                    parent.setVisible(false);
                }
                else {
                 // TODO: Throw une erreur custom avec un JDialogue
                }
            }
        });
        panel.add(importer);

        this.add(panel);
    }

    /**
     * Constructeur de la classe {@code FenetreImport}.
     * Initialise la fenêtre avec ses composants et ses propriétés.
     */
    public FenetreImport(Application parent) {
        super("Importer données");

        this.app = parent;

        // Configuration de la fenêtre
        this.setBounds(new Rectangle(300, 125));
        this.setIconImage(new ImageIcon("src/icon.png").getImage());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocationRelativeTo(parent);

        this.creerContenu();

        this.setResizable(false);
        this.setVisible(false);
    }
}
