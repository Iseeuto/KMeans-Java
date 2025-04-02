package IUG;

import Formes.Point;
import Kmeans.KMean;
import Kmeans.KMeanSimple;
import Kmeans.KmeanElongated;

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
     * Application parente de la fenêtre.
     */
    private Application app;

    /**
     * Charge un fichier de données et applique l'algorithme K-Means correspondant.
     *
     * @param type      Type d'algorithme K-Means ("Simple", "Elongated", "Formes").
     * @param nbCentres Nombre de centres pour le clustering.
     */
    private void loadFile(String type, int nbCentres) {
        ArrayList<String> lines = new ArrayList<>();
        while (this.scanner.hasNextLine()) {
            lines.add(this.scanner.nextLine());
        }

        String[] labels = lines.get(0).split(";");
        ArrayList<Formes.Point> points = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            // TODO: faire pour n dimensions
            String[] s = lines.get(i).split(";");
            Formes.Point p = new Formes.Point(
                    Float.parseFloat(s[0].replace(',', '.')),
                    Float.parseFloat(s[1].replace(',', '.'))
            );
            points.add(p);
        }

        Graphe<?> graph = null;
        switch (type) {
            case "Simple":
                graph = new Graphe<>(new KMeanSimple(nbCentres, points));
                break;
            case "Elongated":
                graph = new Graphe<>(new KmeanElongated(nbCentres, points));
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
        importButton.addActionListener(e -> {
            JFileChooser choice = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers tableur (.csv)", "csv");
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
        });

        // Choix du type d'algorithme K-Means
        String[] choix = {"Simple", "Elongated", "Formes"};
        JComboBox<String> boite = new JComboBox<>(choix);
        boite.setSelectedIndex(0);
        panel.add(boite);

        SpinnerModel model = new SpinnerNumberModel(2, 2, 20, 1);
        JSpinner spinner = new JSpinner(model);
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            ((JSpinner.DefaultEditor) editor).getTextField().setEditable(false);
        }
        panel.add(spinner);

        // Bouton d'importation des données
        JButton importer = new JButton("Importer");
        importer.addActionListener(e -> {
            if (!filename.getText().isEmpty()) {
                parent.loadFile(String.valueOf(boite.getSelectedItem()), (Integer) spinner.getValue());
                parent.setVisible(false);
            } else {
                // TODO: Throw une erreur custom avec un JDialogue
            }
        });
        panel.add(importer);

        this.add(panel);
    }

    /**
     * Constructeur de la classe {@code FenetreImport}.
     * Initialise la fenêtre avec ses composants et ses propriétés.
     *
     * @param parent Application parente de la fenêtre d'importation.
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
