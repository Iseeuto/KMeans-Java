package IUG;

import Kmeans.KMean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Classe représentant un graphique affichant des points et intégrant l'algorithme K-means.
 *
 * @param <K> Une instance de KMean représentant l'algorithme utilisé pour le clustering.
 */
public class Graphe<K extends KMean<?>> extends JPanel {

    /**
     * Instance de l'algorithme K-Means.
     */
    public K Kmean;

    /**
     * Taille des points affichés sur le graphique.
     */
    private final int taillePoint = 6;

    /**
     * Rayon de la zone de survol autour d'un point.
     */
    private final int hoverRadius = 5;

    /**
     * Index du point actuellement survolé (-1 si aucun).
     */
    private Formes.Point hovered;

    /**
     * Décalage pour le positionnement des points dans la fenêtre.
     */
    private final int xOffset = 25, yOffset = 25;

    /**
     * Liste des points affichés sur le graphique.
     */
    private HashSet<Formes.Point> Points = new HashSet<>();

    /**
     * Retourne les points extrêmes du graphique.
     * Ces points représentent les valeurs maximales et minimales en X et Y.
     *
     * @return Un tableau contenant deux points : { (xMax, yMax), (xMin, yMin) }.
     */
    private Formes.Point[] getExtremes() {
        float xMax = Float.MIN_VALUE, yMax = Float.MIN_VALUE, xMin = Float.MAX_VALUE, yMin = Float.MAX_VALUE;

        for (Formes.Point p : Points) {
            xMax = Math.max(p.getX(), xMax);
            yMax = Math.max(p.getY(), yMax);
            xMin = Math.min(p.getX(), xMin);
            yMin = Math.min(p.getY(), yMin);
        }

        return new Formes.Point[]{new Formes.Point(xMax, yMax), new Formes.Point(xMin, yMin)};
    }

    /**
     * Vérifie si un point est actuellement survolé par la souris.
     *
     * @param mouseX Position X de la souris.
     * @param mouseY Position Y de la souris.
     * @param p      Point à vérifier.
     * @return {@code true} si le point est survolé, sinon {@code false}.
     */
    private boolean pointEstSurvole(int mouseX, int mouseY, Formes.Point p) {
        int rayon = this.taillePoint / 2 + this.hoverRadius;
        Formes.Point[] extremes = this.getExtremes();
        float xMax = extremes[0].getX(), yMax = extremes[0].getY();
        int posX = (int) (p.getX() / ((double) xMax / (double) (this.getWidth() - xOffset)));
        int posY = (int) (p.getY() / ((double) yMax / (double) (this.getHeight() - yOffset)));

        return (mouseX >= posX - rayon && mouseX <= posX + rayon &&
                mouseY >= posY - rayon && mouseY <= posY + rayon);
    }

    /**
     * Méthode servant à dessiner les points sur le graphique.
     * Actuellement, cette méthode est vide.
     */
    private void dessinerPoints() {
        return;
    }

    /**
     * Redéfinit la méthode {@code paintComponent} pour dessiner les points sur le graphique.
     *
     * @param g Composant graphique utilisé pour le rendu.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Formes.Point[] extremes = this.getExtremes();
        float xMax = extremes[0].getX(), yMax = extremes[0].getY();

        for (Formes.Point p : Points) {
            int posX;
            int posY;

            posX = (int) (p.getX() / ((double) xMax / (double) (this.getWidth() - xOffset)));
            posY = (int) (p.getY() / ((double) yMax / (double) (this.getHeight() - yOffset)));

            // Change la couleur du point si celui-ci est survolé
            if (p == this.hovered) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }

            g.fillOval(posX, posY, this.taillePoint, this.taillePoint);
        }
    }

    /**
     * Constructeur de la classe {@code Graphe}.
     *
     * @param kmean Instance de l'algorithme K-Means utilisée pour le clustering.
     */
    Graphe(K kmean) {
        this.Kmean = kmean;

//        // Génération aléatoire de points pour le test
//        for (int i = 0; i < Math.random() * 500; i++) {
//            this.Points.add(new Point((int) (Math.random() * 800), (int) (Math.random() * 600)));
//        }

        this.Points = (HashSet<Formes.Point>) this.Kmean.elts;

        for (Formes.Point p: Points) System.out.println(p.getX() + " " + p.getY());

        setBackground(Color.LIGHT_GRAY);

        // Ajoute un écouteur pour détecter le survol des points
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                hovered = null;
                for (Formes.Point p: Points) {
                    if (pointEstSurvole(mouseX, mouseY, p)) {
                        hovered = p;
                        break;
                    }
                }

                if (hovered != null) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                repaint(); // Redessiner l'affichage
            }
        });
    }
}
